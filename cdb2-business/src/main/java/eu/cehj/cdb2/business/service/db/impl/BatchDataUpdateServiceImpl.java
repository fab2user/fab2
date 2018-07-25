package eu.cehj.cdb2.business.service.db.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import eu.cehj.cdb2.business.dao.BatchDataUpdateRepository;
import eu.cehj.cdb2.business.service.db.BatchDataUpdateService;
import eu.cehj.cdb2.business.service.db.CountryOfSyncService;
import eu.cehj.cdb2.common.dto.BatchDataUpdateDTO;
import eu.cehj.cdb2.entity.BatchDataUpdate;

@Service
public class BatchDataUpdateServiceImpl extends BaseServiceImpl<BatchDataUpdate, BatchDataUpdateDTO, Long, BatchDataUpdateRepository> implements BatchDataUpdateService {

    private static final Logger LOGGER = LoggerFactory.getLogger(BatchDataUpdateServiceImpl.class);
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
        dto.setId(entity.getId());
        dto.setCountryOfSyncId(entity.getCountry().getId());
        dto.setField(entity.getField());
        dto.setValue(entity.getValue());
        return dto;
    }

    @Override
    public List<BatchDataUpdate> getByCountry(final long countryId) {
        return this.repository.getByCountryId(countryId);
    }

    @Override
    public List<BatchDataUpdateDTO> getDTOsByCountry(final long countryId) {
        final List<BatchDataUpdate> bdus = this.repository.getByCountryId(countryId);
        return bdus.stream()
                .map(this::populateDTOFromEntity)
                .collect(Collectors.toList());
    }

    @Override
    public List<BatchDataUpdate> save(final List<BatchDataUpdateDTO> bduDTOs, final Long countryOfSyncId) {
        final List<BatchDataUpdate>bdus =  this.getByCountry(countryOfSyncId);
        this.physicalDelete(bdus);

        return bduDTOs
                .stream()
                .map(this::save)
                .map(this::populateEntityFromDTO)
                .collect(Collectors.toList());
    }

    @Override
    public BatchDataUpdateDTO save(final BatchDataUpdateDTO dto){
        final BatchDataUpdate entity = this.populateEntityFromDTO(dto);
        entity.setDeleted(false);
        this.repository.save(entity);
        return this.populateDTOFromEntity(entity);
    }

}
