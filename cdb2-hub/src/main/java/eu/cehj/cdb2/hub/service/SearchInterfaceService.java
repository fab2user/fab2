package eu.cehj.cdb2.hub.service;

import java.util.Map;

public interface SearchInterfaceService {

    public void sendQuery(String countryCode, Map<String, ?> params) throws Exception;
}
