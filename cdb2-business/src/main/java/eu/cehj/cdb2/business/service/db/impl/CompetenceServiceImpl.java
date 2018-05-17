package eu.cehj.cdb2.business.service.db.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import eu.cehj.cdb2.business.dao.CompetenceRepository;
import eu.cehj.cdb2.business.service.db.CompetenceService;
import eu.cehj.cdb2.common.dto.CompetenceDTO;
import eu.cehj.cdb2.common.dto.SimpleCompetenceDTO;
import eu.cehj.cdb2.common.dto.SimpleInstrumentDTO;
import eu.cehj.cdb2.entity.Competence;
import eu.cehj.cdb2.entity.Instrument;

@Service
public class CompetenceServiceImpl extends BaseServiceImpl<Competence, CompetenceDTO, Long, CompetenceRepository> implements CompetenceService {

    @Override
    public List<CompetenceDTO> getAllDTOForInstrument(final Long instrumentId) {
        final List<Competence> entities = this.repository.findAllByInstrumentId(instrumentId);
        final List<CompetenceDTO> dtos = new ArrayList<CompetenceDTO>(entities.size());
        for(final Competence competence : entities) {
            dtos.add(this.populateDTOFromEntity(competence));
        }
        return dtos;
    }

    @Override
    public SimpleCompetenceDTO getSimpleDTO(final Long competenceId) {
        final Competence entity = this.get(competenceId);
        return this.populateSimpleDTOFromEntity(entity);
    }

    public SimpleCompetenceDTO populateSimpleDTOFromEntity(final Competence entity){
        final SimpleCompetenceDTO dto = new SimpleCompetenceDTO();
        dto.setId(entity.getId());
        dto.setCode(entity.getCode());
        dto.setDescription(entity.getDescription());
        final Instrument instrument = entity.getInstrument();
        final SimpleInstrumentDTO instDTO = new SimpleInstrumentDTO();
        instDTO.setId(instrument.getId());
        instDTO.setCode(instrument.getCode());
        instDTO.setDescription(instrument.getDescription());
        dto.setInstrument(instDTO);
        return dto;
    }

}
