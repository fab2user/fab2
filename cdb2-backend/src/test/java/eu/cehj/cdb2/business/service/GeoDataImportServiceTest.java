package eu.cehj.cdb2.business.service;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.stream.Collectors;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import eu.cehj.cdb2.business.service.data.DataImportService;

@RunWith(SpringRunner.class)
@SpringBootTest
public class GeoDataImportServiceTest {

    protected Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private DataImportService service;

    @Test
    public void testImportData() {

        String fileContent;
        //        final InputStream is = ClassLoader.class.getResourceAsStream("FR.txt");
        final InputStream is = this.getClass().getResourceAsStream("FR.txt");
        this.logger.debug(">>>>>>>>> IS/ " + is.toString());
        fileContent = new BufferedReader(new InputStreamReader(is)).lines().collect(Collectors.joining("\n"));
        this.logger.debug(fileContent.toString());
        this.service.importData(fileContent);
    }

}
