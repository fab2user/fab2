package eu.cehj.cdb2.web.controller;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.querydsl.binding.QuerydslPredicate;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.querydsl.core.types.Predicate;

import static org.springframework.http.HttpStatus.*;

import static org.springframework.web.bind.annotation.RequestMethod.*;

import eu.cehj.cdb2.business.service.data.BailiffImportService;
import eu.cehj.cdb2.business.service.db.BailiffService;
import eu.cehj.cdb2.common.dto.BailiffDTO;
import eu.cehj.cdb2.common.dto.BailiffExportDTO;
import eu.cehj.cdb2.common.service.StorageService;
import eu.cehj.cdb2.common.service.task.TaskManager;
import eu.cehj.cdb2.common.service.task.TaskStatus;
import eu.cehj.cdb2.entity.Bailiff;
import eu.cehj.cdb2.entity.QBailiff;
import eu.cehj.cdb2.web.utils.Settings;

@RestController
@RequestMapping("api/bailiff")
public class BailiffController extends BaseController {

    @Autowired
    BailiffService bailiffService;

    @Autowired
    BailiffImportService bailiffImportService;

    @Autowired
    Settings settings;

    @Autowired
    TaskManager taskManager;

    @Autowired
    StorageService storageService;

    @RequestMapping(
            method = {
                    POST, PUT
            })
    @ResponseStatus(value = CREATED)
    public BailiffDTO save(@RequestBody final BailiffDTO bailiffDTO) throws Exception {

        return this.bailiffService.save(bailiffDTO);
    }

    @RequestMapping(method = GET)
    @ResponseStatus(value = OK)
    public List<BailiffDTO> get(@RequestParam(required = false) final Boolean deleted) throws Exception {
        if((deleted != null) && (deleted == true)) {
            return this.bailiffService.getAllEvenDeletedDTO();
        }
        return this.bailiffService.getAllDTO();
    }

    /**
     * Used for xml generation
     */
    @RequestMapping(method = GET, value = "all")
    @ResponseStatus(value = OK)
    public List<BailiffExportDTO> getAllForExport() throws Exception {
        return this.bailiffService.getAllForExport();
    }

    @RequestMapping(method = GET, value = "search")
    @ResponseStatus(value = OK)
    public Page<BailiffDTO> search(@QuerydslPredicate(root = Bailiff.class) final Predicate predicate, final Pageable pageable) throws Exception {
        // Because we return only active bailiffs, we have to tweak the search from the http request, in order to add deleted filter
        final QBailiff bailiff = QBailiff.bailiff;
        final Predicate tweakedPredicate = (bailiff.deleted.isFalse().or(bailiff.deleted.isNull())).and(predicate);
        return this.bailiffService.findAll(tweakedPredicate, pageable);
    }

    @RequestMapping(method = DELETE, value = "/{id}")
    @ResponseStatus(value = NO_CONTENT)
    public void delete(@PathVariable() final Long id) throws Exception {
        this.bailiffService.delete(id);
    }

    @RequestMapping(method = { POST }, value="import")
    @ResponseStatus(value = HttpStatus.OK)
    public TaskStatus importData(@RequestParam("file") final MultipartFile file) throws Exception{
        final TaskStatus task = this.taskManager.createTask(TaskManager.Type.IMPORT_XLS);
        //        try (InputStream is = file.getInputStream()) {
        //            this.bailiffImportService.importFile(is, this.settings.getCountryCode(), task);
        //        }
        this.storageService.store(file);
        this.bailiffImportService.importFile(file.getOriginalFilename(), this.settings.getCountryCode(), task);
        return task;
    }

    @RequestMapping(method = { GET }, value="export")
    public ResponseEntity<Resource> exportData() throws Exception{
        final String exportFilePath = this.bailiffImportService.export("AT");
        final Path path = Paths.get(exportFilePath);
        final ByteArrayResource resource = new ByteArrayResource(Files.readAllBytes(path));
        //        final HttpHeaders headers = new HttpHeaders();
        //        headers.add("filename", "pop");
        this.logger.debug("headers : " + resource.getFilename());
        return ResponseEntity.ok()
                .contentLength(resource.contentLength())
                .contentType(MediaType.parseMediaType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"))
                //                .headers(headers)
                .body(resource);
    }

    @RequestMapping(method = GET, value = "/{id}")
    @ResponseStatus(value = OK)
    public BailiffDTO getById(@PathVariable final Long id) throws Exception {
        return this.bailiffService.getDTO(id);
    }

}