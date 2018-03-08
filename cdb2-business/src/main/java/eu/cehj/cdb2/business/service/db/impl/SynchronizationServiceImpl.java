package eu.cehj.cdb2.business.service.db.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.querydsl.core.types.Predicate;

import eu.cehj.cdb2.business.dao.CountryOfSyncRepository;
import eu.cehj.cdb2.business.dao.SynchronizationRepository;
import eu.cehj.cdb2.business.service.db.SynchronizationService;
import eu.cehj.cdb2.common.dto.SynchronizationDTO;
import eu.cehj.cdb2.entity.CountryOfSync;
import eu.cehj.cdb2.entity.Synchronization;

@Service
public class SynchronizationServiceImpl extends BaseServiceImpl<Synchronization, SynchronizationDTO, Long, SynchronizationRepository> implements SynchronizationService {

    @Autowired
    private CountryOfSyncRepository countryRepository;

    @Override
    public Synchronization populateEntityFromDTO(final SynchronizationDTO dto) throws Exception {
        final Synchronization entity = dto.getId() == null ? new Synchronization() : this.get(dto.getId());
        entity.setActive(dto.isActive());
        entity.setEndDate(dto.getEndDate());
        final CountryOfSync country = this.countryRepository.getByName(dto.getCountryName());
        entity.setCountry(country);
        entity.setStatus(Synchronization.SyncStatus.valueOf(dto.getStatus()));
        return entity;
    }

    @Override
    public SynchronizationDTO populateDTOFromEntity(final Synchronization entity) throws Exception {
        final SynchronizationDTO dto = new SynchronizationDTO();
        dto.setId(entity.getId());
        dto.setActive(entity.isActive());
        dto.setCountryName(entity.getCountry().getName());
        dto.setEndDate(entity.getEndDate());
        dto.setStatus(entity.getStatus().toString());
        dto.setMessage(entity.getMessage());
        return dto;
    }

    @Override
    public List<SynchronizationDTO>getLastByCountry() throws Exception {
        final List<Synchronization> entities =  this.repository.getLastForEachCountry();
        final List<SynchronizationDTO> dtos = new ArrayList<>(entities.size());
        for(final Synchronization entity: entities) {
            final SynchronizationDTO dto = this.populateDTOFromEntity(entity);
            dtos.add(dto);
        }
        return dtos;
    }

    @Override
    public Page<SynchronizationDTO> findAll(final Predicate predicate, final Pageable pageable) throws Exception {
        final Page<Synchronization> entities = this.repository.findAll(predicate, pageable);
        final List<SynchronizationDTO> dtos = new ArrayList<>();
        for(final Synchronization entity: entities) {
            dtos.add(this.populateDTOFromEntity(entity));
        }
        return new PageImpl<>(dtos, pageable, entities.getTotalElements());
    }

}
