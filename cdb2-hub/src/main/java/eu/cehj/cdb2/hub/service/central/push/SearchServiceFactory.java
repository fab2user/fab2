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
    @Qualifier("belgiumQueryService")
    private SearchService belgiumQueryService;

    @Autowired
    @Qualifier("franceQueryService")
    private SearchService franceQueryService;

    public SearchService getSearchService(final CountryOfSync cos) {
        SearchService searchService = null;
        switch (cos.getCountryCode()) {
            case "BE":
                searchService = this.belgiumQueryService;
                break;
            case "FR":
                searchService = this.franceQueryService;
                break;
            default:
                throw new CDBException(String.format("No search service associated with country code %s", cos.getCountryCode()));
        }
        return searchService;
    }
}
