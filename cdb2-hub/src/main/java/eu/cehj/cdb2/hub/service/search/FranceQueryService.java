package eu.cehj.cdb2.hub.service.search;

import static org.apache.commons.lang3.StringUtils.isNotBlank;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.MultiValueMap;
import org.springframework.ws.client.core.support.WebServiceGatewaySupport;
import org.springframework.ws.soap.client.core.SoapActionCallback;

import eu.cehj.cdb2.business.service.db.CountryOfSyncService;
import eu.cehj.cdb2.common.dto.BailiffDTO;
import eu.cehj.cdb2.common.exception.dto.CDBException;
import eu.cehj.cdb2.entity.CountryOfSync;
import eu.cehj.cdb2.hub.service.soap.france.Etude;
import eu.cehj.cdb2.hub.service.soap.france.ListeEtudeByDept;
import eu.cehj.cdb2.hub.service.soap.france.ListeEtudeByDeptResponse;

public class FranceQueryService extends WebServiceGatewaySupport implements LocalWSQueryService {

	@Autowired
	CountryOfSyncService cosService;

	@Override
	public List<BailiffDTO> sendQuery(final String countryCode, final MultiValueMap<String, String> params){
		// TODO - change this until a full search is available - For test purpose
		//		final MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
		//		params.set("name", "A");
		//
		// TODO make a switch , like in the Belgium implementation, to search either by the PostalCode or by the Name
		final CountryOfSync cos = this.cosService.getByCountryCode(countryCode);
		if(cos == null) {
			throw new CDBException(String.format("Unkown country code \"%s\"", countryCode));
		}
		final String soapAction = cos.getUrl();
		if(soapAction == null) {
			throw new CDBException("Web Service request impossible without Web Service Action URL.");
		}
		final ListeEtudeByDept req = new ListeEtudeByDept();
		final String postalCode = params.getFirst("postalCode");
		if (isNotBlank(postalCode)) {
			req.setDep(postalCode);
		}

		final ListeEtudeByDeptResponse resp = (ListeEtudeByDeptResponse) this.getWebServiceTemplate().marshalSendAndReceive(req,
				new SoapActionCallback(soapAction));
		final List<Etude> rawBailiffs = resp.getListeEtudeByDeptResult().getEtude();
		return rawBailiffs.stream().map(this::convertEtudeToDTO).collect(Collectors.toList());
	}

	private BailiffDTO convertEtudeToDTO(final Etude e) {
		final BailiffDTO dto = new BailiffDTO();
		dto.setId(Integer.valueOf(e.getId()).longValue());
		dto.setName(e.getNom());
		dto.setAddress1(e.getAds1());
		dto.setAddress2(e.getAds2());
		dto.setPostalCode(e.getCp());
		dto.setCity(e.getVille());
		dto.setEmail(e.getEm1());
		dto.setPhone(e.getTel());
		dto.setFax(e.getFax());
		dto.setWebSite(e.getWeb());
		dto.setOpenHours(e.getHor());
		return dto;
	}

}
