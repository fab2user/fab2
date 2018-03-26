package eu.cehj.cdb2.business.service.db.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import eu.cehj.cdb2.business.dao.InstrumentRepository;
import eu.cehj.cdb2.business.service.db.CompetenceService;
import eu.cehj.cdb2.business.service.db.InstrumentService;
import eu.cehj.cdb2.common.dto.CompetenceDTO;
import eu.cehj.cdb2.common.dto.InstrumentDTO;
import eu.cehj.cdb2.entity.Competence;
import eu.cehj.cdb2.entity.Instrument;

@Service
public class InstrumentServiceImpl extends BaseServiceImpl<Instrument, InstrumentDTO, Long, InstrumentRepository> implements InstrumentService {



    @Autowired
    private CompetenceService competenceService;

    @Override
    public InstrumentDTO populateDTOFromEntity(final Instrument instrument) throws Exception {
        final InstrumentDTO dto = new InstrumentDTO();
        dto.setId(instrument.getId());
        dto.setCode(instrument.getCode());
        dto.setDescription(instrument.getDescription());
        final List<Competence> comps = instrument.getCompetences();
        final List<CompetenceDTO> compDTOs = comps.stream().map(c -> {
            try {
                return this.competenceService.populateDTOFromEntity(c);
            } catch (final Exception e) {
                this.getLogger().error(e.getMessage(),e);
                return null;
            }
        }).collect(Collectors.toList());
        dto.setCompetences(compDTOs);
        return dto;
    }

    @Override
    public Instrument populateEntityFromDTO(final InstrumentDTO dto) throws Exception {
        // TODO Auto-generated method stub
        return null;
    }

}
