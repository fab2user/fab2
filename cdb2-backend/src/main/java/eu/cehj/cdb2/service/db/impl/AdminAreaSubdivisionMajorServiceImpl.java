package eu.cehj.cdb2.service.db.impl;

import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import eu.cehj.cdb2.business.service.data.GeoDataStructure;
import eu.cehj.cdb2.dao.AdminAreaSubdivisionMajorRepository;
import eu.cehj.cdb2.entity.AdminAreaSubdivisionMajor;
import eu.cehj.cdb2.service.db.AdminAreaSubdivisionMajorService;

@Service
public class AdminAreaSubdivisionMajorServiceImpl extends BaseServiceImpl<AdminAreaSubdivisionMajor, Long> implements AdminAreaSubdivisionMajorService {

    @Autowired
    private EntityManager em;

    @Autowired
    private AdminAreaSubdivisionMajorRepository repository;

    @Override
    public void updateAreaFromStructure(final GeoDataStructure structure) {
        AdminAreaSubdivisionMajor area = this.repository.getByCode(structure.getMajorAreaCode());
        if(area == null) {
            area = this.populateEntity(structure);
            this.repository.save(area);
        }

    }

    @Override
    public AdminAreaSubdivisionMajor populateEntity(final GeoDataStructure structure) {
        final AdminAreaSubdivisionMajor area = new AdminAreaSubdivisionMajor();
        area.setCode(structure.getMajorAreaCode());
        area.setName(structure.getMajorAreaName());
        return area;
    }

}
