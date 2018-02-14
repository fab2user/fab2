package eu.cehj.cdb2.business.service.db.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import eu.cehj.cdb2.business.dao.InstrumentRepository;
import eu.cehj.cdb2.business.service.db.CompetenceService;
import eu.cehj.cdb2.business.service.db.InstrumentService;
import eu.cehj.cdb2.common.dto.InstrumentDTO;
import eu.cehj.cdb2.entity.Instrument;

@Service
public class InstrumentServiceImpl extends BaseServiceImpl<Instrument, InstrumentDTO, Long> implements InstrumentService {

    @Autowired
    private InstrumentRepository repository;

    @Autowired
    private CompetenceService competenceService;


    @Override
    public List<InstrumentDTO> getAllDTO() throws Exception {

        final List<Instrument> instruments = this.repository.findAll();
        final List<InstrumentDTO> dtos = new ArrayList<InstrumentDTO>(instruments.size());
        final Iterator<Instrument> it = instruments.iterator();
        while(it.hasNext()) {
            final Instrument instrument = it.next();
            dtos.add( this.populateDTOFromEntity(instrument));
        }
        return dtos;
    }

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
    public InstrumentDTO save(final InstrumentDTO dto) throws Exception {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public InstrumentDTO getDTO(final Long id) throws Exception {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Instrument populateEntityFromDTO(final InstrumentDTO dto) throws Exception {
        // TODO Auto-generated method stub
        return null;
    }

}
