package eu.cehj.cdb2.business.service.db;

import eu.cehj.cdb2.business.service.data.GeoDataStructure;
import eu.cehj.cdb2.business.service.data.RecordBuilderHelper;
import eu.cehj.cdb2.entity.BaseEntity;

public interface BaseGeoService<T extends BaseEntity> {

    public T populateEntity(GeoDataStructure structure, RecordBuilderHelper helper);
}
