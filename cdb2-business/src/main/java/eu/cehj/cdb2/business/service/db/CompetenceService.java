package eu.cehj.cdb2.business.service.db;

import java.util.List;

import org.springframework.stereotype.Service;

import eu.cehj.cdb2.common.dto.CompetenceDTO;
import eu.cehj.cdb2.entity.Competence;

@Service
public interface CompetenceService extends BaseService<Competence, Long>{

    public List<CompetenceDTO> getAllDTO() throws Exception;

    public CompetenceDTO getDTO(Long id) throws Exception;

    public List<CompetenceDTO> getAllDTOForInstrument(Long instrumentId) throws Exception;

}