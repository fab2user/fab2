package eu.cehj.cdb2.business.service.db.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import eu.cehj.cdb2.business.service.db.HubReferenceService;
import eu.cehj.cdb2.common.dto.HubReferenceDTO;
import eu.cehj.cdb2.entity.CountryOfSync.SearchType;

@Service
public class HubReferenceServiceImpl implements HubReferenceService {

    @Override
    public HubReferenceDTO getReference() {
        final HubReferenceDTO dto = new HubReferenceDTO();
        final List<String> searchTypes = new ArrayList<>();
        for (final SearchType searchType : SearchType.values()) {
            searchTypes.add(searchType.name());
        }
        dto.setSearchTypes(searchTypes);
        return dto;
    }

}
