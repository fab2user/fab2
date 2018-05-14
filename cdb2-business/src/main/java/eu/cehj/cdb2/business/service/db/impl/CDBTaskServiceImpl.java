package eu.cehj.cdb2.business.service.db.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import eu.cehj.cdb2.business.dao.CDBTaskRepository;
import eu.cehj.cdb2.business.service.db.CDBTaskService;
import eu.cehj.cdb2.common.dto.CDBTaskDTO;
import eu.cehj.cdb2.entity.CDBTask;

@Service
public class CDBTaskServiceImpl extends BaseServiceImpl<CDBTask, CDBTaskDTO, Long, CDBTaskRepository> implements CDBTaskService {

    @Autowired
    private ModelMapper mapper;

    @Override
    public CDBTask populateEntityFromDTO(final CDBTaskDTO dto) {
        final CDBTask entity = dto.getId() == null ? new CDBTask() : this.get(dto.getId());
        this.mapper.map(dto, entity);
        return entity;
    }

    @Override
    public CDBTaskDTO populateDTOFromEntity(final CDBTask entity) {
        return this.mapper.map(entity, CDBTaskDTO.class);
    }
}
