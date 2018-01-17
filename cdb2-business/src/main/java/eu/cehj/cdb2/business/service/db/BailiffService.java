package eu.cehj.cdb2.business.service.db;

import java.util.List;

import org.springframework.stereotype.Service;

import eu.cehj.cdb2.business.service.search.model.BailiffSearch;
import eu.cehj.cdb2.common.dto.BailiffDTO;
import eu.cehj.cdb2.entity.Bailiff;

@Service
public interface BailiffService extends BaseService<Bailiff, Long> {

    public BailiffDTO save(BailiffDTO bailiffDTO) throws Exception;

    public List<BailiffDTO> getAllDTO() throws Exception;

    public BailiffDTO getDTO(Long id) throws Exception;

    public Iterable<BailiffDTO> searchDTO(final BailiffSearch searchParams)throws Exception;

}