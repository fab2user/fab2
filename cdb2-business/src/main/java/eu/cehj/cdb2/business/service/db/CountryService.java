package eu.cehj.cdb2.business.service.db;

import org.springframework.stereotype.Service;

import eu.cehj.cdb2.common.dto.CountryDTO;
import eu.cehj.cdb2.entity.Country;

@Service
public interface CountryService extends BaseService<Country, CountryDTO, Long> {

}