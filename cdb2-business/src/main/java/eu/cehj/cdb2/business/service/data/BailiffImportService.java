package eu.cehj.cdb2.business.service.data;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import eu.cehj.cdb2.business.service.db.BailiffService;
import eu.cehj.cdb2.business.utils.BailiffImportModel;
import eu.cehj.cdb2.entity.Bailiff;

@Service
public class BailiffImportService {

    protected Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    BailiffService bailiffService;

    @Transactional
    public void importFile(final InputStream is, final String countryCode) throws Exception {
        try (final Workbook workbook = new XSSFWorkbook(is)) {
            final Sheet sheet = workbook.getSheet("detail");
            final Iterator<Row> it = sheet.rowIterator();
            it.next(); // We skip first row since it contains table's header
            while (it.hasNext()) {
                final Bailiff bailiff = this.populateBailiff(it.next(), this.getImportModel(countryCode));
                this.bailiffService.save(bailiff);
            }

        }
    }

    @Transactional
    public void export(final String countryCode) throws Exception {
        try (final Workbook workbook = new XSSFWorkbook()) {
            final Sheet sheet = workbook.createSheet("Bailiffs");
            final BailiffImportModel importModel = this.getImportModel(countryCode);
            final Row header = this.writeHeaders(sheet, importModel);
            final List<Bailiff> bailiffs = this.bailiffService.getAll();
            int index = 1;
            for (final Bailiff bailiff : bailiffs) {
                final Row currentRow = sheet.createRow(index);
                Cell cell = currentRow.createCell(0, CellType.STRING);
                cell.setCellValue(bailiff.getName());
                cell = currentRow.createCell(1, CellType.STRING);
                cell.setCellValue(bailiff.getPhone());
                cell = currentRow.createCell(2, CellType.STRING);
                cell.setCellValue(bailiff.getFax());
                cell = currentRow.createCell(3, CellType.STRING);
                cell.setCellValue(bailiff.getEmail());
                currentRow.createCell(4, CellType.STRING);
                cell.setCellValue(bailiff.getWebSite());
                index ++;
            }

            final File currDir = new File(".");
            final String path = currDir.getAbsolutePath();
            final String fileLocation = path.substring(0, path.length() - 1) + "temp.xlsx";

            final FileOutputStream outputStream = new FileOutputStream(fileLocation);
            workbook.write(outputStream);
        }
    }

    private Row writeHeaders(final Sheet sheet, final BailiffImportModel importModel)throws Exception {
        final Row row = sheet.createRow(0);
        Cell cell = row.createCell(0, CellType.STRING);
        cell.setCellValue("Name");
        cell = row.createCell(1, CellType.STRING);
        cell.setCellValue("Phone");
        cell = row.createCell(2, CellType.STRING);
        cell.setCellValue("Fax");
        cell = row.createCell(3, CellType.STRING);
        cell.setCellValue("Email");
        cell = row.createCell(4, CellType.STRING);
        cell.setCellValue("Web Site");
        return row;
    }

    private BailiffImportModel getImportModel(final String countryCode)throws Exception {
        final String rootPath = Thread.currentThread().getContextClassLoader().getResource("").getPath();
        final String path = rootPath + "xls_" + countryCode + ".properties";
        try(FileInputStream fis = new FileInputStream(path)){
            final Properties xlsProps = new Properties();
            xlsProps.load(fis);
            return new BailiffImportModel(xlsProps);
        }
    }

    private Bailiff populateBailiff(final Row row, final BailiffImportModel importModel) throws Exception {
        final Bailiff bailiff = new Bailiff();
        Cell cell = row.getCell(importModel.getName());
        final String name = cell.getStringCellValue();
        bailiff.setName(name);
        cell = row.getCell(importModel.getPhone());
        final String phone = cell.getStringCellValue();
        bailiff.setPhone(phone);
        cell = row.getCell(importModel.getFax());
        final String fax = cell.getStringCellValue();
        bailiff.setFax(fax);
        cell = row.getCell(importModel.getEmail());
        final String email = cell.getStringCellValue();
        bailiff.setEmail(email);
        cell = row.getCell(importModel.getWebSite());
        final String webSite = cell.getStringCellValue();
        bailiff.setWebSite(webSite);

        return bailiff;

    }

}



