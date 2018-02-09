package eu.cehj.cdb2.business.service.data;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Properties;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class BailiffImportService {

    protected Logger logger = LoggerFactory.getLogger(this.getClass());

    public void importFile(final InputStream is) throws Exception {
        //        try (ByteArrayInputStream is = (ByteArrayInputStream) file.getInputStream()) {
        final Workbook workbook = new XSSFWorkbook(is);

        final String rootPath = Thread.currentThread().getContextClassLoader().getResource("").getPath();
        final String xlsPath = rootPath + "xls_AT.properties";

        final Properties xlsProps = new Properties();

        try(FileInputStream fis = new FileInputStream(xlsPath)){
            xlsProps.load(fis);
            //TODO: populate an object or map with the properties. This system will allow to have different confs, related to xls files of the different countries
        }


        final Sheet sheet = workbook.getSheet("detail");
        for (final Row row : sheet) {
            //  row.get
            for(final Cell cell : row) {
                this.logger.debug(cell.getStringCellValue());
            }
        }
        //        }
    }

}
