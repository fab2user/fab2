package eu.cehj.cdb2.business.service.db;

import java.util.List;

import org.springframework.stereotype.Service;

import eu.cehj.cdb2.common.dto.GeoAreaDTO;
import eu.cehj.cdb2.entity.GeoArea;

@Service
public interface GeoAreaService extends BaseService<GeoArea, Long>{

    public List<GeoAreaDTO> getAllDTO() throws Exception;

    public GeoAreaDTO saveDTO(GeoAreaDTO dto) throws Exception;

}