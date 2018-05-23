package eu.cehj.cdb2.web.service;

import static org.apache.commons.lang3.StringUtils.*;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Row.MissingCellPolicy;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import eu.cehj.cdb2.business.service.db.AddressService;
import eu.cehj.cdb2.business.service.db.BailiffService;
import eu.cehj.cdb2.business.service.db.CDBTaskService;
import eu.cehj.cdb2.business.service.db.LanguageService;
import eu.cehj.cdb2.business.service.db.MunicipalityService;
import eu.cehj.cdb2.common.exception.dto.CDBException;
import eu.cehj.cdb2.common.service.StorageService;
import eu.cehj.cdb2.entity.Address;
import eu.cehj.cdb2.entity.Bailiff;
import eu.cehj.cdb2.entity.CDBTask;
import eu.cehj.cdb2.entity.Language;
import eu.cehj.cdb2.entity.Municipality;
import eu.cehj.cdb2.web.config.BailiffImportConfig;

@Service
public class BailiffImportService {

    private static final Logger LOGGER = LoggerFactory.getLogger(BailiffImportService.class);

    @Autowired
    private BailiffService bailiffService;

    @Autowired
    private LanguageService langService;

    @Autowired
    private MunicipalityService municipalityService;

    @Autowired
    private AddressService addressService;

    @Autowired
    private StorageService storageService;

    @Autowired
    private CDBTaskService taskService;

    private static final String[] cellTitles = {
            "Name", "Lang", "Address 1", "Address 2", "Postal Code", "City", "Phone", "Fax", "Email", "Web Site"
    };

    @Value("${bailiff.xls.file.tab}")
    private String bailiffTabName;

    @Autowired
    private BailiffImportConfig bailiffImportConfig;

    private Language languageOfDetails;

    @Async
    public void importFile(final String fileName, final String countryCode, final CDBTask task) throws IOException{
        try {
            task.setStatus(CDBTask.Status.IN_PROGRESS);
            this.taskService.save(task);
            this.languageOfDetails = this.getDefaultLangOfDetails();
            final Resource file = this.storageService.loadFile(fileName);

            try (final Workbook workbook = WorkbookFactory.create(file.getInputStream())) {
                workbook.setMissingCellPolicy(MissingCellPolicy.CREATE_NULL_AS_BLANK);
                final Sheet sheet = workbook.getSheet(this.bailiffTabName);
                if(sheet == null) {
                    throw new CDBException(String.format("No tab named '%s' found in provided excel document.", this.bailiffTabName));
                }
                final Iterator<Row> it = sheet.rowIterator();
                it.next(); // We skip first row since it contains table's header
                while (it.hasNext()) {
                    this.processBailiff(it.next());
                }
                task.setStatus(CDBTask.Status.OK);
                this.taskService.save(task);
            }
        } catch (final Exception e) { //Catch Exception because we'll handle all exceptions the same way
            task.setStatus(CDBTask.Status.ERROR);
            String message = e.getMessage();
            if (isBlank(message)) {
                message = "Unknown server error while processing xls import file.";
            }
            task.setMessage(message);
            this.taskService.save(task);
            throw new CDBException(message, e);
        }finally {
            this.storageService.deleteFile(fileName);
        }
    }

    /**
     *
     * As asked by the client, we set english as default lang. of details for all bailiffs.
     * @return
     * @throws Exception
     */
    private Language getDefaultLangOfDetails() {
        return this.langService.getLangByCode("EN");
    }

