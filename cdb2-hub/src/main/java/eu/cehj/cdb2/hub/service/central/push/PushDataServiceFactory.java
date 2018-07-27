package eu.cehj.cdb2.hub.service.central.push;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import eu.cehj.cdb2.entity.CountryOfSync;
import eu.cehj.cdb2.entity.CountryOfSync.SearchType;

@Component
public class PushDataServiceFactory {

    @Autowired
    @Qualifier("asyncPushDataService")
    private PushDataService asyncPushDataService;

    @Autowired
    @Qualifier("asyncPushSOAPDataService")
    private PushDataService asyncPushSOAPDataService;

    public PushDataService getPushDataService(final CountryOfSync cos) {
        if(SearchType.LOCAL_WS.equals(cos.getSearchType())) {
            return this.asyncPushSOAPDataService;
        }
        return this.asyncPushDataService;
    }
}
