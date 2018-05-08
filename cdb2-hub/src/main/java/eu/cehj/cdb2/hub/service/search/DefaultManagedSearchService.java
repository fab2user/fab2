package eu.cehj.cdb2.hub.service.search;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import eu.cehj.cdb2.business.service.db.CountryOfSyncService;
import eu.cehj.cdb2.common.dto.BailiffDTO;
import eu.cehj.cdb2.entity.CountryOfSync;
import eu.cehj.cdb2.hub.utils.Settings;

@Service
public class DefaultManagedSearchService extends ManagedSearchService {

    @Autowired
    private CountryOfSyncService cosService;

    @Autowired
    private RestTemplateBuilder builder;

    @Autowired
    Settings settings;

    protected Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public List<BailiffDTO> sendQuery(final String countryCode, final MultiValueMap<String, String> params) throws Exception {
        final CountryOfSync cos = this.cosService.getByCountryCode(countryCode);
        final RestTemplate restTemplate = this.builder.basicAuthorization(cos.getUser(), cos.getPassword()).build();
        final UriComponentsBuilder uriComponentsBuilder = UriComponentsBuilder.fromHttpUrl(cos.getUrl() + "/" + this.settings.getSearchUrl()).queryParams(params);
        final ResponseEntity<List<BailiffDTO>> dtos = restTemplate.exchange(uriComponentsBuilder.build().encode().toUri(), HttpMethod.GET, null,
                new ParameterizedTypeReference<List<BailiffDTO>>() {
        });
        this.logger.debug(dtos.toString());
        return dtos.getBody();
    }

}
