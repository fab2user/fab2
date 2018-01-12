package eu.cehj.cdb2.service.db.impl;

import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import eu.cehj.cdb2.business.service.data.GeoDataStructure;
import eu.cehj.cdb2.business.service.data.RecordBuilderHelper;
import eu.cehj.cdb2.dao.AdminAreaSubdivisionMinorRepository;
import eu.cehj.cdb2.entity.AdminAreaSubdivisionMinor;
import eu.cehj.cdb2.service.db.AdminAreaSubdivisionMinorService;

@Service
public class AdminAreaSubdivisionMinorServiceImpl extends BaseServiceImpl<AdminAreaSubdivisionMinor, Long> implements AdminAreaSubdivisionMinorService {

    @Autowired
    private EntityManager em;

    @Autowired
    private AdminAreaSubdivisionMinorRepository repository;

    @Override
    public void updateAreaFromStructure(final GeoDataStructure structure, final RecordBuilderHelper helper) {
        AdminAreaSubdivisionMinor area = this.repository.getByCode(structure.getMinorAreaCode());
        if(area == null) {
            area = this.populateEntity(structure, helper);
            this.repository.save(area);
        }
        helper.setMinorArea(area);

    }

    @Override
    public AdminAreaSubdivisionMinor populateEntity(final GeoDataStructure structure, final RecordBuilderHelper helper) {
        final AdminAreaSubdivisionMinor area = new AdminAreaSubdivisionMinor();
        area.setCode(structure.getMinorAreaCode());
        area.setName(structure.getMinorAreaName());
        area.setAdminAreaSubdivisionMiddle(helper.getMiddleArea());
        return area;
    }

}
