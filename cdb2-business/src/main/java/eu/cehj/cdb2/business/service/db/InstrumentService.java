package eu.cehj.cdb2.business.service.db;

import java.util.List;

import org.springframework.stereotype.Service;

import eu.cehj.cdb2.common.dto.InstrumentDTO;
import eu.cehj.cdb2.entity.Instrument;

@Service
public interface InstrumentService extends BaseService<Instrument, Long>{

    public List<InstrumentDTO> getAllDTO() throws Exception;

    public InstrumentDTO populateDTOFromEntity(Instrument instrument) throws Exception;

}