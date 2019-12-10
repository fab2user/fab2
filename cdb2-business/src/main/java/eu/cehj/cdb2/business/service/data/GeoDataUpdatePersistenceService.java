package eu.cehj.cdb2.business.service.data;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import eu.cehj.cdb2.business.service.db.MunicipalityService;

@Service
public class GeoDataUpdatePersistenceService implements DataPersistenceService {

	@Autowired
	MunicipalityService municipalityService;

	private static final Logger LOGGER = LoggerFactory.getLogger(GeoDataUpdatePersistenceService.class);

	@Override
	@Transactional
	public void persistData(final List<GeoDataStructure> dataStrucures) {
		dataStrucures.stream().forEach(geoStructure -> {
			final RecordBuilderHelper helper = new RecordBuilderHelper();
			if (geoStructure.getGeoNameFeatureClass().equals("P")) {
				this.municipalityService.updateGeoNameIDFromStructure(geoStructure, helper);
				LOGGER.debug("GeoName updated: " + geoStructure.toString());
			}
		});
	}
}
