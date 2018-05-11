package eu.cehj.cdb2.web.controller;

import java.io.IOException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.querydsl.binding.QuerydslPredicate;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.querydsl.core.types.Predicate;

import static org.springframework.http.HttpStatus.*;

import static org.springframework.web.bind.annotation.RequestMethod.*;

import eu.cehj.cdb2.business.exception.CDBException;
import eu.cehj.cdb2.business.service.data.DataImportService;
import eu.cehj.cdb2.business.service.db.CDBTaskService;
import eu.cehj.cdb2.business.service.db.MunicipalityService;
import eu.cehj.cdb2.common.dto.CDBTaskDTO;
import eu.cehj.cdb2.common.dto.MunicipalityDTO;
import eu.cehj.cdb2.common.service.StorageService;
import eu.cehj.cdb2.entity.CDBTask;
import eu.cehj.cdb2.entity.Municipality;

@RestController
@RequestMapping("api/municipality")
public class MunicipalityController extends BaseController {

    private static final Logger LOGGER = LoggerFactory.getLogger(MunicipalityController.class);

    @Autowired
    MunicipalityService municipalityService;

    @Autowired
    DataImportService dataImportService;

    @Autowired
    private DataImportService importService;

    @Autowired
    private CDBTaskService taskService;

    @Autowired
    private StorageService storageService;

    @RequestMapping(method = { POST, PUT })
    @ResponseStatus(value = CREATED)
    public MunicipalityDTO save(@RequestBody final MunicipalityDTO municipalityDTO) {

        return this.municipalityService.save(municipalityDTO);
    }

    @RequestMapping(method = { GET })
    @ResponseStatus(value = OK)
    public List<MunicipalityDTO> get() {
        return this.municipalityService.getAllDTO();
    }

    @RequestMapping(method = RequestMethod.GET, value="search")
    @ResponseStatus(value = OK)
    public Page<MunicipalityDTO> search(
            @QuerydslPredicate(root = Municipality.class) final Predicate predicate, final Pageable pageable) {
        return this.municipalityService.findAll(predicate, pageable);
    }

    @RequestMapping(method = { POST }, value="update")
    @ResponseStatus(value = HttpStatus.OK)
    public CDBTaskDTO upload(@RequestParam("file") final MultipartFile file){
        final CDBTask task = this.taskService.save(new CDBTask(CDBTask.Type.GEONAME_IMPORT));
        try {
            this.storageService.store(file);
            this.importService.importData(file.getOriginalFilename(), task);
        } catch (final IOException e) {
            LOGGER.error(e.getMessage(),e);
            throw new CDBException(e.getMessage(),e);
        }
        return this.taskService.getDTO(task.getId());
    }

}