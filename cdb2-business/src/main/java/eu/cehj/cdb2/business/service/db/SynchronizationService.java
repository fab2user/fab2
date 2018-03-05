package eu.cehj.cdb2.business.service.db;

import java.util.List;

import org.springframework.stereotype.Service;

import eu.cehj.cdb2.common.dto.SynchronizationDTO;
import eu.cehj.cdb2.entity.Synchronization;

@Service
public interface SynchronizationService extends BaseService<Synchronization, SynchronizationDTO, Long> {

    public List<SynchronizationDTO> getLastByCountry() throws Exception;

}