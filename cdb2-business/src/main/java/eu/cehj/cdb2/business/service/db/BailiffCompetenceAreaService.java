package eu.cehj.cdb2.business.service.db;

import java.util.List;

import org.springframework.stereotype.Service;

import eu.cehj.cdb2.common.dto.BailiffCompetenceAreaDTO;
import eu.cehj.cdb2.entity.BailiffCompetenceArea;

@Service
public interface BailiffCompetenceAreaService extends BaseService<BailiffCompetenceArea, Long> {

    public BailiffCompetenceAreaDTO save(BailiffCompetenceAreaDTO dto) throws Exception;

    public List<BailiffCompetenceAreaDTO> getAllDTO() throws Exception;

    public BailiffCompetenceAreaDTO getDTO(Long id) throws Exception;

    public List<BailiffCompetenceAreaDTO> getAllDTO(Long bailiffId) throws Exception;

}