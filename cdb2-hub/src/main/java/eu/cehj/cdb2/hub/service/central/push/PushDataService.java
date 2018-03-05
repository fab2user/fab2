package eu.cehj.cdb2.hub.service.central.push;

import eu.cehj.cdb2.entity.CountryOfSync;
import eu.cehj.cdb2.entity.Synchronization;
import eu.chj.cdb2.common.Data;

public interface PushDataService {

    public Data processBailiffs( final CountryOfSync cos) throws Exception;

    public Data processAreas(final CountryOfSync cos) throws Exception;

    public void sendToCDB(final Data data, Synchronization sync) throws Exception;

    public void process(CountryOfSync cos, Synchronization sync) throws Exception;

}
