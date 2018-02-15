package eu.cehj.cdb2.business.service.db.impl;

import org.springframework.stereotype.Service;

import eu.cehj.cdb2.business.service.db.CountryService;
import eu.cehj.cdb2.common.dto.CountryDTO;
import eu.cehj.cdb2.entity.Country;

@Service
public class CountryServiceImpl extends BaseServiceImpl<Country, CountryDTO, Long> implements CountryService {

    @Override
    public Country populateEntityFromDTO(final CountryDTO dto) throws Exception {
        final Country entity = dto.getId() == null ? new Country() : this.get(dto.getId());
        entity.setActive(dto.isActive());
        entity.setName(dto.getName());
        entity.setUrl(dto.getUrl());
        return entity;
    }

    @Override
    public CountryDTO populateDTOFromEntity(final Country entity) throws Exception {
        final CountryDTO dto = new CountryDTO(entity.getName(), entity.getUrl(), entity.isActive());
        return dto;
    }




}
