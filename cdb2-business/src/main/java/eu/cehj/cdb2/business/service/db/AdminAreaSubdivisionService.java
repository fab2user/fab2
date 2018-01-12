package eu.cehj.cdb2.business.service.db;

import eu.cehj.cdb2.business.service.data.GeoDataStructure;
import eu.cehj.cdb2.business.service.data.RecordBuilderHelper;

public interface AdminAreaSubdivisionService{

    public void updateAreaFromStructure(GeoDataStructure structure, RecordBuilderHelper helper);


}
