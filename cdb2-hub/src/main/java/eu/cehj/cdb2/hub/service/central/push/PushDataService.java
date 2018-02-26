package eu.cehj.cdb2.hub.service.central.push;

import java.util.List;

import eu.cehj.cdb2.common.dto.BailiffExportDTO;
import eu.cehj.cdb2.common.service.CdbPushMessage;

public interface PushDataService {

    public List<BailiffExportDTO> fetchBailiffs(String countryCode) throws Exception;

    public CdbPushMessage generateXmlContent(List<BailiffExportDTO> dtos, String countryCode) throws Exception;

    public CdbPushMessage pushData() throws Exception;

}
