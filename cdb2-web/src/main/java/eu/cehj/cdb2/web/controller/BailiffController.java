package eu.cehj.cdb2.web.controller;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.NO_CONTENT;
import static org.springframework.http.HttpStatus.OK;
import static org.springframework.web.bind.annotation.RequestMethod.DELETE;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;
import static org.springframework.web.bind.annotation.RequestMethod.PUT;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.querydsl.binding.QuerydslPredicate;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.querydsl.core.types.Predicate;

import eu.cehj.cdb2.business.service.data.BailiffImportService;
import eu.cehj.cdb2.business.service.db.BailiffService;
import eu.cehj.cdb2.common.dto.BailiffDTO;
import eu.cehj.cdb2.entity.Bailiff;
import eu.cehj.cdb2.entity.QBailiff;

@RestController
@RequestMapping("api/bailiff")
public class BailiffController extends BaseController {

    @Autowired
    BailiffService bailiffService;

    @Autowired
    BailiffImportService bailiffImportService;

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
    public void importData(@RequestParam("file") final MultipartFile file) throws Exception{
        //        try (InputStream is = file.getInputStream()) {
        //        this.bailiffImportService.importFile(file);
        //        }
    }

}