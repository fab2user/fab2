package eu.cehj.cdb2.hub.service.central.push;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import javax.xml.bind.JAXBElement;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import eu.cehj.cdb2.business.service.db.SynchronizationService;
import eu.cehj.cdb2.common.dto.BailiffExportDTO;
import eu.cehj.cdb2.common.dto.CompetenceExportDTO;
import eu.cehj.cdb2.common.dto.GeoAreaDTO;
import eu.cehj.cdb2.common.dto.MunicipalityDTO;
import eu.cehj.cdb2.common.exception.dto.CDBException;
import eu.cehj.cdb2.entity.CountryOfSync;
import eu.cehj.cdb2.entity.Synchronization;
import eu.cehj.cdb2.entity.Synchronization.SyncStatus;
import eu.cehj.cdb2.hub.service.RequestResponseLoggingInterceptor;
import eu.cehj.cdb2.hub.utils.GlobalCdbSyncResponse;
import eu.cehj.cdb2.hub.utils.Settings;
import eu.chj.cdb2.common.Body.Competences;
import eu.chj.cdb2.common.Body.Details;
import eu.chj.cdb2.common.Competence;
import eu.chj.cdb2.common.Court;
import eu.chj.cdb2.common.Data;
import eu.chj.cdb2.common.Detail;
import eu.chj.cdb2.common.GeoArea;
import eu.chj.cdb2.common.Municipality;
import eu.chj.cdb2.common.ObjectFactory;

@Service
public class AsyncPushDataService implements PushDataService {

    @Autowired
    private SynchronizationService syncService;

    private static final Logger LOGGER = LoggerFactory.getLogger(AsyncPushDataService.class);

    @Autowired
    private RestTemplateBuilder builder;

    @Autowired
    private Settings settings;

    @Value("${cdb.update.url}")
    private String cdbUrl;

    @Value("${cdb.update.user}")
    private String cdbUser;

    @Value("${cdb.update.password}")
    private String cdbPassword;

    @Override
    public void process(final CountryOfSync cos, final Synchronization sync)  {
        try {
            //            final ExecutorService executor = Executors.newWorkStealingPool();
            final ExecutorService executor = Executors.newFixedThreadPool(15);
            final Callable<Data> taskBailiff = () ->  this.processBailiffs(cos);
            final Callable<Data> taskArea = () -> this.processAreas(cos);
            final List<Callable<Data>> callables = new ArrayList<>();
            callables.add(taskBailiff);
            callables.add(taskArea);

            final List<Future<Data>> finishedData = executor.invokeAll(callables);
            final Data dataToSend = finishedData.get(0).get();
                        final Data areasData = finishedData.get(1).get();
                        for (final GeoArea area : areasData.getGeoAreas()) {
                            dataToSend.getGeoAreas().add(area);
                        }
            this.sendToCDB(dataToSend, sync);
        } catch (final Exception e) {
            sync.setStatus(SyncStatus.ERROR);
            String message = e.getMessage();
            if(StringUtils.isBlank(message)) {
                message = "Unknown error while processing xml export file.";
            }
            sync.setMessage(message);
            LOGGER.error(e.getMessage(), e);
        }finally {
            sync.setEndDate(new Date());
            this.syncService.save(sync);
        }
    }

    @Override
    public Data processBailiffs(final CountryOfSync cos) {

        final RestTemplate restTemplate = this.builder.basicAuthorization(cos.getUser(), cos.getPassword()).build();
        final String bailiffsUrl = cos.getUrl() + "/" + this.settings.getBailiffsUrl();
        final UriComponentsBuilder uriComponentsBuilderBailiff = UriComponentsBuilder.fromHttpUrl(bailiffsUrl);
        LOGGER.info("Push Service - Sending request to {}", bailiffsUrl);
        ResponseEntity<List<BailiffExportDTO>> entities = null;
        try {
            entities = restTemplate.exchange(uriComponentsBuilderBailiff.build().encode().toUri(), HttpMethod.GET, null,
                    new ParameterizedTypeReference<List<BailiffExportDTO>>() {
            });
        } catch (final RestClientException e) {
            if(e.getClass() == ResourceAccessException.class) {
                throw new CDBException(String.format("Serveur %s can not be reached. Please try again later.", bailiffsUrl), e);
            }
        }
        List<BailiffExportDTO> dtos = new ArrayList<>();
        if(entities != null) {
            dtos = entities.getBody();
        }
        final Data data = new Data();
        final ObjectFactory factory = new ObjectFactory();
        for (final BailiffExportDTO dto : dtos) {
            final Court court = new Court();
            court.setId(dto.getNationalId());
            court.setCountry(cos.getCountryCode());
            final Details details = new Details();
            final Detail detail = new Detail();
            detail.setName(dto.getName());
            detail.setAddress(dto.getAddress1() +
                    " " + dto.getAddress2());
			detail.setEmail(dto.getEmail());
			detail.setFax(dto.getFax());
			detail.setTel(dto.getTel());
			detail.setPostalCode(dto.getPostalCode());
			detail.setMunicipality(dto.getMunicipality());
			details.getDetails().add(detail);
			court.setDetails(details);
			data.getCourtsAndPhysicalPersons().add(court);
			final Competences competences = new Competences();
			for (final CompetenceExportDTO competenceDTO : dto.getCompetences()) {
				final Competence competence = new Competence();
				final GeoArea area = new GeoArea();
				area.setId(competenceDTO.getGeoAreaId());
				final JAXBElement<Object> areaId = factory.createCompetenceGeoAreaId(area);
				competence.setInstrument(competenceDTO.getInstrument());
				competence.setType(competenceDTO.getType());
				competence.getGeoAreaIds().add(areaId);
				competences.getCompetences().add(competence);
			}
			court.setCompetences(competences);
        }
        return data;
    }

