package eu.cehj.cdb2.hub.service.central.push;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;

import eu.cehj.cdb2.common.dto.BailiffDTO;
import eu.cehj.cdb2.common.dto.CompetenceDTO;
import eu.cehj.cdb2.common.exception.dto.CDBException;
import eu.cehj.cdb2.entity.CountryOfSync;
import eu.cehj.cdb2.hub.service.BatchUpdater;
import eu.cehj.cdb2.hub.service.search.SearchService;
import eu.chj.cdb2.common.Body.Competences;
import eu.chj.cdb2.common.Body.Details;
import eu.chj.cdb2.common.Competence;
import eu.chj.cdb2.common.Court;
import eu.chj.cdb2.common.Data;
import eu.chj.cdb2.common.Detail;
import eu.chj.cdb2.common.ObjectFactory;

@Service
public class AsyncPushSOAPDataService extends AsyncPushDataService {

    private SearchService searchService;

    @Autowired
    private BatchUpdater batchUpdater;

    @Autowired
    private SearchServiceFactory searchServiceFactory;

    private static final Logger LOGGER = LoggerFactory.getLogger(AsyncPushSOAPDataService.class);

    @Override
    public Data processBailiffs(final CountryOfSync cos) {
        this.searchService = this.searchServiceFactory.getSearchService(cos);
        final List<BailiffDTO> dtos = this.searchService.sendQuery(cos.getCountryCode(), new LinkedMultiValueMap<>());
        final ObjectFactory factory = new ObjectFactory();
        final Data data = factory.createData();

        try {
            for (final BailiffDTO dto : dtos) {
                final Court body = factory.createCourt();
                if(dto.getId()!=null) {
                    body.setId(Long.toString(dto.getId()));
                }else {
                    body.setId("N/A");
                }
                body.setCountry(cos.getCountryCode());
                final Details details = new Details();
                final Detail detail = new Detail();
                detail.setName(dto.getName());
                detail.setLang(StringUtils.defaultIfBlank(dto.getLangDisplay(), "N/A"));
                detail.setAddress((dto.getAddress1() + " " + StringUtils.defaultString(dto.getAddress2(), "")).trim());
                detail.setEmail(dto.getEmail());
                detail.setFax(dto.getFax());
                detail.setTel(dto.getPhone());
                detail.setPostalCode(dto.getPostalCode());
                detail.setMunicipality(dto.getCity());
                details.getDetails().add(detail);
                body.setDetails(details);

                final Competences competences = factory.createBodyCompetences();

                if (dto.getCompetences() != null) {
                    for (final CompetenceDTO competenceDTO : dto.getCompetences()) {
                        final Competence competence = factory.createCompetence();
                        competence.setType(competenceDTO.getCode());
                        competence.setInstrument(competenceDTO.getInstrument().getCode());
                        competences.getCompetences().add(competence);
                    }
                }
                final List<Competence>comps = this.batchUpdater.updateCompetence(cos);
                for(final Competence comp: comps) {
                    competences.getCompetences().add(comp);
                }

                body.setCompetences(competences);
                data.getCourtsAndPhysicalPersons().add(body);
            }
        } catch (final Exception e) {
            LOGGER.error(e.getMessage(), e);
            throw new CDBException(e.getMessage());
        }
        return data;
    }

    @Override
    public Data processAreas(final CountryOfSync cos) {
        return new Data();
    }
}
