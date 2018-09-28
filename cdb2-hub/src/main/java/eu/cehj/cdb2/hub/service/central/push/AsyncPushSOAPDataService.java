package eu.cehj.cdb2.hub.service.central.push;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;

import eu.cehj.cdb2.common.dto.BailiffDTO;
import eu.cehj.cdb2.entity.CountryOfSync;
import eu.cehj.cdb2.hub.service.BatchUpdater;
import eu.cehj.cdb2.hub.service.central.push.mapper.DataMapper;
import eu.cehj.cdb2.hub.service.central.push.mapper.DataMapperFactory;
import eu.cehj.cdb2.hub.service.search.SearchService;
import eu.chj.cdb2.common.Data;

@Service
/**
 * Fetch Bailiffs data from national instances that have their own web service (hence no FAB database).
 *
 */
public class AsyncPushSOAPDataService extends AsyncPushDataService {

    @Autowired
    private BatchUpdater batchUpdater;

    @Autowired
    private SearchServiceFactory searchServiceFactory;

    @Autowired
    private DataMapperFactory dataMapperFactory;

    private static final Logger LOGGER = LoggerFactory.getLogger(AsyncPushSOAPDataService.class);

    @Override
    public Data processBailiffs(final CountryOfSync cos) {
        final SearchService searchService = this.searchServiceFactory.getSearchService(cos);
        final List<BailiffDTO> dtos = searchService.sendQuery(cos.getCountryCode(), new LinkedMultiValueMap<>());

        final DataMapper dataMapper = this.dataMapperFactory.getDataMapper(cos);
        return dataMapper.map(cos, dtos);
    }

    @Override
    public Data processAreas(final CountryOfSync cos) {
        return new Data();
    }

}
