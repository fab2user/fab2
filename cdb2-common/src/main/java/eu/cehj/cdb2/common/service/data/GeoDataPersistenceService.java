package eu.cehj.cdb2.common.service.data;

import java.util.List;

public class GeoDataPersistenceService implements DataPersistenceService {

    @Override
    public void persistData(final List<DataStructure> dataStrucures) {
        dataStrucures.stream().forEach(structure -> {
            final GeoDataStructure geoStructure = (GeoDataStructure) structure;

        });

    }

}
