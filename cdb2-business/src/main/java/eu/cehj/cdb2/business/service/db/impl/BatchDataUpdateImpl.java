package eu.cehj.cdb2.business.service.db.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import eu.cehj.cdb2.business.dao.BatchDataUpdateRepository;
import eu.cehj.cdb2.business.service.db.BatchDataUpdateService;
import eu.cehj.cdb2.business.service.db.CountryOfSyncService;
import eu.cehj.cdb2.common.dto.BatchDataUpdateDTO;
import eu.cehj.cdb2.entity.BatchDataUpdate;

@Service
public class BatchDataUpdateImpl extends BaseServiceImpl<BatchDataUpdate, BatchDataUpdateDTO, Long, BatchDataUpdateRepository> implements BatchDataUpdateService {

    @Autowired
    private CountryOfSyncService cosService;

    @Override
    public BatchDataUpdate populateEntityFromDTO(final BatchDataUpdateDTO dto) {
        final BatchDataUpdate entity = dto.getId() == null ? new BatchDataUpdate() : this.get(dto.getId());
        entity.setCountry(this.cosService.get(dto.getCountryOfSyncId()));
        entity.setField(dto.getField());
        entity.setValue(dto.getValue());
        return entity;
    }

    @Override
    public BatchDataUpdateDTO populateDTOFromEntity(final BatchDataUpdate entity){
        final BatchDataUpdateDTO dto = new BatchDataUpdateDTO();
        dto.setCountryOfSyncId(entity.getCountry().getId());
        dto.setField(entity.getField());
        dto.setValue(entity.getValue());
        return dto;
    }

    @Override
    public List<BatchDataUpdate> getByCountry(final long countryId) {
        return this.repository.getByCountryId(countryId);
    }

}
