package eu.cehj.cdb2.business.service.db;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.querydsl.core.types.Predicate;

import eu.cehj.cdb2.common.dto.MunicipalityDTO;
import eu.cehj.cdb2.entity.Municipality;

@Service
public interface MunicipalityService extends BaseService<Municipality, Long>, BaseGeoService<Municipality>, AdminAreaSubdivisionService {

    public MunicipalityDTO save(MunicipalityDTO municipalityDTO) throws Exception;

    public List<MunicipalityDTO> getAllDTO() throws Exception;

    public MunicipalityDTO getDTO(Long id) throws Exception;

    public MunicipalityDTO populateDTOFromEntity(Municipality municipality);

    public Page<MunicipalityDTO> findAll(Predicate predicate, Pageable pageable) throws Exception;

}