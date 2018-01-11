package eu.cehj.cdb2.service.db.impl;

import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import eu.cehj.cdb2.business.service.data.GeoDataStructure;
import eu.cehj.cdb2.entity.AdminAreaSubdivisionMiddle;
import eu.cehj.cdb2.service.db.AdminAreaSubdivisionMiddleService;

@Service
public class AdminAreaSubdivisionMiddleServiceImpl extends BaseServiceImpl<AdminAreaSubdivisionMiddle, Long> implements AdminAreaSubdivisionMiddleService {

    @Autowired
    private EntityManager em;

    @Override
    public AdminAreaSubdivisionMiddle populateEntity(final GeoDataStructure structure) {
        // TODO Auto-generated method stub
        return null;
    }

}
