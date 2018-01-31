package eu.cehj.cdb2.business.service.db;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.querydsl.core.types.Predicate;

import eu.cehj.cdb2.common.dto.BailiffDTO;
import eu.cehj.cdb2.entity.Bailiff;

@Service
public interface BailiffService extends BaseService<Bailiff, Long> {

    public BailiffDTO save(BailiffDTO bailiffDTO) throws Exception;

    public List<BailiffDTO> getAllDTO() throws Exception;

    public BailiffDTO getDTO(Long id) throws Exception;

    public Page<BailiffDTO> findAll(Predicate predicate, Pageable pageable) throws Exception;

}