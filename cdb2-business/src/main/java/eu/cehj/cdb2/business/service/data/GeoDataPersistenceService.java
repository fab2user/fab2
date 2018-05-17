package eu.cehj.cdb2.business.service.data;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import eu.cehj.cdb2.business.service.db.AdminAreaSubdivisionMajorService;
import eu.cehj.cdb2.business.service.db.AdminAreaSubdivisionMiddleService;
import eu.cehj.cdb2.business.service.db.AdminAreaSubdivisionMinorService;
import eu.cehj.cdb2.business.service.db.MunicipalityService;

@Service
public class GeoDataPersistenceService implements DataPersistenceService {

    @Autowired
    MunicipalityService municipalityService;

    @Autowired
    AdminAreaSubdivisionMajorService adminAreaSubdivisionMajorService;

    @Autowired
    AdminAreaSubdivisionMinorService adminAreaSubdivisionMinorService;

    @Autowired
    AdminAreaSubdivisionMiddleService  adminAreaSubdivisionMiddleService;


    private static final Logger LOGGER = LoggerFactory.getLogger(GeoDataPersistenceService.class);

    @Override
    @Transactional
    public void persistData(final List<GeoDataStructure> dataStrucures) {
        dataStrucures.stream().forEach(geoStructure -> {
            final RecordBuilderHelper helper = new RecordBuilderHelper();
            this.adminAreaSubdivisionMajorService.updateAreaFromStructure(geoStructure, helper);
            this.adminAreaSubdivisionMiddleService.updateAreaFromStructure(geoStructure, helper);
            this.adminAreaSubdivisionMinorService.updateAreaFromStructure(geoStructure, helper);
            this.municipalityService.updateAreaFromStructure(geoStructure, helper);
            LOGGER.debug("Area processed: " + geoStructure.toString());
        });
    }
}
