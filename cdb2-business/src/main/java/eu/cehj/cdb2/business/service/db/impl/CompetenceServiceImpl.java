package eu.cehj.cdb2.business.service.db.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import eu.cehj.cdb2.business.dao.CompetenceRepository;
import eu.cehj.cdb2.business.service.db.CompetenceService;
import eu.cehj.cdb2.business.service.db.InstrumentService;
import eu.cehj.cdb2.common.dto.CompetenceDTO;
import eu.cehj.cdb2.entity.Competence;

@Service
public class CompetenceServiceImpl extends BaseServiceImpl<Competence, CompetenceDTO, Long, CompetenceRepository> implements CompetenceService {

    @Autowired
    private InstrumentService instrumentService;

    @Override
    public CompetenceDTO populateDTOFromEntity(final Competence competence) throws Exception {
        //        final CompetenceDTO dto = new CompetenceDTO();
        //        dto.setId(competence.getId());
        //        dto.setCode(competence.getCode());
        //        dto.setDescription(competence.getDescription());
        final CompetenceDTO dto = super.populateDTOFromEntity(competence);
        dto.setInstrument(this.instrumentService.populateDTOFromEntity(competence.getInstrument()));
        return dto;
    }

    @Override
    public List<CompetenceDTO> getAllDTOForInstrument(final Long instrumentId) throws Exception {
        final List<Competence> entities = this.repository.findAllByInstrumentId(instrumentId);
        final List<CompetenceDTO> dtos = new ArrayList<CompetenceDTO>(entities.size());
        for(final Competence competence : entities) {
            dtos.add(this.populateDTOFromEntity(competence));
        }
        return dtos;
    }

    @Override
    public Competence populateEntityFromDTO(final CompetenceDTO dto) throws Exception {
        // TODO Auto-generated method stub
        return null;
    }

}
