package eu.cehj.cdb2.business.service.db;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.querydsl.core.types.Predicate;

import eu.cehj.cdb2.common.dto.SynchronizationDTO;
import eu.cehj.cdb2.entity.Synchronization;

@Service
public interface SynchronizationService extends BaseService<Synchronization, SynchronizationDTO, Long> {

    public List<SynchronizationDTO> getLastByCountry() throws Exception;

    public Page<SynchronizationDTO> findAll(Predicate predicate, Pageable pageable) throws Exception;

}