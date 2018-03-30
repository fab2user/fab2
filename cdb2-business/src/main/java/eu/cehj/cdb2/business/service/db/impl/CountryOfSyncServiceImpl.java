package eu.cehj.cdb2.business.service.db.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import eu.cehj.cdb2.business.dao.CountryOfSyncRepository;
import eu.cehj.cdb2.business.service.db.CountryOfSyncService;
import eu.cehj.cdb2.business.service.db.SynchronizationService;
import eu.cehj.cdb2.common.dto.CountryOfSyncDTO;
import eu.cehj.cdb2.common.dto.CountryOfSyncRefDTO;
import eu.cehj.cdb2.entity.CountryOfSync;
import eu.cehj.cdb2.entity.Synchronization;

@Service
public class CountryOfSyncServiceImpl extends BaseServiceImpl<CountryOfSync, CountryOfSyncDTO, Long, CountryOfSyncRepository> implements CountryOfSyncService {

    @Autowired
    private SynchronizationService syncService;

    @Override
    public CountryOfSync populateEntityFromDTO(final CountryOfSyncDTO dto) throws Exception {
        final CountryOfSync entity = dto.getId() == null ? new CountryOfSync() : this.get(dto.getId());
        entity.setActive(dto.isActive());
        entity.setName(dto.getName());
        entity.setUrl(dto.getUrl());
        entity.setUser(dto.getUser());
        entity.setPassword(dto.getPassword());
        entity.setCountryCode(dto.getCountryCode());
        return entity;
    }

    @Override
    public CountryOfSyncDTO populateDTOFromEntity(final CountryOfSync entity) throws Exception {
        final Synchronization sync = this.syncService.getLastByCountry(entity.getId());
        final CountryOfSyncDTO dto = new CountryOfSyncDTO();
        dto.setName(entity.getName());
        dto.setActive(entity.isActive());
        dto.setId(entity.getId());
        dto.setLastSync(sync.getEndDate());
        dto.setLastSyncSuccess(sync.getStatus().equals(Synchronization.SyncStatus.OK) ? true: false);
        dto.setPassword(entity.getPassword());
        dto.setUser(entity.getUser());
        dto.setUrl(entity.getUrl());
        dto.setCountryCode(entity.getCountryCode());
        return dto;
    }

    @Override
    public CountryOfSync getByCountryCode(final String countryCode) throws Exception {
        return this.repository.getByCountryCode(countryCode);
    }

    @Override
    public List<CountryOfSyncRefDTO> getAllRefDTO() throws Exception {
        return this.getAll()
                .stream()
                .map(entity -> this.populateRefDTOFromEntity(entity))
                .collect(Collectors.toList());
    }

    private CountryOfSyncRefDTO populateRefDTOFromEntity(final CountryOfSync entity){
        final CountryOfSyncRefDTO dto = new CountryOfSyncRefDTO();
        dto.setId(entity.getId());
        dto.setCountryCode(entity.getCountryCode());
        dto.setName(entity.getName());
        return dto;
    }



}
