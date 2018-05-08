package eu.cehj.cdb2.business.service.db.impl;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import eu.cehj.cdb2.business.dao.CountryOfSyncRepository;
import eu.cehj.cdb2.business.service.db.CountryOfSyncService;
import eu.cehj.cdb2.business.service.db.SynchronizationService;
import eu.cehj.cdb2.common.dto.CountryOfSyncDTO;
import eu.cehj.cdb2.common.dto.CountryOfSyncRefDTO;
import eu.cehj.cdb2.common.dto.SynchronizationDTO;
import eu.cehj.cdb2.entity.CountryOfSync;
import eu.cehj.cdb2.entity.Synchronization;

@Service
public class CountryOfSyncServiceImpl extends BaseServiceImpl<CountryOfSync, CountryOfSyncDTO, Long, CountryOfSyncRepository> implements CountryOfSyncService {

    @Autowired
    private SynchronizationService syncService;

    private final String CRON_SEPARATOR = ",";

    @Override
    public CountryOfSync populateEntityFromDTO(final CountryOfSyncDTO dto) throws Exception {
        final CountryOfSync entity = dto.getId() == null ? new CountryOfSync() : this.get(dto.getId());
        entity.setActive(dto.isActive());
        entity.setName(dto.getName());
        entity.setUrl(dto.getUrl());
        entity.setUser(dto.getUser());
        entity.setPassword(dto.getPassword());
        entity.setCountryCode(dto.getCountryCode());
        entity.setDaysOfWeek(this.intArrayToString(dto.getDaysOfWeek()));
        entity.setFrequency(dto.getFrequency());
        entity.setSearchType(dto.getSearchType());
        return entity;
    }

    @Override
    public CountryOfSyncDTO populateDTOFromEntity(final CountryOfSync entity) throws Exception {
        final CountryOfSyncDTO dto = new CountryOfSyncDTO();
        final Synchronization sync = this.syncService.getLastByCountry(entity.getId());
        if(sync != null) {
            final SynchronizationDTO syncDTO = this.syncService.populateDTOFromEntity(sync);
            //            dto.setLastSync(sync.getEndDate());
            //            dto.setLastSyncStatus(sync.getStatus());
            dto.setLastSynchronization(syncDTO);
        }
        dto.setName(entity.getName());
        dto.setActive(entity.isActive());
        dto.setId(entity.getId());
        dto.setPassword(entity.getPassword());
        dto.setUser(entity.getUser());
        dto.setUrl(entity.getUrl());
        dto.setDaysOfWeek(this.stringToIntArray(entity.getDaysOfWeek()));
        dto.setCountryCode(entity.getCountryCode());
        dto.setFrequency(entity.getFrequency());
        dto.setSearchType(entity.getSearchType());
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

    private String intArrayToString(final int[] intArray) {
        final String[]stArray = new String[intArray.length];
        for (int ind = 0;ind<intArray.length;ind++) {
            stArray[ind] = Integer.toString(intArray[ind]);
        }
        return Arrays.stream(stArray).collect(Collectors.joining(this.CRON_SEPARATOR));
    }

    private int[] stringToIntArray(final String st) {
        if(StringUtils.isNotBlank(st)) {
            final String[]strs = st.split(this.CRON_SEPARATOR);
            final int[]ints = new int[strs.length];
            for(int i = 0;i<ints.length;i++) {
                ints[i] = Integer.parseInt(strs[i]);
            }
            return ints;
        }else {
            return new int[0];
        }
    }


}
