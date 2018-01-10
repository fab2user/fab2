package eu.cehj.cdb2.common.test.service;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Paths;
import java.util.stream.Collectors;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import eu.cehj.cdb2.common.service.data.DataImportService;
import eu.cehj.cdb2.common.service.data.GeoDataImportService;

public class GeoDataImportServiceTest {

    protected Logger logger = LoggerFactory.getLogger(this.getClass());

    @Test
    public void testImportData() {

        final DataImportService service = new GeoDataImportService();
        String fileContent;
        this.logger.debug(Paths.get(".").toAbsolutePath().toString());
        final InputStream is = ClassLoader.class.getResourceAsStream("/eu/cehj/cdb2/common/resources/FR.txt");
        fileContent = new BufferedReader(new InputStreamReader(is)).lines().collect(Collectors.joining("\n"));
        this.logger.debug(fileContent.toString());
        service.importData(fileContent);
    }

}
