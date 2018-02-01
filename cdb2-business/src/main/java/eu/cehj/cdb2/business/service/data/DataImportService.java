package eu.cehj.cdb2.business.service.data;

import java.io.BufferedReader;

import org.springframework.stereotype.Service;

@Service
public interface DataImportService {

    public void importData(BufferedReader reader);

}
