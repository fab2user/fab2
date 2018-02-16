package eu.cehj.cdb2.business.service.db;

import org.springframework.stereotype.Service;

import eu.cehj.cdb2.common.dto.SynchronizationDTO;
import eu.cehj.cdb2.entity.Synchronization;

@Service
public interface SynchronizationService extends BaseService<Synchronization, SynchronizationDTO, Long> {

}