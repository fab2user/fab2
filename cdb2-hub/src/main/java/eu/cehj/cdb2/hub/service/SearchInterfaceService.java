package eu.cehj.cdb2.hub.service;

import org.springframework.data.domain.Page;
import org.springframework.util.MultiValueMap;

import eu.cehj.cdb2.common.dto.BailiffDTO;

public interface SearchInterfaceService {

    public Page<BailiffDTO> sendQuery(String countryCode, MultiValueMap<String, String> params) throws Exception;
}
