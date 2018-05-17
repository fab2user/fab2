package eu.cehj.cdb2.business.service.db;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.querydsl.core.types.Predicate;

import eu.cehj.cdb2.common.dto.GeoAreaDTO;
import eu.cehj.cdb2.common.dto.GeoAreaSimpleDTO;
import eu.cehj.cdb2.entity.GeoArea;

@Service
public interface GeoAreaService extends BaseService<GeoArea, GeoAreaDTO, Long>{

    public GeoAreaDTO saveDTO(GeoAreaDTO dto);

    public GeoAreaSimpleDTO getSimpleDTO(Long id);

    public List<GeoAreaSimpleDTO> getAllSimpleDTO();

    public Page<GeoAreaDTO> findAll(Predicate predicate, Pageable pageable);

}