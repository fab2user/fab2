package eu.cehj.cdb2.hub.service.central.push;

import java.util.List;

import javax.xml.bind.JAXBElement;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;

import eu.cehj.cdb2.common.dto.BailiffDTO;
import eu.cehj.cdb2.common.dto.CompetenceDTO;
import eu.cehj.cdb2.entity.CountryOfSync;
import eu.cehj.cdb2.hub.service.search.BelgiumSearchService;
import eu.chj.cdb2.common.Body;
import eu.chj.cdb2.common.Body.Competences;
import eu.chj.cdb2.common.Body.Details;
import eu.chj.cdb2.common.Competence;
import eu.chj.cdb2.common.Data;
import eu.chj.cdb2.common.Detail;
import eu.chj.cdb2.common.GeoArea;
import eu.chj.cdb2.common.ObjectFactory;

@Service
public class AsyncPushSOAPDataService extends AsyncPushDataService {

    @Autowired
    BelgiumSearchService searchService;

    @Override
    public Data processBailiffs(final CountryOfSync cos) {
        final List<BailiffDTO>dtos = this.searchService.sendQuery(cos.getCountryCode(), new LinkedMultiValueMap<>());
        final Data data = new Data();
        final ObjectFactory factory = new ObjectFactory();
        for (final BailiffDTO dto : dtos) {
            final Body body = new Body();
            body.setCountry(cos.getCountryCode());
            final Details details = new Details();
            final Detail detail = new Detail();
            detail.setName(dto.getName());
            detail.setAddress(dto.getAddress1() +
                    " " + dto.getAddress2());
            detail.setEmail(dto.getEmail());
            detail.setFax(dto.getFax());
            detail.setTel(dto.getPhone());
            detail.setPostalCode(dto.getPostalCode());
            detail.setMunicipality(dto.getCity());
            details.getDetail().add(detail);
            body.setDetails(details);
            data.getCourtOrPhysicalPerson().add(body);
            final Competences competences = new Competences();
            for (final CompetenceDTO competenceDTO : dto.getCompetences()) {
                final Competence competence = new Competence();
                final GeoArea area = new GeoArea();
                //                area.setId(competenceDTO.getGeoAreaId());
                final JAXBElement<Object> areaId = factory.createCompetenceGeoAreaId(area);
                competence.setInstrument(competenceDTO.getInstrument().getDescription());
                //                competence.setType(competenceDTO.getType());
                competence.getGeoAreaId().add(areaId);
                competences.getCompetence().add(competence);
            }
            body.setCompetences(competences);
        }
        return data;
    }
}
