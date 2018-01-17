package eu.cehj.cdb2.business.service.db.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import eu.cehj.cdb2.business.dao.InstrumentRepository;
import eu.cehj.cdb2.business.service.db.InstrumentService;
import eu.cehj.cdb2.common.dto.InstrumentDTO;
import eu.cehj.cdb2.entity.Instrument;

@Service
public class InstrumentServiceImpl extends BaseServiceImpl<Instrument, Long> implements InstrumentService {

    @Autowired
    private InstrumentRepository repository;


    @Override
    public List<InstrumentDTO> getAllDTO() throws Exception {

        final List<Instrument> instruments = (List<Instrument>) this.repository.findAll();
        final List<InstrumentDTO> dtos = new ArrayList<InstrumentDTO>(instruments.size());
        instruments.forEach( instrument -> {
            final InstrumentDTO dto = this.populateDTOFromEntity(instrument);
            dtos.add(dto);
        });
        return dtos;
    }

    public InstrumentDTO populateDTOFromEntity(final Instrument instrument) {
        final InstrumentDTO dto = new InstrumentDTO();
        dto.setId(instrument.getId());
        dto.setCode(instrument.getCode());
        dto.setDescription(instrument.getDescription());
        return dto;
    }

}
