package eu.cehj.cdb2.business.service.data;

import org.springframework.stereotype.Service;

import eu.cehj.cdb2.entity.CDBTask;

@Service
public interface DataImportService {

    public void importData(String fileName, CDBTask task) throws Exception;

}
