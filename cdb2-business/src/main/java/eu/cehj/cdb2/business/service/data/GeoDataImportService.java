package eu.cehj.cdb2.business.service.data;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import eu.cehj.cdb2.business.service.db.CDBTaskService;
import eu.cehj.cdb2.common.exception.dto.CDBException;
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
	private GeoDataUpdatePersistenceService geoDataUpdatePersistenceService;

	@Autowired
	private CDBTaskService taskService;

	@Override
	@Async
	//    I had to remove @Transactional. It slowed the process BIG TIME & errors weren't written to DB.
	public void importData(final String fileName, final CDBTask task) throws IOException {
		this.task = task;
		task.setStatus(CDBTask.Status.IN_PROGRESS);
		this.taskService.save(task);
		try (final BufferedReader reader = new BufferedReader(new InputStreamReader(this.storageService.loadFile(fileName).getInputStream(),StandardCharsets.UTF_8))) {

			List<GeoDataStructure> dataStructures;
			try {
				dataStructures = this.processLines(reader);
				this.geoDataPersistenceService.persistData(dataStructures);

			} catch (final Exception e) {
				if(e.getClass().isAssignableFrom(ArrayIndexOutOfBoundsException.class)) {

					this.processError(task, String.format("Geoname import with id #%d failed with error \"Array Index Out Of Bound Exception\".%sIt indicates the import file is in a wrong format.", task.getId(), System.lineSeparator()), e);
				}else {
					this.processError(task, String.format("Geoname import with id #%d failed with error %s.", task.getId(), e.getMessage()), e);
				}
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

	@Override
	@Async
	//    I had to remove @Transactional. It slowed the process BIG TIME & errors weren't written to DB.
	public void updateGeoNameData(final String fileName, final CDBTask task) throws IOException {
		this.task = task;
		task.setStatus(CDBTask.Status.IN_PROGRESS);
		this.taskService.save(task);
		try (final BufferedReader reader = new BufferedReader(new InputStreamReader(this.storageService.loadFile(fileName).getInputStream(),StandardCharsets.UTF_8))) {

			List<GeoDataStructure> dataStructures;
			try {
				dataStructures = this.processUpdateLines(reader);
				this.geoDataUpdatePersistenceService.persistData(dataStructures);

			} catch (final Exception e) {
				if(e.getClass().isAssignableFrom(ArrayIndexOutOfBoundsException.class)) {

					this.processError(task, String.format("Geoname import with id #%d failed with error \"Array Index Out Of Bound Exception\".%sIt indicates the import file is in a wrong format.", task.getId(), System.lineSeparator()), e);
				}else {
					this.processError(task, String.format("Geoname import with id #%d failed with error %s.", task.getId(), e.getMessage()), e);
				}
			}
			if(LOGGER.isInfoEnabled()) {
				LOGGER.info(String.format("Geoname Update import with id #%d successful", task.getId()));
			}
			task.setEndDate(new Date());
			task.setStatus(Status.OK);
			this.taskService.save(task);
		}finally {
			this.storageService.deleteFile(fileName);
		}
	}

	@Override
	public void processError(final CDBTask task, final String errorMessage, final Exception e) {
		LOGGER.error(errorMessage);
		task.setEndDate(new Date());
		task.setType(CDBTask.Type.GEONAME_IMPORT);
		task.setStatus(Status.ERROR);
		task.setMessage(errorMessage);
		this.taskService.save(task);
		throw new CDBException(String.format("Error during Geoname import: %s", errorMessage),e);
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

	/**
	 * Expected format file:
	 *    country code      : iso country code, 2 characters
	 *    postal code       : varchar(20)
	 *    place name        : varchar(180)
	 *    admin name1       : 1. order subdivision (state) varchar(100)
	 *    admin code1       : 1. order subdivision (state) varchar(20)
	 *    admin name2       : 2. order subdivision (county/province) varchar(100)
	 *    admin code2       : 2. order subdivision (county/province) varchar(20)
	 *    admin name3       : 3. order subdivision (community) varchar(100)
	 *    admin code3       : 3. order subdivision (community) varchar(20)
	 *    latitude          : estimated latitude (wgs84)
	 *    longitude         : estimated longitude (wgs84)
	 *    accuracy          : accuracy of lat/lng from 1=estimated, 4=geonameid, 6=centroid of addresses or shape
	 */
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


	public List<GeoDataStructure> processUpdateLines(final BufferedReader reader) {
		this.task.setStatus(Status.IN_PROGRESS);
		this.taskService.save(this.task);
		final List<GeoDataStructure> dataStructures = new ArrayList<>();
		reader.lines().forEach(line -> {
			final GeoDataStructure dataStructure = this.processUpdateLine(line);
			if(dataStructure != null) {
				dataStructures.add(dataStructure);
			}
		});
		return dataStructures;
	}

	/**
	 * Expected format file :
	      geonameid         : integer id of record in geonames database
	      name              : name of geographical point (utf8) varchar(200)
	      asciiname         : name of geographical point in plain ascii characters, varchar(200)
	      alternatenames    : alternatenames, comma separated, ascii names automatically transliterated, convenience attribute from alternatename table, varchar(10000)
	      latitude          : latitude in decimal degrees (wgs84)
	      longitude         : longitude in decimal degrees (wgs84)
	      feature class     : see http://www.geonames.org/export/codes.html, char(1)
	      feature code      : see http://www.geonames.org/export/codes.html, varchar(10)
	      country code      : ISO-3166 2-letter country code, 2 characters
	      cc2               : alternate country codes, comma separated, ISO-3166 2-letter country code, 200 characters
	      admin1 code       : fipscode (subject to change to iso code), see exceptions below, see file admin1Codes.txt for display names of this code; varchar(20)
	      admin2 code       : code for the second administrative division, a county in the US, see file admin2Codes.txt; varchar(80)
	      admin3 code       : code for third level administrative division, varchar(20)
	      admin4 code       : code for fourth level administrative division, varchar(20)
	      population        : bigint (8 byte int)
	      elevation         : in meters, integer
	      dem               : digital elevation model, srtm3 or gtopo30, average elevation of 3''x3'' (ca 90mx90m) or 30''x30'' (ca 900mx900m) area in meters, integer. srtm processed by cgiar/ciat.
	      timezone          : the iana timezone id (see file timeZone.txt) varchar(40)
	      modification date : date of last modification in yyyy-MM-dd format


	      feature classes:
			A: country, state, region,...
			H: stream, lake, ...
			L: parks,area, ...
			P: city, village,...
			R: road, railroad
			S: spot, building, farm
			T: mountain,hill,rock,...
			U: undersea
			V: forest,heath,
	 */
	public GeoDataStructure processUpdateLine(final String line) {
		final GeoDataStructure dataStructure = new GeoDataStructure();
		final String[] fields = line.split("\t");
		dataStructure.setGeoNameId(Long.valueOf(fields[0]));
		dataStructure.setCityName(fields[1]);

		dataStructure.setGeoNameFeatureClass(fields[6]);
		dataStructure.setGeoNameFeatureCode(fields[7]);

		dataStructure.setMinorAreaCode(fields[12]);

		if(LOGGER.isDebugEnabled()) {
			LOGGER.debug(dataStructure.toString());
		}
		return dataStructure;
	}
	public CDBTask getTask() {
		return this.task;
	}

}
