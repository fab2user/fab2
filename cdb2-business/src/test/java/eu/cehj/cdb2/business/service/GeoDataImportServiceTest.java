package eu.cehj.cdb2.business.service;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import eu.cehj.cdb2.business.service.data.GeoDataImportService;

// @RunWith(SpringRunner.class)
// @SpringBootTest()
public class GeoDataImportServiceTest {

    protected Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private GeoDataImportService service;

    //    @Test
    public void testImportData() {

        try (final InputStream is = this.getClass().getResourceAsStream("FR.txt"); BufferedReader reader = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8));) {
            this.service.importData(reader);
        } catch (final Throwable e) {
            this.logger.error(e.getMessage(), e);
        }
    }
}
