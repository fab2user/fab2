package eu.cehj.cdb2.hub.service.central.push;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import eu.cehj.cdb2.business.service.db.CountryOfSyncService;
import eu.cehj.cdb2.business.service.db.SynchronizationService;
import eu.cehj.cdb2.entity.CountryOfSync;
import eu.cehj.cdb2.entity.Synchronization;
import eu.cehj.cdb2.entity.Synchronization.SyncStatus;

@Service
public class PushDataServiceLauncher{

    private static final Logger LOGGER = LoggerFactory.getLogger(PushDataServiceLauncher.class);

    @Autowired
    private CountryOfSyncService cosService;

    @Autowired
    private SynchronizationService syncService;

    @Autowired
    private PushDataService asyncPushDataService;

    public CountryOfSync getCountryUrl(final String countryCode) {
        return this.cosService.getByCountryCode(countryCode);

    }

    public Synchronization process(final String countryCode) {
        if(LOGGER.isDebugEnabled()) {
            LOGGER.debug(String.format("Starting CDB sync for country %s...", countryCode));
        }
        final CountryOfSync cos = this.getCountryUrl(countryCode);

        Synchronization sync = new Synchronization();
        sync.setCountry(cos);
        sync.setStatus(SyncStatus.IN_PROGRESS);
        sync = this.syncService.save(sync);
        this.asyncPushDataService.process(cos, sync);
        return sync;
    }

}
