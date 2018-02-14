package eu.cehj.cdb2.business.service.db;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.querydsl.core.types.Predicate;

import eu.cehj.cdb2.common.dto.MunicipalityDTO;
import eu.cehj.cdb2.entity.Municipality;

@Service
public interface MunicipalityService extends BaseService<Municipality, MunicipalityDTO, Long>, BaseGeoService<Municipality>, AdminAreaSubdivisionService {

    public MunicipalityDTO populateDTOFromEntity(Municipality municipality);

    public Page<MunicipalityDTO> findAll(Predicate predicate, Pageable pageable) throws Exception;

}