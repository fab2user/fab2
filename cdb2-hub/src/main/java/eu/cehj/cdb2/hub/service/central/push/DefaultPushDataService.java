package eu.cehj.cdb2.hub.service.central.push;

import java.util.ArrayList;
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
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import eu.cehj.cdb2.business.service.db.CountryOfSyncService;
import eu.cehj.cdb2.business.service.db.SynchronizationService;
import eu.cehj.cdb2.common.dto.BailiffExportDTO;
import eu.cehj.cdb2.common.dto.CompetenceExportDTO;
import eu.cehj.cdb2.common.dto.GeoAreaDTO;
import eu.cehj.cdb2.common.dto.MunicipalityDTO;
import eu.cehj.cdb2.common.service.CdbPushMessage;
import eu.cehj.cdb2.entity.CountryOfSync;
import eu.cehj.cdb2.entity.Synchronization;
import eu.cehj.cdb2.entity.Synchronization.SyncStatus;
import eu.cehj.cdb2.hub.utils.CdbResponse;
import eu.cehj.cdb2.hub.utils.Settings;
import eu.chj.cdb2.common.Body;
import eu.chj.cdb2.common.Body.Competences;
import eu.chj.cdb2.common.Body.Details;
import eu.chj.cdb2.common.Competence;
import eu.chj.cdb2.common.Data;
import eu.chj.cdb2.common.Detail;
import eu.chj.cdb2.common.GeoArea;
import eu.chj.cdb2.common.Municipality;
import eu.chj.cdb2.common.ObjectFactory;

@Service
public class DefaultPushDataService implements PushDataService {

    protected Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private CountryOfSyncService cosService;

    @Autowired
    private RestTemplateBuilder builder;

    @Autowired
    private Settings settings;

    @Autowired
    private SynchronizationService syncService;

    @Value("${cdb.update.url}")
    private String cdbUrl;

    @Value("${cdb.update.user}")
    private String cdbUser;

    @Value("${cdb.update.password}")
    private String cdbPassword;

    @Override
    public CountryOfSync getCountryUrl(final String countryCode) throws Exception {
        return this.cosService.getByCountryCode(countryCode);

    }

    @Override
    public Data processBailiffs(final CountryOfSync cos) throws Exception {

        final RestTemplate restTemplate = this.builder.basicAuthorization(cos.getUser(), cos.getPassword()).build();

        final UriComponentsBuilder uriComponentsBuilderBailiff = UriComponentsBuilder.fromHttpUrl(cos.getUrl() + "/" + this.settings.getBailiffsUrl());
        final ResponseEntity<List<BailiffExportDTO>> entities = restTemplate.exchange(uriComponentsBuilderBailiff.build().encode().toUri(), HttpMethod.GET, null,
                new ParameterizedTypeReference<List<BailiffExportDTO>>() {
        });
        this.logger.debug(entities.toString());
        final List<BailiffExportDTO> dtos = entities.getBody();
        final Data data = new Data();
        final ObjectFactory factory = new ObjectFactory();
        for (final BailiffExportDTO dto : dtos) {
            final Body body = new Body();
            body.setCountry(cos.getCountryCode());
            final Details details = new Details();
            final Detail detail = new Detail();
            detail.setName(dto.getName());
            detail.setAddress(dto.getAddress());
            detail.setEmail(dto.getEmail());
            detail.setFax(dto.getFax());
            detail.setTel(dto.getTel());
            detail.setPostalCode(dto.getPostalCode());
            detail.setMunicipality(dto.getMunicipality());
            details.getDetail().add(detail);
            body.setDetails(details);
            data.getCourtOrPhysicalPerson().add(body);
            final Competences competences = new Competences();
            for (final CompetenceExportDTO competenceDTO : dto.getCompetences()) {
                final Competence competence = new Competence();
                final GeoArea area = new GeoArea();
                area.setId(competenceDTO.getGeoAreaId());
                final JAXBElement<Object> areaId = factory.createCompetenceGeoAreaId(area);
                competence.setInstrument(competenceDTO.getInstrument());
                competence.setType(competenceDTO.getType());
                competence.getGeoAreaId().add(areaId);
                competences.getCompetence().add(competence);
            }
            body.setCompetences(competences);
        }
        return data;
    }

