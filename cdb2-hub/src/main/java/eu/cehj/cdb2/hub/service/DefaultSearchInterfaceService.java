package eu.cehj.cdb2.hub.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import eu.cehj.cdb2.business.service.db.CountryOfSyncService;
import eu.cehj.cdb2.common.dto.BailiffDTO;
import eu.cehj.cdb2.entity.CountryOfSync;
import eu.cehj.cdb2.hub.utils.RestResponsePage;

@Service
public class DefaultSearchInterfaceService implements SearchInterfaceService{

    @Autowired
    CountryOfSyncService cosService;

    @Autowired
    RestTemplateBuilder builder;

    protected Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public Page<BailiffDTO> sendQuery(final String countryCode, final MultiValueMap<String, String> params) throws Exception {
        final CountryOfSync cos = this.cosService.getByCountryCode(countryCode);
        final RestTemplate restTemplate = this.builder.basicAuthorization(cos.getUser(), cos.getPassword()).build();
        final UriComponentsBuilder uriComponentsBuilder = UriComponentsBuilder.fromHttpUrl(cos.getUrl())
                .queryParams(params);
        final ResponseEntity<RestResponsePage<BailiffDTO>> dtos = restTemplate.exchange(uriComponentsBuilder.build().encode().toUri(), HttpMethod.GET, null,  new ParameterizedTypeReference<RestResponsePage<BailiffDTO>>() {});
        this.logger.debug(dtos.toString());
        return dtos.getBody();
    }

}
