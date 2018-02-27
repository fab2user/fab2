package eu.cehj.cdb2.hub.service.central.push;

import eu.cehj.cdb2.common.service.CdbPushMessage;
import eu.cehj.cdb2.entity.CountryOfSync;
import eu.chj.cdb2.common.Data;

public interface PushDataService {

    public CountryOfSync getCountryUrl(final String countryCode) throws Exception;

    public Data processBailiffs( final CountryOfSync cos) throws Exception;

    public Data processAreas(final CountryOfSync cos) throws Exception;

    public CdbPushMessage pushData(final Data data) throws Exception;

    public CdbPushMessage process(final String countryCode) throws Exception;

}