    @Transactional
    public String export(final String countryCode) throws IOException {
        try (final Workbook workbook = new XSSFWorkbook()) {
            final Sheet sheet = workbook.createSheet("Bailiffs");
            this.writeHeaders(sheet);
            final List<Bailiff> bailiffs = this.bailiffService.getAll();
            int index = 1;

            for (final Bailiff bailiff : bailiffs) {

                final String[] bailiffValues = this.buildValues(bailiff);
                this.writeRow(sheet, index, bailiffValues);
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

    private String[] buildValues(final Bailiff bailiff) {

        //@formatter:off
        return new String[] {
                defaultIfBlank(bailiff.getName(), ""),
                bailiff.getLanguages().stream().map(Language::getLanguage).collect(Collectors.joining(", ")),
                Optional
                .of(bailiff)
                .map(Bailiff::getAddress)
                .map(Address::getAddress1)
                .orElseGet(() -> ""),
                Optional
                .of(bailiff)
                .map(Bailiff::getAddress)
                .map(Address::getAddress2)
                .orElseGet(() -> ""),
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

    private void writeRow(final Sheet sheet, final int index, final String[] bailiffValues) {
        final Row row = sheet.createRow(index);
        for (int i = 0; i < bailiffValues.length; i++) {
            final Cell cell = row.createCell(i, CellType.STRING);
            cell.setCellValue(bailiffValues[i]);
        }
    }

    private Row writeHeaders(final Sheet sheet){
        final Row row = sheet.createRow(0);
        for (int ind = 0; ind < cellTitles.length; ind++) {
            final Cell cell = row.createCell(ind, CellType.STRING);
            cell.setCellValue(cellTitles[ind]);
        }
        return row;
    }

    private void processBailiff(final Row row){
        final Bailiff bailiff = this.populateBailiff(row);
        final Address address = this.populateAddress(row);
        bailiff.setAddress(address);
        bailiff.getLangOfDetails().add(this.languageOfDetails);
        this.bailiffService.save(bailiff);

    }

    private Bailiff populateBailiff(final Row row) {
        try {
            final Bailiff bailiff = new Bailiff();
            Cell cell = row.getCell(this.bailiffImportConfig.getName());
            final String name = this.getCellValue(cell);
            bailiff.setName(name);
            cell = row.getCell(this.bailiffImportConfig.getPhone());
            final String phone = this.getCellValue(cell);
            bailiff.setPhone(phone);
            cell = row.getCell(this.bailiffImportConfig.getFax());
            final String fax = this.getCellValue(cell);
            bailiff.setFax(fax);
            cell = row.getCell(this.bailiffImportConfig.getEmail());
            final String email = this.getCellValue(cell);
            bailiff.setEmail(email);
            cell = row.getCell(this.bailiffImportConfig.getWebSite());
            final String webSite = this.getCellValue(cell);
            bailiff.setWebSite(webSite);
            return bailiff;
        } catch (final Exception e) {
            throw new CDBException("Error when importing bailiff data from Excel file. Please check that the file is correct.", e);
        }
    }

    private Address populateAddress(final Row row) {
        LOGGER.debug("Processing row #" + row.getRowNum());
        Cell cell = row.getCell(this.bailiffImportConfig.getMunicipality());
        final String name = this.getCellValue(cell);
        cell = row.getCell(this.bailiffImportConfig.getPostalCode());
        final String postalCode = this.getCellValue(cell);
        Municipality municipality = this.municipalityService.getByPostalCodeAndName(postalCode, name);
        if (municipality == null) {
            municipality = new Municipality();
            municipality.setName(name);
            municipality.setPostalCode(postalCode);
            municipality = this.municipalityService.save(municipality);
        }
        final Address address = new Address();
        cell = row.getCell(this.bailiffImportConfig.getAddress1());
        address.setAddress1(this.getCellValue(cell));
        cell = row.getCell(this.bailiffImportConfig.getAddress2());
        address.setAddress2(this.getCellValue(cell));
        address.setMunicipality(municipality);
        return this.addressService.save(address);
    }

    // LibreOffice treats cell as numeric if content is number: hence this method which processes correctly the cell in both cases
    private String getCellValue(final Cell cell){
        try {
            return cell.getStringCellValue().trim();
        }catch(final Exception e) {
            try {
                final double numericValue = cell.getNumericCellValue();
                final DecimalFormat decimalFormat = new DecimalFormat("#.##");
                return decimalFormat.format(numericValue);
            }catch(final Exception ex) {
                LOGGER.error(ex.getMessage(), e);
            }
        }
        return "";
    }

}
