package eu.cehj.cdb2.business.service.db.impl;

import java.util.List;

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
    public List<CountryDTO> getAllDTO() throws Exception {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public CountryDTO getDTO(final Long id) throws Exception {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Country populateEntityFromDTO(final CountryDTO dto) throws Exception {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public CountryDTO populateDTOFromEntity(final Country entity) throws Exception {
        // TODO Auto-generated method stub
        return null;
    }




}
