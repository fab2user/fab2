package eu.cehj.cdb2.hub.service.central.push;

import java.io.ByteArrayOutputStream;
import java.util.List;

import javax.xml.bind.JAXBContext;
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
import eu.cehj.cdb2.common.service.CdbPushMessage;
import eu.cehj.cdb2.entity.Bailiff;
import eu.cehj.cdb2.entity.CountryOfSync;
import eu.cehj.cdb2.hub.utils.Settings;
import eu.chj.cdb2.common.Body;
import eu.chj.cdb2.common.Body.Details;
import eu.chj.cdb2.common.Data;
import eu.chj.cdb2.common.Detail;

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
    public List<Bailiff> fetchBailiffs(final String countryCode) throws Exception {
        final CountryOfSync cos = this.cosService.getByCountryCode(countryCode);
        final RestTemplate restTemplate = this.builder.basicAuthorization(cos.getUser(), cos.getPassword()).build();
        final UriComponentsBuilder uriComponentsBuilder = UriComponentsBuilder.fromHttpUrl(cos.getUrl() + "/" + this.settings.getBailiffsUrl());
        final ResponseEntity<List<Bailiff>> entities = restTemplate.exchange(uriComponentsBuilder.build().encode().toUri(), HttpMethod.GET, null,
                new ParameterizedTypeReference<List<Bailiff>>() {
        });
        this.logger.debug(entities.toString());
        return entities.getBody();
    }

    @Override
    public CdbPushMessage generateXmlContent(final List<Bailiff> entities) throws Exception {
        final ByteArrayOutputStream os = new ByteArrayOutputStream();
        final Data data = new Data();
        for (final Bailiff entity : entities) {
            final Body body = new Body();
            final Details details = new Details();
            final Detail detail = new Detail();
            detail.setName(entity.getName());
            detail.setAddress(entity.getAddress().getAddress());
            details.getDetail().add(detail);
            body.setDetails(details);
            data.getCourtOrPhysicalPerson().add(body);
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
        final List<Bailiff> entities = this.fetchBailiffs("FR");
        return this.generateXmlContent(entities);
    }

}
