package eu.cehj.cdb2.business.service.db;

import java.util.List;

import org.springframework.stereotype.Service;

import eu.cehj.cdb2.common.dto.BailiffCompetenceAreaCustomDTO;
import eu.cehj.cdb2.common.dto.BailiffCompetenceAreaDTO;
import eu.cehj.cdb2.entity.BailiffCompetenceArea;
import eu.cehj.cdb2.entity.GeoArea;

@Service
public interface BailiffCompetenceAreaService extends BaseService<BailiffCompetenceArea, BailiffCompetenceAreaDTO, Long> {

    public List<BailiffCompetenceAreaDTO> getAllDTO(Long bailiffId);

    public Iterable<BailiffCompetenceArea> findAllForGeoArea(GeoArea geoArea);

    public Iterable<BailiffCompetenceArea> findAllForBailiffId(Long bailiffId);

    List<BailiffCompetenceAreaCustomDTO> getAllSimpleDTO(Long bailiffId);

}