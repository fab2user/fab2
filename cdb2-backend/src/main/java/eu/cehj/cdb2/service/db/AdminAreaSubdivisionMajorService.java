package eu.cehj.cdb2.service.db;

import org.springframework.stereotype.Service;

import eu.cehj.cdb2.business.service.data.GeoDataStructure;
import eu.cehj.cdb2.entity.AdminAreaSubdivisionMajor;

@Service
public interface AdminAreaSubdivisionMajorService extends BaseService<AdminAreaSubdivisionMajor, Long> {

    public void updateAreaFromStructure(GeoDataStructure structure);


}