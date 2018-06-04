package eu.cehj.cdb2.hub.service.search;

import static org.apache.commons.lang3.StringUtils.*;

import java.util.List;
import java.util.stream.Collectors;

import org.datacontract.schemas._2004._07.lnxnkcngdwhdjconsult.ArrayOfOffice;
import org.datacontract.schemas._2004._07.lnxnkcngdwhdjconsult.Office;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.MultiValueMap;
import org.springframework.ws.client.core.support.WebServiceGatewaySupport;
import org.springframework.ws.soap.client.core.SoapActionCallback;

import eu.cehj.cdb2.business.service.db.CountryOfSyncService;
import eu.cehj.cdb2.common.dto.BailiffDTO;
import eu.cehj.cdb2.common.exception.dto.CDBException;
import eu.cehj.cdb2.entity.CountryOfSync;
import https.www_nkcn_cia.FCBBGetOffices;
import https.www_nkcn_cia.FCBBGetOfficesResponse;
import https.www_nkcn_cia.ObjectFactory;

public class BelgiumSearchService extends WebServiceGatewaySupport implements LocalWSSearchService {

    @Autowired
    private CountryOfSyncService cosService;

    @Override
    public List<BailiffDTO> sendQuery(final String countryCode, final MultiValueMap<String, String> params){
        final CountryOfSync cos = this.cosService.getByCountryCode(countryCode);
        if(cos == null) {
            throw new CDBException(String.format("Unkown country code \"%s\"", countryCode));
        }
        final String soapAction = cos.getUrl();
        if(soapAction == null) {
            throw new CDBException("Web Service request impossible without Web Service Action URL.");
        }
        final FCBBGetOffices req = new FCBBGetOffices();
        final String postalCode = params.getFirst("postalCode");
        final ObjectFactory factory = new ObjectFactory();
        if (isNotBlank(postalCode)) {
            req.setStrZipCdOrCity( factory.createFCBBGetOfficesStrZipCdOrCity(postalCode));
        }
        final String name = params.getFirst("name");
        if(isNotBlank(name)) {
            req.setStrOfficeName(factory.createFCBBGetOfficesStrOfficeName(name));
        }
        final FCBBGetOfficesResponse resp = (FCBBGetOfficesResponse) this.getWebServiceTemplate().marshalSendAndReceive(req,
                new SoapActionCallback(soapAction));
        final ArrayOfOffice bailiffsContainer = resp.getFCBBGetOfficesResult().getValue();
        final List<Office> rawBailiffs = bailiffsContainer.getOffice();
        return rawBailiffs.stream().map(this::convertOfficeToDTO).collect(Collectors.toList());
    }

    private BailiffDTO convertOfficeToDTO(final Office office) {
        final BailiffDTO dto = new BailiffDTO();
        dto.setName(office.getOfficeName());
        dto.setAddress1(String.format("%s %s", office.getAddressStreetNmbr(), office.getAddressStreet()));
        dto.setPostalCode(office.getAddressZipCd());
        dto.setCity(office.getAddressCity());
        dto.setEmail(office.getEMail());
        dto.setPhone(office.getTel());
        dto.setFax(office.getFax());
        dto.setWebSite(office.getWeb());
        return dto;
    }

}