    @Override
    public Data processAreas(final CountryOfSync cos) {
        // TODO: If needed, create a new geoArea service returning only data needed by CDB
        final Data data = new Data();
                final String areasUrl = cos.getUrl() + "/" + this.settings.getAreasUrl();
                final RestTemplate restTemplate = this.builder.basicAuthorization(cos.getUser(), cos.getPassword()).build();
                final UriComponentsBuilder uriComponentsBuilder = UriComponentsBuilder.fromHttpUrl(areasUrl);
                LOGGER.info("Push Service - Sending request to {}", areasUrl);
                final ResponseEntity<List<GeoAreaDTO>> respDtos = restTemplate.exchange(uriComponentsBuilder.build().encode().toUri(), HttpMethod.GET, null,
                        new ParameterizedTypeReference<List<GeoAreaDTO>>() {
                });
                if(LOGGER.isDebugEnabled()) {
                    LOGGER.debug(respDtos.toString());
                }
                final List<GeoAreaDTO> dtos = respDtos.getBody();
                for (final GeoAreaDTO dto : dtos) {
                    final GeoArea geoArea = this.buildGeoArea(dto);
                    data.getGeoAreas().add(geoArea);
                }
        return data;

    }

    @Override
    public void sendToCDB(final Data data, final Synchronization sync) {
        // Always in error since we don't have any test server able to process the XML file for now
        try {
            sync.setStatus(SyncStatus.SENDING_TO_CDB);
            sync.setMessage("Processing...");
            this.syncService.save(sync);

            final RestTemplate restTemplate = this.builder.basicAuthorization(this.cdbUser, this.cdbPassword).build();
            restTemplate.setInterceptors(Collections.singletonList(new RequestResponseLoggingInterceptor()));

            final UriComponentsBuilder uriComponentsBuilderBailiff = UriComponentsBuilder.fromHttpUrl(this.cdbUrl);
            final HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_XML);
            final HttpEntity<Data> entity = new HttpEntity<>(data, headers);


            final ResponseEntity<GlobalCdbSyncResponse> response = restTemplate.exchange(uriComponentsBuilderBailiff.build().encode().toUri(), HttpMethod.POST, entity,
                    new ParameterizedTypeReference<GlobalCdbSyncResponse>() {
            });
            if (response.getStatusCode().is2xxSuccessful()){
                sync.setMessage("Data export to CDB terminated successfully.");
                sync.setStatus(SyncStatus.OK);
            }else {
                // TODO: If CDB WS doesn't return failureDescription, generate error message according to failureCode, following mapping in user manual
                sync.setMessage(response.getBody().getFailureDescription());
            }

            this.syncService.save(sync);
        } catch (final Exception e) {
            // If CDB processes the request and an error occurs, it will answer with a nice message and code. But if we mess the things up before (wrong url or credentials), we get a dumb error message. That's why I prefer catching it here.
            LOGGER.error(e.getMessage(),e);
            throw new CDBException("Error while sending data to CDB.");
        }
    }

    public GeoArea buildGeoArea(final GeoAreaDTO dto){
        final GeoArea geoArea = new GeoArea();
        geoArea.setId(Long.toString(dto.getId()));
        for (final MunicipalityDTO municipalityDTO : dto.getMunicipalities()) {
            final Municipality municipality = new Municipality();
            municipality.setName(municipalityDTO.getName());
            municipality.setPostalCode(municipalityDTO.getPostalCode());
            geoArea.getMunicipalitiesAndStreetsAndAddresses().add(municipality);
        }

        return geoArea;
    }
}
