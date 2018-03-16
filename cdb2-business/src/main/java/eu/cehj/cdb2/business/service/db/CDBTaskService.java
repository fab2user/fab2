package eu.cehj.cdb2.business.service.db;

import org.springframework.stereotype.Service;

import eu.cehj.cdb2.common.dto.CDBTaskDTO;
import eu.cehj.cdb2.entity.CDBTask;

@Service
public interface CDBTaskService extends BaseService<CDBTask, CDBTaskDTO, Long> {

}