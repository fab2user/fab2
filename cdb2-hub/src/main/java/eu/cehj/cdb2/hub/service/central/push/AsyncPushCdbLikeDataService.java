package eu.cehj.cdb2.hub.service.central.push;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import eu.cehj.cdb2.common.dto.cdb.CDBResponse;
import eu.cehj.cdb2.common.dto.cdb.CompetentBody;
import eu.cehj.cdb2.common.dto.cdb.CompetentBodyDetail;
import eu.cehj.cdb2.common.exception.dto.CDBException;
import eu.cehj.cdb2.entity.CountryOfSync;
import eu.cehj.cdb2.hub.service.BatchUpdater;
import eu.chj.cdb2.common.Body.Competences;
import eu.chj.cdb2.common.Body.Details;
import eu.chj.cdb2.common.Competence;
import eu.chj.cdb2.common.Court;
import eu.chj.cdb2.common.Data;
import eu.chj.cdb2.common.Detail;
import eu.chj.cdb2.common.ObjectFactory;

@Service
/**
 * Fetch Bailiffs data from national instances that have their own web service (hence no FAB database).
 *
 */
public class AsyncPushCdbLikeDataService extends AsyncPushDataService {

	private static final String GEOAREA_PREFIX = "GEOAREABAIL";

	@Autowired
	private RestTemplateBuilder builder;

	@Autowired
	private BatchUpdater batchUpdater;

	private static final Logger LOGGER = LoggerFactory.getLogger(AsyncPushCdbLikeDataService.class);

	@Override
	public Data processBailiffs(final CountryOfSync cos) {

		// TODO: For some reason this method uses BailiffExportDTO instead of standard BailiffDTO.
		// See if it'd be possible to remove this one and always use BailiffDTO
		final RestTemplate restTemplate = this.builder.basicAuthorization(cos.getUser(), cos.getPassword()).build();
		final String bailiffsUrl = cos.getFetchUrl();
		final UriComponentsBuilder uriComponentsBuilderBailiff = UriComponentsBuilder.fromHttpUrl(bailiffsUrl);
		LOGGER.info("Push Service - Sending request to {}", bailiffsUrl);
		ResponseEntity<CDBResponse> entities = null;
		try {
			entities = restTemplate.exchange(uriComponentsBuilderBailiff.build().encode().toUri(), HttpMethod.GET, null,
					new ParameterizedTypeReference<CDBResponse>() {
			});
		} catch (final RestClientException e) {
			if(e.getClass() == ResourceAccessException.class) {
				throw new CDBException(String.format("Serveur %s can not be reached. Please try again later.", bailiffsUrl), e);
			} else {
				throw new CDBException("Unknown exception", e);
			}
		}
		List<CompetentBody> dtos = new ArrayList<>();
		if(entities != null) {
			dtos = entities.getBody().getCompetentBodies();
		}
		final Data data = new Data();
		final ObjectFactory factory = new ObjectFactory();
		for (final CompetentBody dto : dtos) {
			final Court court = new Court();
			court.setId(StringUtils.defaultIfBlank(dto.getId(), dto.getId().toString()));
			court.setCountry(cos.getCountryCode());
			final Details details = new Details();
			for (final CompetentBodyDetail competentBodyDetail : dto.getDetails()) {
				Detail detail = new Detail();
				detail.setName(competentBodyDetail.getName());
				detail.setLang(competentBodyDetail.getLang());
				detail.setAddress(competentBodyDetail.getAddress());
				//detail.setEmail(competentBodyDetail.get);
				detail.setFax(competentBodyDetail.getFax());
				detail.setTel(competentBodyDetail.getTel());
				detail.setPostalCode(competentBodyDetail.getPostalCode());
				detail.setMunicipality(competentBodyDetail.getMunicipality());
				detail = this.batchUpdater.updateDetail(cos, detail);
				details.getDetails().add(detail);
			}

			court.setDetails(details);
			data.getCourtsAndPhysicalPersons().add(court);
			final Competences competences = new Competences();

			final List<Competence>comps = this.batchUpdater.updateCompetence(cos);
			for(final Competence comp: comps) {
				competences.getCompetences().add(comp);
			}

			court.setCompetences(competences);

		}
		return data;
	}

	@Override
	public Data processAreas(final CountryOfSync cos) {
		return new Data();
	}
}
