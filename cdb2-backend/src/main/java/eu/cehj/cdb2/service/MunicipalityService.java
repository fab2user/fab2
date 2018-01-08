package eu.cehj.cdb2.service;

import java.util.List;

import eu.cehj.cdb2.common.dto.MunicipalityDTO;
import eu.cehj.cdb2.db.Municipality;

public interface MunicipalityService extends BaseService<Municipality, Long> {

    public MunicipalityDTO save(MunicipalityDTO municipalityDTO) throws Exception;

    public List<MunicipalityDTO> getAllDTO() throws Exception;

    public MunicipalityDTO getDTO(Long id) throws Exception;

}