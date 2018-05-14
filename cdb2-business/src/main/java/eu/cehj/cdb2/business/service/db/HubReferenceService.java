package eu.cehj.cdb2.business.service.db;

import org.springframework.stereotype.Service;

import eu.cehj.cdb2.common.dto.HubReferenceDTO;

@Service
/**
 * Only here to provide reference values to populate select fields in Hub application;
 */
public interface HubReferenceService{

    public HubReferenceDTO getReference();

}