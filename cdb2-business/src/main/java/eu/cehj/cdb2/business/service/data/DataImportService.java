package eu.cehj.cdb2.business.service.data;

import java.io.IOException;

import org.springframework.stereotype.Service;

import eu.cehj.cdb2.entity.CDBTask;

@Service
public interface DataImportService {

    public void importData(String fileName, CDBTask task) throws IOException;

    public void processError(CDBTask task, String errorMessage, Exception e);

}
