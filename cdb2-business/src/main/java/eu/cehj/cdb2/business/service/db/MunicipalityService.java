package eu.cehj.cdb2.business.service.db;

import java.util.List;

import org.springframework.stereotype.Service;

import eu.cehj.cdb2.common.dto.MunicipalityDTO;
import eu.cehj.cdb2.entity.Municipality;

@Service
public interface MunicipalityService extends BaseService<Municipality, Long>, BaseGeoService<Municipality>, AdminAreaSubdivisionService {

    public MunicipalityDTO save(MunicipalityDTO municipalityDTO) throws Exception;

    public List<MunicipalityDTO> getAllDTO() throws Exception;

    public MunicipalityDTO getDTO(Long id) throws Exception;

    MunicipalityDTO populateDTOFromEntity(Municipality municipality);

}