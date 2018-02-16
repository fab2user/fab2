package eu.cehj.cdb2.business.service.db.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import eu.cehj.cdb2.business.dao.CountryRepository;
import eu.cehj.cdb2.business.service.db.SynchronizationService;
import eu.cehj.cdb2.common.dto.SynchronizationDTO;
import eu.cehj.cdb2.entity.Country;
import eu.cehj.cdb2.entity.Synchronization;

@Service
public class SynchronizationServiceImpl extends BaseServiceImpl<Synchronization, SynchronizationDTO, Long> implements SynchronizationService {

    @Autowired
    private CountryRepository countryRepository;

    @Override
    public Synchronization populateEntityFromDTO(final SynchronizationDTO dto) throws Exception {
        final Synchronization entity = dto.getId() == null ? new Synchronization() : this.get(dto.getId());
        entity.setActive(dto.isActive());
        entity.setExecutionDate(dto.getExecutionDate());
        final Country country = this.countryRepository.getByName(dto.getCountryName());
        entity.setCountry(country);
        entity.setStatus(Synchronization.SyncStatus.valueOf(dto.getStatus()));
        return entity;
    }

    @Override
    public SynchronizationDTO populateDTOFromEntity(final Synchronization entity) throws Exception {
        final SynchronizationDTO dto = new SynchronizationDTO();
        dto.setId(entity.getId());
        dto.setActive(entity.isActive());
        dto.setExecutionDate(entity.getExecutionDate());
        return dto;
    }

}
