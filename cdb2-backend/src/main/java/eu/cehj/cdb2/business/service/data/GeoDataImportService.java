package eu.cehj.cdb2.business.service.data;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * Imports areas geo data into database, from http://www.geonames.org/.
 */
@Service
public class GeoDataImportService implements DataImportService {

    protected Logger logger = LoggerFactory.getLogger(this.getClass());

    // @Autowired
    //    private GeoDataPersistenceService geoDataPersistenceService;

    @Override
    public void importData(final String fileContent) {
        final List<GeoDataStructure> dataStructures  = this.processLines(fileContent);
        final GeoDataPersistenceService geoDataPersistenceService = new GeoDataPersistenceService();
        geoDataPersistenceService.persistData(dataStructures);
        //        dataStructures.stream().forEach(structure ->{
        //            this.logger.debug(structure.toString());
        //        });
    }

    public List<GeoDataStructure> processLines(final String fileContent) {
        final List<GeoDataStructure> dataStructures = new ArrayList<>();
        try {
            final InputStream is = new ByteArrayInputStream(fileContent.getBytes(StandardCharsets.UTF_8.name()));
            new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8.name())).lines().forEach(line -> {
                final GeoDataStructure dataStructure = this.processLine(line);
                if(dataStructure != null) {
                    dataStructures.add(dataStructure);
                }
            });
        } catch (final IOException e) {
            this.logger.error(e.getMessage(), e);
        }
        return dataStructures;
    }

    public GeoDataStructure processLine(final String line) {
        final GeoDataStructure dataStructure = new GeoDataStructure();
        final String[] fields = line.split("\t");
        dataStructure.setCountryCode(fields[0]);
        dataStructure.setZipCode(fields[1]);
        dataStructure.setCityName(fields[2]);
        dataStructure.setMajorAreaName(fields[3]);
        dataStructure.setMajorAreaCode(fields[4]);
        dataStructure.setMiddleAreaName(fields[5]);
        dataStructure.setMiddleAreaCode(fields[6]);
        dataStructure.setMinorAreaName(fields[7]);
        dataStructure.setMinorAreaCode(fields[8]);
        dataStructure.setxPos(fields[9]);
        dataStructure.setyPos(fields[10]);
        this.logger.debug(dataStructure.toString());
        return dataStructure;
    }

}
