package eu.cehj.cdb2.business.service.db.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import eu.cehj.cdb2.business.dao.CompetenceRepository;
import eu.cehj.cdb2.business.service.db.CompetenceService;
import eu.cehj.cdb2.business.service.db.InstrumentService;
import eu.cehj.cdb2.common.dto.CompetenceDTO;
import eu.cehj.cdb2.entity.Competence;

@Service
public class CompetenceServiceImpl extends BaseServiceImpl<Competence, Long> implements CompetenceService {

    @Autowired
    private CompetenceRepository repository;

    @Autowired
    private InstrumentService instrumentService;

    @Override
    public List<CompetenceDTO> getAllDTO() throws Exception {
        //FIXME
        final List<Competence> competences = this.repository.findAll();
        final List<CompetenceDTO> dtos = new ArrayList<CompetenceDTO>(competences.size());
        final Iterator<Competence> it = competences.iterator();
        while(it.hasNext()) {
            dtos.add(this.populateDTOFromEntity(it.next()));
        }
        return dtos;
    }

    public CompetenceDTO populateDTOFromEntity(final Competence competence) throws Exception {
        final CompetenceDTO dto = new CompetenceDTO();
        dto.setId(competence.getId());
        dto.setCode(competence.getCode());
        dto.setDescription(competence.getDescription());
        dto.setInstrument(this.instrumentService.populateDTOFromEntity(competence.getInstrument()));
        return dto;
    }

    @Override
    public CompetenceDTO getDTO(final Long id)throws Exception{
        final Competence entity = this.get(id);
        return this.populateDTOFromEntity(entity);
    }

    @Override
    public List<CompetenceDTO> getAllDTOForInstrument(final Long instrumentId) throws Exception {
        //FIXME
        final List<Competence> entities = this.repository.findAllByInstrumentId(instrumentId);
        final List<CompetenceDTO> dtos = new ArrayList<CompetenceDTO>(entities.size());
        final Iterator<Competence> it = entities.iterator();
        while(it.hasNext()) {
            dtos.add(this.populateDTOFromEntity(it.next()));
        }
        return dtos;
    }

}
