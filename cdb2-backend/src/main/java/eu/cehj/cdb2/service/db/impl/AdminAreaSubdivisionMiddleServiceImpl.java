package eu.cehj.cdb2.service.db.impl;

import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import eu.cehj.cdb2.business.service.data.GeoDataStructure;
import eu.cehj.cdb2.business.service.data.RecordBuilderHelper;
import eu.cehj.cdb2.dao.AdminAreaSubdivisionMiddleRepository;
import eu.cehj.cdb2.entity.AdminAreaSubdivisionMiddle;
import eu.cehj.cdb2.service.db.AdminAreaSubdivisionMiddleService;

@Service
public class AdminAreaSubdivisionMiddleServiceImpl extends BaseServiceImpl<AdminAreaSubdivisionMiddle, Long> implements AdminAreaSubdivisionMiddleService {

    @Autowired
    private EntityManager em;

    @Autowired
    private AdminAreaSubdivisionMiddleRepository repository;

    @Override
    public void updateAreaFromStructure(final GeoDataStructure structure, final RecordBuilderHelper helper) {
        AdminAreaSubdivisionMiddle area = this.repository.getByCode(structure.getMiddleAreaCode());
        if(area == null) {
            area = this.populateEntity(structure, helper);
            this.repository.save(area);
        }
        helper.setMiddleArea(area);
    }

    @Override
    public AdminAreaSubdivisionMiddle populateEntity(final GeoDataStructure structure, final RecordBuilderHelper helper) {
        final AdminAreaSubdivisionMiddle area = new AdminAreaSubdivisionMiddle();
        area.setCode(structure.getMiddleAreaCode());
        area.setName(structure.getMiddleAreaName());
        area.setAdminAreaSubdivisionMajor(helper.getMajorArea());
        return area;
    }

}
