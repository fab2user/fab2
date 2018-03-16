package eu.cehj.cdb2.web.controller;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.stream.Collectors;

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
    public MunicipalityDTO save(@RequestBody final MunicipalityDTO municipalityDTO) throws Exception {

        return this.municipalityService.save(municipalityDTO);
    }

    @RequestMapping(method = { GET })
    @ResponseStatus(value = OK)
    public List<MunicipalityDTO> get() throws Exception {
        return this.municipalityService.getAllDTO();
    }

    @RequestMapping(method = { GET }, value = "import")
    @ResponseStatus(value = OK)
    public void importData() throws Exception {
        final InputStream is = this.getClass().getResourceAsStream("FR.txt");
        final String fileContent = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8)).lines().collect(Collectors.joining("\n"));
        //  this.dataImportService.importData(fileContent);
    }

    @RequestMapping(method = RequestMethod.GET, value="search")
    @ResponseStatus(value = OK)
    public Page<MunicipalityDTO> search(
            @QuerydslPredicate(root = Municipality.class) final Predicate predicate, final Pageable pageable) throws Exception {
        return this.municipalityService.findAll(predicate, pageable);
    }

    @RequestMapping(method = { POST }, value="update")
    @ResponseStatus(value = HttpStatus.OK)
    //    public CompletableFuture<List<MunicipalityDTO>> upload(@RequestParam("file") final MultipartFile file) throws Exception{
    public CDBTaskDTO upload(@RequestParam("file") final MultipartFile file) throws Exception{
        final CDBTask task = this.taskService.save(new CDBTask());
        this.storageService.store(file);
        this.importService.importData(file.getOriginalFilename(), task);
        return this.taskService.getDTO(task.getId());
    }

}