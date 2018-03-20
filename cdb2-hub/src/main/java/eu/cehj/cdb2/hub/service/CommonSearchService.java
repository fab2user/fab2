package eu.cehj.cdb2.hub.service;

import java.util.List;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;

import eu.cehj.cdb2.common.dto.BailiffDTO;

@Primary
@Service
public class CommonSearchService implements SearchService, BeanFactoryAware {

    private BeanFactory beanFactory;
    private SearchService searchService;

    @Override
    public void setBeanFactory(final BeanFactory beanFactory) throws BeansException {
        this.beanFactory = beanFactory;
    }

    @Override
    public List<BailiffDTO> sendQuery(final String countryCode, final MultiValueMap<String, String> params) throws Exception {
        this.searchService = (SearchService)this.beanFactory.getBean(this.identifySearch(countryCode));
        //TODO: check if we really need to pass countryCode, and remove it if necessary
        return this.searchService.sendQuery(countryCode, params);
    }

    //FIXME: replace with correct implementation (DB lookup for accessing correct implementation --managed or not)
    private String identifySearch(final String countryCode) {
        if ("FR".equals(countryCode)) {
            return "franceSearchService";
        } else {
            return "defaultManagedSearchService";
        }
    }

}
