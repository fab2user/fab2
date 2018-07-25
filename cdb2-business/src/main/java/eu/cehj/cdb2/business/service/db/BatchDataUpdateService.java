package eu.cehj.cdb2.business.service.db;

import java.util.List;

import org.springframework.stereotype.Service;

import eu.cehj.cdb2.common.dto.BatchDataUpdateDTO;
import eu.cehj.cdb2.entity.BatchDataUpdate;

@Service
public interface BatchDataUpdateService extends BaseService<BatchDataUpdate, BatchDataUpdateDTO, Long> {

    public List<BatchDataUpdate> getByCountry(long countryId);

    public List<BatchDataUpdateDTO> getDTOsByCountry(long countryId);

    public List<BatchDataUpdate> save(List<BatchDataUpdateDTO> bduDTOs, Long countryOfSyncId);
}