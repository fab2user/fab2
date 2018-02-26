package eu.cehj.cdb2.hub.service.central.push;

import java.io.ByteArrayOutputStream;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.Marshaller;

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
    public List<BailiffExportDTO> fetchBailiffs(final String countryCode) throws Exception {
        final CountryOfSync cos = this.cosService.getByCountryCode(countryCode);
        final RestTemplate restTemplate = this.builder.basicAuthorization(cos.getUser(), cos.getPassword()).build();
        final UriComponentsBuilder uriComponentsBuilder = UriComponentsBuilder.fromHttpUrl(cos.getUrl() + "/" + this.settings.getBailiffsUrl());
        final ResponseEntity<List<BailiffExportDTO>> entities = restTemplate.exchange(uriComponentsBuilder.build().encode().toUri(), HttpMethod.GET, null,
                new ParameterizedTypeReference<List<BailiffExportDTO>>() {
        });
        this.logger.debug(entities.toString());
        return entities.getBody();
    }

    @Override
    public CdbPushMessage generateXmlContent(final List<BailiffExportDTO> dtos, final String countryCode) throws Exception {
        final ByteArrayOutputStream os = new ByteArrayOutputStream();
        final Data data = new Data();
        final ObjectFactory factory = new ObjectFactory();
        for (final BailiffExportDTO dto : dtos) {
            final Body body = new Body();
            body.setCountry(countryCode);
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


        final JAXBContext context = JAXBContext.newInstance(CdbPushMessage.class);
        final Marshaller m = context.createMarshaller();
        m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
        final CdbPushMessage message = new CdbPushMessage();
        message.setData(data);
        return message;

    }

    @Override
    public CdbPushMessage pushData() throws Exception {
        final List<BailiffExportDTO> dtos = this.fetchBailiffs("FR");
        return this.generateXmlContent(dtos, "FR");
    }

}
