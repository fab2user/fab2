package eu.cehj.cdb2.business.service.db.impl;

import org.springframework.stereotype.Service;

import eu.cehj.cdb2.business.dao.AdminAreaSubdivisionMinorRepository;
import eu.cehj.cdb2.business.service.data.GeoDataStructure;
import eu.cehj.cdb2.business.service.data.RecordBuilderHelper;
import eu.cehj.cdb2.business.service.db.AdminAreaSubdivisionMinorService;
import eu.cehj.cdb2.common.dto.AdminAreaSubdivisionMinorDTO;
import eu.cehj.cdb2.entity.AdminAreaSubdivisionMinor;

@Service
public class AdminAreaSubdivisionMinorServiceImpl extends BaseServiceImpl<AdminAreaSubdivisionMinor, AdminAreaSubdivisionMinorDTO, Long, AdminAreaSubdivisionMinorRepository> implements AdminAreaSubdivisionMinorService {

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

    @Override
    public AdminAreaSubdivisionMinor populateEntityFromDTO(final AdminAreaSubdivisionMinorDTO dto) throws Exception {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public AdminAreaSubdivisionMinorDTO populateDTOFromEntity(final AdminAreaSubdivisionMinor entity) throws Exception {
        // TODO Auto-generated method stub
        return null;
    }

}
