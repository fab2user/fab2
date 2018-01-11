package eu.cehj.cdb2.business.service.data;

import java.util.List;

import org.springframework.stereotype.Service;

@Service
public interface DataPersistenceService {
    public void persistData(List<GeoDataStructure> dataStrucures);
}
