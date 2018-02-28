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

import eu.cehj.cdb2.business.service.db.AddressService;
import eu.cehj.cdb2.business.service.db.BailiffService;
import eu.cehj.cdb2.business.service.db.MunicipalityService;
import eu.cehj.cdb2.business.utils.BailiffImportModel;
import eu.cehj.cdb2.entity.Address;
import eu.cehj.cdb2.entity.Bailiff;
import eu.cehj.cdb2.entity.Municipality;

@Service
public class BailiffImportService {

    protected Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    BailiffService bailiffService;

    @Autowired
    MunicipalityService municipalityService;

    @Autowired
    AddressService addressService;

    @Transactional
    public void importFile(final InputStream is, final String countryCode) throws Exception {
        try (final Workbook workbook = new XSSFWorkbook(is)) {
            final Sheet sheet = workbook.getSheet("detail");
            final Iterator<Row> it = sheet.rowIterator();
            it.next(); // We skip first row since it contains table's header
            while (it.hasNext()) {
                this.processBailiff(it.next(), this.getImportModel(countryCode));
            }

        }
    }

    @Transactional
    public String export(final String countryCode) throws Exception {
        try (final Workbook workbook = new XSSFWorkbook()) {
            final Sheet sheet = workbook.createSheet("Bailiffs");
            final BailiffImportModel importModel = this.getImportModel(countryCode);
            this.writeHeaders(sheet, importModel);
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
                index++;
            }

            final File currDir = new File(".");
            final String path = currDir.getAbsolutePath();
            final String fileLocation = path.substring(0, path.length() - 1) + "bailiffs.xlsx";

            final FileOutputStream outputStream = new FileOutputStream(fileLocation);
            workbook.write(outputStream);
            return fileLocation;
        }
    }

    private Row writeHeaders(final Sheet sheet, final BailiffImportModel importModel) throws Exception {
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

    private BailiffImportModel getImportModel(final String countryCode) throws Exception {
        final String rootPath = Thread.currentThread().getContextClassLoader().getResource("").getPath();
        final String path = rootPath + "xls_" + countryCode + ".properties";
        try (FileInputStream fis = new FileInputStream(path)) {
            final Properties xlsProps = new Properties();
            xlsProps.load(fis);
            return new BailiffImportModel(xlsProps);
        }
    }

    private void processBailiff(final Row row, final BailiffImportModel importModel) throws Exception {
        final Bailiff bailiff = this.populateBailiff(row, importModel);
        final Address address = this.populateAddress(row, importModel);
        bailiff.setAddress(address);
        this.bailiffService.save(bailiff);

    }

    private Bailiff populateBailiff(final Row row, final BailiffImportModel importModel) {
        final Bailiff bailiff = new Bailiff();
        Cell cell = row.getCell(importModel.getName());
        final String name = cell.getStringCellValue().trim();
        bailiff.setName(name);
        cell = row.getCell(importModel.getPhone());
        final String phone = cell.getStringCellValue().trim();
        bailiff.setPhone(phone);
        cell = row.getCell(importModel.getFax());
        final String fax = cell.getStringCellValue().trim();
        bailiff.setFax(fax);
        cell = row.getCell(importModel.getEmail());
        final String email = cell.getStringCellValue().trim();
        bailiff.setEmail(email);
        cell = row.getCell(importModel.getWebSite());
        final String webSite = cell.getStringCellValue().trim();
        bailiff.setWebSite(webSite);
        return bailiff;
    }

    private Address populateAddress(final Row row, final BailiffImportModel importModel) throws Exception {
        Cell cell = row.getCell(importModel.getMunicipality());
        final String name = cell.getStringCellValue().trim();
        cell = row.getCell(importModel.getPostalCode());
        final String postalCode = cell.getStringCellValue().trim();
        Municipality municipality = this.municipalityService.getByPostalCodeAndName(postalCode, name);
        if (municipality == null) {
            municipality = new Municipality();
            municipality.setName(name);
            municipality.setPostalCode(postalCode);
            municipality = this.municipalityService.save(municipality);
        }
        final Address address = new Address();
        cell = row.getCell(importModel.getAddress());
        address.setAddress(cell.getStringCellValue().trim());
        address.setMunicipality(municipality);
        return this.addressService.save(address);
    }

}
