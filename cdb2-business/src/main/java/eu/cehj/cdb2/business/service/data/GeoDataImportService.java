package eu.cehj.cdb2.business.service.data;

import java.io.BufferedReader;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Imports areas geo data into database, from http://www.geonames.org/.
 */
@Service
public class GeoDataImportService implements DataImportService {

    protected Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private GeoDataPersistenceService geoDataPersistenceService;

    @Override
    public void importData(final BufferedReader reader) {
        final List<GeoDataStructure> dataStructures  = this.processLines(reader);
        this.geoDataPersistenceService.persistData(dataStructures);
    }

    public List<GeoDataStructure> processLines(final BufferedReader reader) {
        final List<GeoDataStructure> dataStructures = new ArrayList<>();
        reader.lines().forEach(line -> {
            final GeoDataStructure dataStructure = this.processLine(line);
            if(dataStructure != null) {
                dataStructures.add(dataStructure);
            }
        });
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
