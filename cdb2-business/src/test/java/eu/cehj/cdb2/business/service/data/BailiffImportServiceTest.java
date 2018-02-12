package eu.cehj.cdb2.business.service.data;

import java.io.InputStream;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

@ActiveProfiles("test")
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.NONE)
//@TestPropertySource(locations="classpath:application-test.properties")
public class BailiffImportServiceTest {

    protected Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private BailiffImportService service;

    @Test
    public void testImportFile() {

        try (final InputStream is = this.getClass().getResourceAsStream("AT.xlsx");) {
            this.service.importFile(is, "AT");
        } catch (final Throwable e) {
            this.logger.error(e.getMessage(), e);
        }
    }

    @Test
    public void testExportFile() {
        try {
            this.service.export("AT");
        } catch (final Throwable e) {
            this.logger.error(e.getMessage(), e);
        }
    }
}
