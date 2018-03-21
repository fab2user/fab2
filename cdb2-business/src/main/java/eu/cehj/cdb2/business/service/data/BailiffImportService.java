package eu.cehj.cdb2.business.service.data;

import static org.apache.commons.lang3.StringUtils.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.Properties;
import java.util.stream.Collectors;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import eu.cehj.cdb2.business.exception.CDBException;
import eu.cehj.cdb2.business.service.db.AddressService;
import eu.cehj.cdb2.business.service.db.BailiffService;
import eu.cehj.cdb2.business.service.db.MunicipalityService;
import eu.cehj.cdb2.business.utils.BailiffImportModel;
import eu.cehj.cdb2.common.service.StorageService;
import eu.cehj.cdb2.common.service.task.TaskStatus;
import eu.cehj.cdb2.entity.Address;
import eu.cehj.cdb2.entity.Bailiff;
import eu.cehj.cdb2.entity.Municipality;

@Service
public class BailiffImportService {

    protected Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private BailiffService bailiffService;

    @Autowired
    private MunicipalityService municipalityService;

    @Autowired
    private AddressService addressService;

    @Autowired
    private StorageService storageService;

    private final String[] cellTitles = {
            "ID", "Name", "Lang", "Address", "Postal Code", "Municipality", "Phone", "Fax", "Email", "Web Site"
    };

    @Async
    @Transactional
    public void importFile(final String fileName, final String countryCode, final TaskStatus task) throws Exception {
        try {
            final Resource file = this.storageService.loadFile(fileName);
            try (final Workbook workbook = new XSSFWorkbook(file.getInputStream())) {
                final Sheet sheet = workbook.getSheet("detail");
                final Iterator<Row> it = sheet.rowIterator();
                it.next(); // We skip first row since it contains table's header
                while (it.hasNext()) {
                    this.processBailiff(it.next(), this.getImportModel(countryCode));
                }
                task.setStatus(TaskStatus.Status.OK);
            }
        } catch (final Exception e) {
            task.setStatus(TaskStatus.Status.ERROR);
            String message = e.getMessage();
            if (isBlank(message)) {
                message = "Unknown server error while processing xls import file.";
            }
            task.setMessage(message);
            throw e;
        } finally {
            this.storageService.deleteFile(fileName);
        }
    }

    @Transactional
    public String export(final String countryCode) throws Exception {
        try (final Workbook workbook = new XSSFWorkbook()) {
            final Sheet sheet = workbook.createSheet("Bailiffs");
            this.writeHeaders(sheet);
            final List<Bailiff> bailiffs = this.bailiffService.getAll();
            int index = 1;

            for (final Bailiff bailiff : bailiffs) {

                final String[] bailiffValues = this.buildValues(bailiff);
                this.writeRow(sheet, index, bailiffValues);
                index ++;
            }

            final File currDir = new File(".");
            final String path = currDir.getAbsolutePath();
            final String fileLocation = path.substring(0, path.length() - 1) + "bailiffs.xlsx";

            final FileOutputStream outputStream = new FileOutputStream(fileLocation);
            workbook.write(outputStream);
            return fileLocation;
        }
    }

    private String[] buildValues(final Bailiff bailiff) {
        //@formatter:off
        return new String[] {
                Long.toString(bailiff.getId()),
                defaultIfBlank(bailiff.getName(), ""),
                this.getLangsForBailiff(bailiff),
                Optional
                .of(bailiff)
                .map(Bailiff::getAddress)
                .map(Address::getAddress)
                .orElseGet(() -> ""),
                this.getLangsForBailiff(bailiff),
                Optional
                .of(bailiff)
                .map(Bailiff::getAddress)
                .map(Address::getMunicipality)
                .map(Municipality::getPostalCode)
                .orElseGet(() -> ""),
                Optional
                .of(bailiff)
                .map(Bailiff::getAddress)
                .map(Address::getMunicipality)
                .map(Municipality::getName)
                .orElseGet(() -> ""),
                defaultIfBlank(bailiff.getPhone(), ""),
                defaultIfBlank(bailiff.getFax(), ""),
                defaultIfBlank(bailiff.getEmail(), ""),
                defaultIfBlank(bailiff.getWebSite(), ""),
        };
        //@formatter:on
    }

    private String getLangsForBailiff(final Bailiff bailiff) {
        return bailiff.getLanguages().stream().map(l -> l.getLanguage()).collect(Collectors.joining(", "));
    }

    private void writeRow(final Sheet sheet, final int index, final String[] bailiffValues) {
        final Row row = sheet.createRow(index);
        for (int i = 0; i < bailiffValues.length; i++) {
            final Cell cell = row.createCell(i, CellType.STRING);
            cell.setCellValue(bailiffValues[i]);
        }
    }

    private Row writeHeaders(final Sheet sheet) throws Exception {
        final Row row = sheet.createRow(0);
        for (int ind = 0; ind < this.cellTitles.length; ind++) {
            final Cell cell = row.createCell(ind, CellType.STRING);
            cell.setCellValue(this.cellTitles[ind]);
        }
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

    private Bailiff populateBailiff(final Row row, final BailiffImportModel importModel)throws Exception {
        try {
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
        } catch (final Exception e) {
            throw new CDBException("Error when importing bailiff data from Excel file. Please check that the file is correct.");
        }
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
