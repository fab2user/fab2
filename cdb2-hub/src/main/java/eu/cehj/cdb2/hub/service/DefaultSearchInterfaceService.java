package eu.cehj.cdb2.hub.service;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import eu.cehj.cdb2.business.service.db.CountryOfSyncService;
import eu.cehj.cdb2.common.dto.BailiffDTO;
import eu.cehj.cdb2.entity.CountryOfSync;

@Service
public class DefaultSearchInterfaceService implements SearchInterfaceService{

    @Autowired
    CountryOfSyncService cosService;

    @Autowired
    RestTemplateBuilder builder;

    protected Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public void sendQuery(final String countryCode, final Map<String, ?> params) throws Exception {
        final CountryOfSync cos = this.cosService.getByCountryCode(countryCode);
        final RestTemplate restTemplate = this.builder.basicAuthorization(cos.getUser(), cos.getPassword()).build();
        final ResponseEntity<BailiffDTO[]> dtos = restTemplate.getForEntity(cos.getUrl(), BailiffDTO[].class, params);
        this.logger.debug(dtos.toString());
    }

}
