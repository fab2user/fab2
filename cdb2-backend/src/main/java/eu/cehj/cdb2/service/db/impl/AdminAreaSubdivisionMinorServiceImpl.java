package eu.cehj.cdb2.service.db.impl;

import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import eu.cehj.cdb2.business.service.data.GeoDataStructure;
import eu.cehj.cdb2.entity.AdminAreaSubdivisionMinor;
import eu.cehj.cdb2.service.db.AdminAreaSubdivisionMinorService;

@Service
public class AdminAreaSubdivisionMinorServiceImpl extends BaseServiceImpl<AdminAreaSubdivisionMinor, Long> implements AdminAreaSubdivisionMinorService {

    @Autowired
    private EntityManager em;

    @Override
    public AdminAreaSubdivisionMinor populateEntity(final GeoDataStructure structure) {
        // TODO Auto-generated method stub
        return null;
    }

}
