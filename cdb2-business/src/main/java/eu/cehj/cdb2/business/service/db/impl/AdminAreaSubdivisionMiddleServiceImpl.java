package eu.cehj.cdb2.business.service.db.impl;

import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import eu.cehj.cdb2.business.dao.AdminAreaSubdivisionMiddleRepository;
import eu.cehj.cdb2.business.service.data.GeoDataStructure;
import eu.cehj.cdb2.business.service.data.RecordBuilderHelper;
import eu.cehj.cdb2.business.service.db.AdminAreaSubdivisionMiddleService;
import eu.cehj.cdb2.common.dto.AdminAreaSubdivisionMiddleDTO;
import eu.cehj.cdb2.entity.AdminAreaSubdivisionMiddle;

@Service
public class AdminAreaSubdivisionMiddleServiceImpl extends BaseServiceImpl<AdminAreaSubdivisionMiddle, AdminAreaSubdivisionMiddleDTO, Long> implements AdminAreaSubdivisionMiddleService {

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

    @Override
    public AdminAreaSubdivisionMiddle populateEntityFromDTO(final AdminAreaSubdivisionMiddleDTO dto) throws Exception {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public AdminAreaSubdivisionMiddleDTO populateDTOFromEntity(final AdminAreaSubdivisionMiddle entity) throws Exception {
        // TODO Auto-generated method stub
        return null;
    }

}
