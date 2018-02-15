package eu.cehj.cdb2.business.service.db.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import eu.cehj.cdb2.business.dao.CountryRepository;
import eu.cehj.cdb2.business.service.db.CountryService;
import eu.cehj.cdb2.common.dto.CountryDTO;
import eu.cehj.cdb2.entity.Country;

@Service
public class CountryServiceImpl extends BaseServiceImpl<Country, CountryDTO, Long> implements CountryService {

    @Autowired
    private CountryRepository repository;

    @Override
    public Country populateEntityFromDTO(final CountryDTO dto) throws Exception {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public CountryDTO populateDTOFromEntity(final Country entity) throws Exception {
        final CountryDTO dto = new CountryDTO(entity.getName(), entity.getUrl(), entity.isActive());
        return dto;
    }




}
