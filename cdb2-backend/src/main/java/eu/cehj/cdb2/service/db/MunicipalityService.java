package eu.cehj.cdb2.service.db;

import java.util.List;

import eu.cehj.cdb2.common.dto.MunicipalityDTO;
import eu.cehj.cdb2.entity.Municipality;
import eu.cehj.cdb2.service.db.BaseService;

public interface MunicipalityService extends BaseService<Municipality, Long> {

    public MunicipalityDTO save(MunicipalityDTO municipalityDTO) throws Exception;

    public List<MunicipalityDTO> getAllDTO() throws Exception;

    public MunicipalityDTO getDTO(Long id) throws Exception;

}