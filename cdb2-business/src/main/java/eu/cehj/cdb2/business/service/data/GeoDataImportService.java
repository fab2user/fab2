package eu.cehj.cdb2.business.service.data;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import eu.cehj.cdb2.business.service.db.CDBTaskService;
import eu.cehj.cdb2.common.service.StorageService;
import eu.cehj.cdb2.entity.CDBTask;
import eu.cehj.cdb2.entity.CDBTask.Status;

/**
 * Imports areas geo data into database, from http://www.geonames.org/.
 */
@Service
@Scope("prototype")
public class GeoDataImportService implements DataImportService {

    private static final Logger LOGGER = LoggerFactory.getLogger(GeoDataImportService.class);

    private CDBTask task;

    @Autowired
    private StorageService storageService;

    @Autowired
    private GeoDataPersistenceService geoDataPersistenceService;

    @Autowired
    private CDBTaskService taskService;

    @Override
    @Async
    @Transactional
    public void importData(final String fileName, final CDBTask task) throws IOException {
        this.task = task;
        task.setStatus(CDBTask.Status.IN_PROGRESS);
        this.taskService.save(task);
        try (final BufferedReader reader = new BufferedReader(new InputStreamReader(this.storageService.loadFile(fileName).getInputStream()))) {

            List<GeoDataStructure> dataStructures;
            try {
                dataStructures = this.processLines(reader);
                this.geoDataPersistenceService.persistData(dataStructures);
            } catch (final Exception e) {
                LOGGER.error(String.format("Geoname import with id #%d failed with error %s", task.getId(), e.getMessage()), e);
                task.setEndDate(new Date());
                task.setType(CDBTask.Type.GEONAME_IMPORT);
                task.setStatus(Status.ERROR);
                task.setMessage(e.getMessage());
                this.taskService.save(task);
            }
            if(LOGGER.isInfoEnabled()) {
                LOGGER.info(String.format("Geoname import with id #%d successful", task.getId()));
            }
            task.setEndDate(new Date());
            task.setStatus(Status.OK);
            this.taskService.save(task);
        }finally {
            this.storageService.deleteFile(fileName);
        }
    }

    public List<GeoDataStructure> processLines(final BufferedReader reader) {
        this.task.setStatus(Status.IN_PROGRESS);
        this.taskService.save(this.task);
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
        if(LOGGER.isDebugEnabled()) {
            LOGGER.debug(dataStructure.toString());
        }
        return dataStructure;
    }

    public CDBTask getTask() {
        return this.task;
    }

}
