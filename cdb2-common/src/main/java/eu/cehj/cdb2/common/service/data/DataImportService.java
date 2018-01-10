package eu.cehj.cdb2.common.service.data;

import org.springframework.stereotype.Service;

@Service
public interface DataImportService {
	
	public void importData(String fileContent);
	
}
