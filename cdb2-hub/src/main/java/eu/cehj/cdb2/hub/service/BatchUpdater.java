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

    public List<Detail> updateDetail(final CountryOfSync cos) {
        final List<BatchDataUpdate> updates = this.batchDataUpdateService.getByCountry(cos.getId());
        return updates
                .stream()
                .filter(update -> !"COMPETENCE".equals(update.getField()))
                .map(this::createSOAPDetail)
                .collect(Collectors.toList());
    }

    public Detail createSOAPDetail(final BatchDataUpdate update) {
        if (update.getField().equals("COMPETENCE")) {
            throw new CDBException("This update cant not be used for detail field");
        }
        final Detail detail= this.objectFactory.createDetail();
        switch (update.getField()) {
            case "FAX":
                detail.setFax(update.getValue());
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
                .filter(update -> "COMPETENCE".equals(update.getField()))
                .map(this::createSOAPCompetence)
                .collect(Collectors.toList());
    }

    public Competence createSOAPCompetence(final BatchDataUpdate update) {
        if (!update.getField().equals("COMPETENCE")) {
            throw new CDBException("This update cant not be used for competence field");
        }
        final Competence competence = this.objectFactory.createCompetence();
        final String[] values = update.getValue().split("\\|");
        competence.setType(values[0]);
        competence.setInstrument(values[1]);
        return competence;
    }
}
