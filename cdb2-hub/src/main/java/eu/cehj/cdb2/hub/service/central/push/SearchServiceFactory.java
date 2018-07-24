package eu.cehj.cdb2.hub.service.central.push;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import eu.cehj.cdb2.common.exception.dto.CDBException;
import eu.cehj.cdb2.entity.CountryOfSync;
import eu.cehj.cdb2.hub.service.search.SearchService;

@Component
public class SearchServiceFactory {

    @Autowired
    @Qualifier("belgiumSearchService")
    private SearchService belgiumSearchService;

    @Autowired
    @Qualifier("franceSearchService")
    private SearchService FranceSearchService;

    public SearchService getSearchService(final CountryOfSync cos) {
        SearchService searchService = null;
        switch (cos.getCountryCode()) {
            case "BE":
                searchService = this.belgiumSearchService;
                break;
            case "FR":
                searchService = this.FranceSearchService;
                break;
            default:
                throw new CDBException(String.format("No search service associated with country code %s", cos.getCountryCode()));
        }
        return searchService;
    }
}
