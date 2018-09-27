package eu.cehj.cdb2.hub.service.central.push.mapper;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import eu.cehj.cdb2.common.dto.BailiffDTO;
import eu.cehj.cdb2.common.dto.CompetenceDTO;
import eu.cehj.cdb2.entity.CountryOfSync;
import eu.cehj.cdb2.hub.service.BatchUpdater;
import eu.chj.cdb2.common.Body.Competences;
import eu.chj.cdb2.common.Body.Details;
import eu.chj.cdb2.common.Competence;
import eu.chj.cdb2.common.Court;
import eu.chj.cdb2.common.Data;
import eu.chj.cdb2.common.Detail;

@Service
public class BelgiumDataMapper implements DataMapper {

    @Autowired
    private BatchUpdater batchUpdater;

    @Override
    public Data map(final CountryOfSync cos, final List<BailiffDTO> dtos) {
        final Data data = new Data();
        for (final BailiffDTO dto : dtos) {
            final Court court = new Court();
            court.setId(StringUtils.defaultIfBlank(dto.getNationalId(), dto.getId().toString()));
            court.setCountry(cos.getCountryCode());
            final Details details = new Details();
            Detail detail = new Detail();
            detail.setName(dto.getName());
            detail.setLang(this.getLangOFDetails(dto.getLangOfDetails()));
            detail.setAddress((dto.getAddress1() + " " + StringUtils.defaultString(dto.getAddress2(), "")).trim());
            detail.setEmail(dto.getEmail());
            detail.setFax(dto.getFax());
            detail.setTel(dto.getPhone());
            detail.setPostalCode(dto.getPostalCode());
            detail.setMunicipality(dto.getCity());
            detail = this.batchUpdater.updateDetail(cos, detail);
            details.getDetails().add(detail);

            court.setDetails(details);
            data.getCourtsAndPhysicalPersons().add(court);
            final Competences competences = new Competences();
            for (final CompetenceDTO competenceDTO : dto.getCompetences()) {
                final Competence competence = new Competence();
                //                final GeoArea area = new GeoArea();
                //                area.setId(GEOAREA_PREFIX + competenceDTO.getGeoAreaId());
                //                final JAXBElement<Object> areaId = factory.createCompetenceGeoAreaId(area);
                competence.setInstrument(competenceDTO.getInstrument().getCode());
                //                competence.setType(competenceDTO.getType());
                //                competence.getGeoAreaIds().add(areaId);
                competences.getCompetences().add(competence);
            }

            final List<Competence>comps = this.batchUpdater.updateCompetence(cos);
            for(final Competence comp: comps) {
                competences.getCompetences().add(comp);
            }

            court.setCompetences(competences);

        }
        return data;
    }

    private String getLangOFDetails(final Long langCode) {

        if(langCode == null) {
            return "en";
        }
        if(1 == langCode) {
            return "NL";
        }
        if(2 == langCode) {
            return "FR";
        }
        return "en";
    }

}
