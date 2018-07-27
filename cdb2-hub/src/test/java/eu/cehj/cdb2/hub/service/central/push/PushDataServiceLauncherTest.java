package eu.cehj.cdb2.hub.service.central.push;

import static org.mockito.Mockito.*;

import org.junit.Before;
import org.junit.Test;

import eu.cehj.cdb2.entity.CountryOfSync;
import eu.cehj.cdb2.entity.CountryOfSync.SearchType;

public class PushDataServiceLauncherTest {

    private PushDataServiceFactory pushDataServiceFactory;

    private CountryOfSync cosManaged;

    private CountryOfSync cosLocalWS;

    @Before
    public void setUp() throws Exception {
        this.cosManaged = new CountryOfSync();
        this.cosManaged.setSearchType(SearchType.MANAGED);
        this.cosLocalWS = new CountryOfSync();
        this.cosLocalWS.setSearchType(SearchType.LOCAL_WS);
        this.pushDataServiceFactory = mock(PushDataServiceFactory.class);
        //        when(this.pushDataServiceFactory.getPushDataService(this.cosManaged)))
    }

    @Test
    public void testReturnPushSOAPDataService() {
        final PushDataServiceLauncher launcher = new PushDataServiceLauncher();
        launcher.process("BE");
    }

}
