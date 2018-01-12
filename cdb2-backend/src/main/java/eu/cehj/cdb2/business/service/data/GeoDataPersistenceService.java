package eu.cehj.cdb2.business.service.data;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import eu.cehj.cdb2.service.db.AdminAreaSubdivisionMajorService;
import eu.cehj.cdb2.service.db.AdminAreaSubdivisionMiddleService;
import eu.cehj.cdb2.service.db.AdminAreaSubdivisionMinorService;
import eu.cehj.cdb2.service.db.MunicipalityService;

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

    @Override
    @Transactional
    public void persistData(final List<GeoDataStructure> dataStrucures) {
        dataStrucures.stream().forEach(structure -> {
            final GeoDataStructure geoStructure = structure;
            this.adminAreaSubdivisionMajorService.updateAreaFromStructure(geoStructure);
        });

    }

}
