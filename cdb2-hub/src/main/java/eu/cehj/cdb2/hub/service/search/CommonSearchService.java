package eu.cehj.cdb2.hub.service.search;

import java.util.List;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;

import eu.cehj.cdb2.business.exception.CDBException;
import eu.cehj.cdb2.business.service.db.CountryOfSyncService;
import eu.cehj.cdb2.common.dto.BailiffDTO;
import eu.cehj.cdb2.entity.CountryOfSync;
import eu.cehj.cdb2.entity.CountryOfSync.SearchType;

@Primary
@Service
public class CommonSearchService implements SearchService, BeanFactoryAware {

    private BeanFactory beanFactory;
    @Autowired
    private CountryOfSyncService cosService;

    @Override
    public void setBeanFactory(final BeanFactory beanFactory) {
        this.beanFactory = beanFactory;
    }

    @Override
    public List<BailiffDTO> sendQuery(final String countryCode, final MultiValueMap<String, String> params) {
        final SearchService searchService = (SearchService)this.beanFactory.getBean(this.identifySearch(countryCode));
        return searchService.sendQuery(countryCode, params);
    }

    private String identifySearch(final String countryCode) {

        final CountryOfSync cos = this.cosService.getByCountryCode(countryCode);
        if(cos == null) {
            throw new CDBException(String.format("Unknown country code \"%s\".",countryCode ));
        }

        if(cos.getSearchType() == SearchType.CDB) {
            return "cdbSearchService";
        }else {
            // TODO: Pass to a default local webservice service, which will call the relevant country service
            if ("FR".equals(countryCode)) {
                return "franceSearchService";
            } else {
                return "defaultManagedSearchService";
            }
        }
    }

}
