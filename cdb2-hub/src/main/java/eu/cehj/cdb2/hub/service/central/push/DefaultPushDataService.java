package eu.cehj.cdb2.hub.service.central.push;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import javax.xml.bind.JAXBElement;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import eu.cehj.cdb2.business.service.db.CountryOfSyncService;
import eu.cehj.cdb2.common.dto.BailiffExportDTO;
import eu.cehj.cdb2.common.dto.CompetenceExportDTO;
import eu.cehj.cdb2.common.dto.GeoAreaDTO;
import eu.cehj.cdb2.common.dto.MunicipalityDTO;
import eu.cehj.cdb2.common.service.CdbPushMessage;
import eu.cehj.cdb2.entity.CountryOfSync;
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

    @Override
    public CountryOfSync getCountryUrl(final String countryCode) throws Exception {
        return this.cosService.getByCountryCode(countryCode);

    }

    @Override
    public Data processBailiffs( final CountryOfSync cos) throws Exception {

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
            for(final CompetenceExportDTO competenceDTO: dto.getCompetences()) {
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
        for(final GeoAreaDTO dto: dtos) {
            final GeoArea geoArea = this.buildGeoArea(dto);
            data.getGeoArea().add(geoArea);
        }
        return data;

    }

    public GeoArea buildGeoArea(final GeoAreaDTO dto) throws Exception{
        final GeoArea geoArea = new GeoArea();
        geoArea.setId(Long.toString(dto.getId()));
        for(final MunicipalityDTO municipalityDTO:dto.getMunicipalities()) {
            final Municipality municipality = new Municipality();
            municipality.setName(municipalityDTO.getName());
            municipality.setPostalCode(municipalityDTO.getPostalCode());
            geoArea.getMunicipalityOrStreetOrAddress().add(municipality);
        }

        return geoArea;
    }


    @Override
    public CdbPushMessage pushData(final Data data) throws Exception {
        //        final JAXBContext context = JAXBContext.newInstance(CdbPushMessage.class);
        //        final Marshaller m = context.createMarshaller();
        //        m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
        final CdbPushMessage message = new CdbPushMessage();
        message.setData(data);
        return message;

    }

    @Override
    public CdbPushMessage process(final String countryCode) throws Exception {
        final ExecutorService executor = Executors.newWorkStealingPool();
        final CountryOfSync cos = this.getCountryUrl(countryCode);
        final Callable<Data> taskBailiff = () -> {
            try {
                return this.processBailiffs(cos);
            }
            catch (final InterruptedException e) {
                throw new IllegalStateException("task interrupted", e);
            }
        };
        final Callable<Data> taskArea = () -> {
            try {
                return this.processAreas(cos);
            }
            catch (final InterruptedException e) {
                throw new IllegalStateException("task interrupted", e);
            }
        };
        final List<Callable<Data>> callables = new ArrayList<Callable<Data>>();
        callables.add(taskBailiff);
        callables.add(taskArea);

        final List<Future<Data>> finishedData = executor.invokeAll(callables);
        final Data bailiffData = finishedData.get(0).get();
        final Data areasData = finishedData.get(1).get();
        for(final GeoArea area: areasData.getGeoArea()) {
            bailiffData.getGeoArea().add(area);
        }
        return this.pushData(bailiffData);
    }

}
