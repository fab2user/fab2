package eu.cehj.cdb2.hub.service.central.push;

import java.util.List;

import eu.cehj.cdb2.common.service.CdbPushMessage;
import eu.cehj.cdb2.entity.Bailiff;

public interface PushDataService {

    public List<Bailiff> fetchBailiffs(String countryCode) throws Exception;

    public CdbPushMessage generateXmlContent(List<Bailiff> dtos) throws Exception;

    public CdbPushMessage pushData() throws Exception;

}
