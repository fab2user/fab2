package eu.cehj.cdb2.business.service.db.impl;

import org.springframework.stereotype.Service;

import eu.cehj.cdb2.business.service.db.InstrumentService;
import eu.cehj.cdb2.common.dto.InstrumentDTO;
import eu.cehj.cdb2.entity.Instrument;

@Service
public class InstrumentServiceImpl extends BaseServiceImpl<Instrument, InstrumentDTO, Long> implements InstrumentService {

    @Override
    public InstrumentDTO populateDTOFromEntity(final Instrument instrument) throws Exception {
        final InstrumentDTO dto = new InstrumentDTO();
        dto.setId(instrument.getId());
        dto.setCode(instrument.getCode());
        dto.setDescription(instrument.getDescription());
        //        dto.setCompetences(this.competenceService.getAllDTOForInstrument(instrument.getId()));
        return dto;
    }

    @Override
    public Instrument populateEntityFromDTO(final InstrumentDTO dto) throws Exception {
        // TODO Auto-generated method stub
        return null;
    }

}
