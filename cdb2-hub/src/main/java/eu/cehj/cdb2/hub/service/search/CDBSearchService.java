package eu.cehj.cdb2.hub.service.search;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import eu.cehj.cdb2.business.service.db.BailiffService;
import eu.cehj.cdb2.business.service.db.CountryOfSyncService;
import eu.cehj.cdb2.common.dto.BailiffDTO;
import eu.cehj.cdb2.common.dto.cdb.CDBResponse;
import eu.cehj.cdb2.common.exception.dto.CDBException;
import eu.cehj.cdb2.entity.CountryOfSync;
import eu.cehj.cdb2.entity.CountryOfSync.SearchType;

@Service("cdbSearchService")
public class CDBSearchService implements SearchService {

	@Value("${cdb.instrument}")
	private String instrument;

	@Value("${cdb.competence.type}")
	private String competenceType;

	@Autowired
	private CountryOfSyncService cosService;

	@Autowired
	private BailiffService bailiffService;

	@Autowired
	private RestTemplateBuilder builder;


	@Override
	public List<BailiffDTO> sendQuery(final String countryCode, final MultiValueMap<String, String> params) {
		final CountryOfSync cos = this.cosService.getByCountryCode(countryCode);
		if(cos == null) {
			throw new CDBException(String.format("Country code not found \"%s\".", countryCode));
		}
		if(cos.getSearchType() != SearchType.CDB) {
			throw new CDBException(String.format("Search type \"%s\" not intended to be processed by CDBSearchService.",  cos.getSearchType()));
		}

		final String url = cos.getUrl();
		if(url == null) {
			throw new CDBException(String.format("Service url is unknown for country code \"%s\".", countryCode));
		}

		final RestTemplate restTemplate = this.builder.build();

		final MultiValueMap<String, String> headers =
				new LinkedMultiValueMap<>();
		headers.add("Content-Type", "application/json");
		final Map<String,String> query = new LinkedHashMap<>();
		query.put("country", countryCode);
		query.put("postalCode", params.getFirst("postalCode"));
		query.put("instrument", this.instrument);
		query.put("competenceType", this.competenceType);
		final HttpEntity<Map<String, String>> request = new HttpEntity<>(query, headers);
		final ResponseEntity<CDBResponse> response = restTemplate
				.exchange(url, HttpMethod.POST, request, CDBResponse.class);

		return this.bailiffService.populateDTOsFromCDB(response.getBody());
	}

}