    @Override
    public Data processAreas(final CountryOfSync cos) throws Exception {
        // TODO: If needed, create a new geoArea service returning only data needed by CDB
        final Data data = new Data();
        final RestTemplate restTemplate = this.builder.basicAuthorization(cos.getUser(), cos.getPassword()).build();
        final UriComponentsBuilder uriComponentsBuilder = UriComponentsBuilder.fromHttpUrl(cos.getUrl() + "/" + this.settings.getAreasUrl());

        final ResponseEntity<List<GeoAreaDTO>> respDtos = restTemplate.exchange(uriComponentsBuilder.build().encode().toUri(), HttpMethod.GET, null,
                new ParameterizedTypeReference<List<GeoAreaDTO>>() {
        });
        this.logger.debug(respDtos.toString());
        final List<GeoAreaDTO> dtos = respDtos.getBody();
        for (final GeoAreaDTO dto : dtos) {
            final GeoArea geoArea = this.buildGeoArea(dto);
            data.getGeoArea().add(geoArea);
        }
        return data;

    }

    public GeoArea buildGeoArea(final GeoAreaDTO dto) throws Exception {
        final GeoArea geoArea = new GeoArea();
        geoArea.setId(Long.toString(dto.getId()));
        for (final MunicipalityDTO municipalityDTO : dto.getMunicipalities()) {
            final Municipality municipality = new Municipality();
            municipality.setName(municipalityDTO.getName());
            municipality.setPostalCode(municipalityDTO.getPostalCode());
            geoArea.getMunicipalityOrStreetOrAddress().add(municipality);
        }

        return geoArea;
    }

    @Override
    public void sendToCDB(final Data data, final Synchronization sync) throws Exception {
        sync.setStatus(SyncStatus.SENDING_TO_CDB);
        this.syncService.save(sync);
        final CdbPushMessage message = new CdbPushMessage();
        message.setData(data);
        final RestTemplate restTemplate = this.builder.basicAuthorization(this.cdbUser, this.cdbPassword).build();

        final UriComponentsBuilder uriComponentsBuilderBailiff = UriComponentsBuilder.fromHttpUrl(this.cdbUrl);
        final HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_XML);
        final HttpEntity<String> entity = new HttpEntity<>("parameters", headers);

        final ResponseEntity<CdbResponse> response = restTemplate.exchange(uriComponentsBuilderBailiff.build().encode().toUri(), HttpMethod.POST, entity,
                new ParameterizedTypeReference<CdbResponse>() {
        });
        if (response.getStatusCode().is2xxSuccessful()){
            sync.setStatus(SyncStatus.OK);
        }else {
            // TODO: If CDB WS doesn't return failureDescription, generate error message according to failureCode, following mapping in user manual
            sync.setMessage(response.getBody().getFailureDescription());
        }

        this.syncService.save(sync);
    }

    @Override
    public Synchronization process(final String countryCode) throws Exception {

        final CountryOfSync cos = this.getCountryUrl(countryCode);

        Synchronization sync = new Synchronization();
        sync.setCountry(cos);
        sync.setStatus(SyncStatus.IN_PROGRESS);
        sync = this.syncService.save(sync);
        //        this.processAsync(cos, sync);
        return sync;
    }

    @Async
    private void processAsync(final CountryOfSync cos, final Synchronization sync) throws Exception {
        // FIXME: doesn't execute asynchronously !
        try {
            final ExecutorService executor = Executors.newWorkStealingPool();
            final Callable<Data> taskBailiff = () -> {
                try {
                    return this.processBailiffs(cos);
                } catch (final InterruptedException e) {
                    throw new IllegalStateException("task interrupted", e);
                }
            };
            final Callable<Data> taskArea = () -> {
                try {
                    return this.processAreas(cos);
                } catch (final InterruptedException e) {
                    throw new IllegalStateException("task interrupted", e);
                }
            };
            final List<Callable<Data>> callables = new ArrayList<Callable<Data>>();
            callables.add(taskBailiff);
            callables.add(taskArea);

            final List<Future<Data>> finishedData = executor.invokeAll(callables);
            final Data dataToSend = finishedData.get(0).get();
            final Data areasData = finishedData.get(1).get();
            for (final GeoArea area : areasData.getGeoArea()) {
                dataToSend.getGeoArea().add(area);
            }
            this.sendToCDB(dataToSend, sync);
        } catch (final Exception e) {
            sync.setStatus(SyncStatus.ERROR);
            String message = e.getMessage();
            if(StringUtils.isBlank(message)) {
                message = "Unknown error while processing xml export file.";
            }
            sync.setMessage(message);
        }finally {
            sync.setExecutionDate(new Date());
            this.syncService.save(sync);
        }
    }

}
