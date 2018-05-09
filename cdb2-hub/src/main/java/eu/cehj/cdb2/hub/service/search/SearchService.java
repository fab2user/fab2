package eu.cehj.cdb2.hub.service.search;

import java.util.List;

import org.springframework.util.MultiValueMap;

import eu.cehj.cdb2.common.dto.BailiffDTO;

public interface SearchService {

    public List<BailiffDTO> sendQuery(final String countryCode, final MultiValueMap<String, String> params) throws Exception;
}
