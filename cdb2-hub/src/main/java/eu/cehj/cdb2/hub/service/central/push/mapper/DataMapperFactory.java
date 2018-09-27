package eu.cehj.cdb2.hub.service.central.push.mapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import eu.cehj.cdb2.common.exception.dto.CDBException;
import eu.cehj.cdb2.entity.CountryOfSync;

@Component
public class DataMapperFactory {

    @Autowired
    @Qualifier("belgiumDataMapper")
    private DataMapper belgiumDataMapper;

    public DataMapper getDataMapper(final CountryOfSync cos) {
        DataMapper dataMapper = null;
        switch (cos.getCountryCode()) {
            case "BE":
                dataMapper = this.belgiumDataMapper;
                break;

            default:
                throw new CDBException(String.format("No search service associated with country code %s", cos.getCountryCode()));
        }
        return dataMapper;
    }
}
