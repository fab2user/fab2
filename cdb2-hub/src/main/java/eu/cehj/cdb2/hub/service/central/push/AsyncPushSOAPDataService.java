package eu.cehj.cdb2.hub.service.central.push;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import eu.cehj.cdb2.hub.service.BatchUpdater;

@Service
@Deprecated
/**
 * Supposed to provide specialization for AsyncPushDataService. However, since the former will probably always be a SOAP service, this class should never be used.
 *
 */
public class AsyncPushSOAPDataService extends AsyncPushDataService {

    @Autowired
    private BatchUpdater batchUpdater;

    @Autowired
    private SearchServiceFactory searchServiceFactory;

    private static final Logger LOGGER = LoggerFactory.getLogger(AsyncPushSOAPDataService.class);

    //    @Override
    //    public Data processBailiffs(final CountryOfSync cos) {
    //        final SearchService searchService = this.searchServiceFactory.getSearchService(cos);
    //        final List<BailiffDTO> dtos = searchService.sendQuery(cos.getCountryCode(), new LinkedMultiValueMap<>());
    //        final ObjectFactory factory = new ObjectFactory();
    //        final Data data = factory.createData();
    //
    //        try {
    //            for (final BailiffDTO dto : dtos) {
    //                final Court court = factory.createCourt();
    //                if(dto.getNationalId()!=null) {
    //                    court.setId(defaultString(dto.getNationalIdPrefix()) + dto.getNationalId());
    //                } else if (dto.getId() != null) {
    //                    court.setId(defaultString(dto.getNationalIdPrefix()) + Long.toString(dto.getId()));
    //                }else {
    //                    court.setId("N/A");
    //                }
    //                court.setCountry(cos.getCountryCode());
    //                final Details details = new Details();
    //                final Detail detail = this.batchUpdater.updateDetail(cos);
    //                detail.setName(defaultIfBlank(dto.getName(), detail.getName()));
    //                //                detail.setLang(defaultIfBlank(detail.getLang(),dto.getLangDisplay() ));
    //                detail.setLang("ZZ");
    //                detail.setAddress((defaultIfBlank(dto.getAddress1(), detail.getAddress()) + " " + defaultString(dto.getAddress2(), "")).trim());
    //                detail.setEmail(defaultIfBlank(dto.getEmail(), detail.getEmail()));
    //                detail.setFax(defaultIfBlank(dto.getFax(), detail.getFax()));
    //                detail.setTel(defaultIfBlank(dto.getPhone(), detail.getTel()));
    //                detail.setPostalCode(defaultIfBlank(dto.getPostalCode(), detail.getPostalCode()));
    //                detail.setMunicipality(defaultIfBlank(dto.getCity(), detail.getMunicipality()));
    //                details.getDetails().add(detail);
    //
    //                court.setDetails(details);
    //
    //                final Competences competences = factory.createBodyCompetences();
    //
    //                if (dto.getCompetences() != null) {
    //                    for (final CompetenceDTO competenceDTO : dto.getCompetences()) {
    //                        final Competence competence = factory.createCompetence();
    //                        competence.setType(competenceDTO.getCode());
    //                        competence.setInstrument(competenceDTO.getInstrument().getCode());
    //                        competences.getCompetences().add(competence);
    //                    }
    //                }
    //                final List<Competence>comps = this.batchUpdater.updateCompetence(cos);
    //                for(final Competence comp: comps) {
    //                    competences.getCompetences().add(comp);
    //                }
    //
    //                court.setCompetences(competences);
    //                data.getCourtsAndPhysicalPersons().add(court);
    //            }
    //        } catch (final Exception e) {
    //            LOGGER.error(e.getMessage(), e);
    //            throw new CDBException(e.getMessage());
    //        }
    //        return data;
    //    }
    //
    //    @Override
    //    public Data processAreas(final CountryOfSync cos) {
    //        return new Data();
    //    }
}
