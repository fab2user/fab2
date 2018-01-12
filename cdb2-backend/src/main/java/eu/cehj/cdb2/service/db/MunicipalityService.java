package eu.cehj.cdb2.service.db;

import java.util.List;

import eu.cehj.cdb2.common.dto.MunicipalityDTO;
import eu.cehj.cdb2.entity.Municipality;

public interface MunicipalityService extends BaseService<Municipality, Long>, BaseGeoService<Municipality>, AdminAreaSubdivisionService {

    public MunicipalityDTO save(MunicipalityDTO municipalityDTO) throws Exception;

    public List<MunicipalityDTO> getAllDTO() throws Exception;

    public MunicipalityDTO getDTO(Long id) throws Exception;

}