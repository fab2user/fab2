package eu.cehj.cdb2.business.service.db;

import org.springframework.stereotype.Service;

import eu.cehj.cdb2.common.dto.InstrumentDTO;
import eu.cehj.cdb2.entity.Instrument;

@Service
public interface InstrumentService extends BaseService<Instrument, InstrumentDTO, Long>{

    //    @Override
    //    public InstrumentDTO populateDTOFromEntity(Instrument instrument);

}