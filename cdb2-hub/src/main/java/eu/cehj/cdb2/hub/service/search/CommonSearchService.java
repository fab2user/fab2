package eu.cehj.cdb2.hub.service.search;

import java.util.List;

import org.springframework.beans.BeansException;
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
    private SearchService searchService;
    @Autowired
    private CountryOfSyncService cosService;

    @Override
    public void setBeanFactory(final BeanFactory beanFactory) throws BeansException {
        this.beanFactory = beanFactory;
    }

    @Override
    public List<BailiffDTO> sendQuery(final String countryCode, final MultiValueMap<String, String> params) throws Exception {
        this.searchService = (SearchService)this.beanFactory.getBean(this.identifySearch(countryCode));
        return this.searchService.sendQuery(countryCode, params);
    }

    private String identifySearch(final String countryCode) throws Exception{

        final CountryOfSync cos = this.cosService.getByCountryCode(countryCode);
        if(cos == null) {
            throw new CDBException(String.format("Unknown country code \"%s\".",countryCode ));
        }

        if(cos.getSearchType() == SearchType.CDB) {
            return "cdbSearchService";
        }else {
            if ("FR".equals(countryCode)) {
                return "franceSearchService";
            } else {
                return "defaultManagedSearchService";
            }
        }
    }

}
