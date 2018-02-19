package eu.cehj.cdb2.business.service.db;

import org.springframework.stereotype.Service;

import eu.cehj.cdb2.common.dto.CountryOfSyncDTO;
import eu.cehj.cdb2.entity.CountryOfSync;

@Service
public interface CountryOfSyncService extends BaseService<CountryOfSync, CountryOfSyncDTO, Long> {

    public CountryOfSync getByCountryCode(String countryCode)throws Exception;

}