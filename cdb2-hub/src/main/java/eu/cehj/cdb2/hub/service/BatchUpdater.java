package eu.cehj.cdb2.hub.service;

import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import eu.cehj.cdb2.business.service.db.BatchDataUpdateService;
import eu.cehj.cdb2.common.exception.dto.CDBException;
import eu.cehj.cdb2.entity.BatchDataUpdate;
import eu.cehj.cdb2.entity.CountryOfSync;
import eu.chj.cdb2.common.Competence;
import eu.chj.cdb2.common.Detail;
import eu.chj.cdb2.common.ObjectFactory;

@Service
public class BatchUpdater {

    private static final Logger LOGGER = LoggerFactory.getLogger(BatchUpdater.class);

    @Autowired
    private BatchDataUpdateService batchDataUpdateService;

    @Autowired
    private ObjectFactory objectFactory;

    private static final String COMPETENCE = "COMPETENCE";

    public Detail updateDetail(final CountryOfSync cos, Detail detail) {
        final List<BatchDataUpdate> updates = this.batchDataUpdateService.getByCountry(cos.getId());
        for(final BatchDataUpdate update: updates) {
            if(!COMPETENCE.equals(update.getField())){
                detail = this.createSOAPDetail(update, detail);
            }
        }
        return detail;
    }

    public Detail createSOAPDetail(final BatchDataUpdate update, final Detail detail) {
        if (update.getField().equals(COMPETENCE)) {
            throw new CDBException("This update cant not be used for detail field");
        }
        switch (update.getField()) {
            case "FAX":
                detail.setFax(update.getValue());
                break;
            case "LANG":
                detail.setLang(update.getValue());
                break;
            default:
                break;
        }
        return detail;
    }

    public List<Competence> updateCompetence(final CountryOfSync cos) {
        final List<BatchDataUpdate> updates = this.batchDataUpdateService.getByCountry(cos.getId());
        return updates
                .stream()
                .filter(update -> COMPETENCE.equals(update.getField()))
                .map(this::createSOAPCompetence)
                .collect(Collectors.toList());
    }

    public Competence createSOAPCompetence(final BatchDataUpdate update) {
        if (!update.getField().equals(COMPETENCE)) {
            throw new CDBException("This update cant not be used for competence field");
        }
        final Competence competence = this.objectFactory.createCompetence();
        final String[] values = update.getValue().split("\\|");
        competence.setType(values[0]);
        competence.setInstrument(values[1]);
        return competence;
    }
}
