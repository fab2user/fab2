package eu.cehj.cdb2.hub.service.central.push;

import eu.cehj.cdb2.entity.CountryOfSync;
import eu.cehj.cdb2.entity.Synchronization;
import eu.chj.cdb2.common.Data;

public interface PushDataService {

    public Data processBailiffs( final CountryOfSync cos);

    public Data processAreas(final CountryOfSync cos);

    public void sendToCDB(final Data data, Synchronization sync);

    public void process(CountryOfSync cos, Synchronization sync);

}
