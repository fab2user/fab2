package eu.cehj.cdb2.business.service.db;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.querydsl.core.types.Predicate;

import eu.cehj.cdb2.common.dto.BailiffDTO;
import eu.cehj.cdb2.common.dto.BailiffExportDTO;
import eu.cehj.cdb2.common.dto.cdb.CDBResponse;
import eu.cehj.cdb2.entity.Bailiff;

@Service
public interface BailiffService extends BaseService<Bailiff, BailiffDTO, Long> {

    public List<BailiffDTO> findAll(Predicate predicate, Pageable pageable);

    public List<BailiffDTO> getAllEvenDeletedDTO();

    public List<BailiffExportDTO> getAllForExport();

    public BailiffExportDTO populateExportDTOFromEntity(Bailiff entity);

    public List<BailiffDTO> populateDTOsFromCDB(final CDBResponse cdbResponse);

}