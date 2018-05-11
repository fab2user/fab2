package eu.cehj.cdb2.web.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.HttpStatus.*;

import static org.springframework.web.bind.annotation.RequestMethod.*;

import eu.cehj.cdb2.business.service.db.LanguageService;
import eu.cehj.cdb2.common.dto.LanguageDTO;

@RestController
@RequestMapping("api/language")
public class LanguageController extends BaseController {

    @Autowired
    LanguageService languageService;

    @RequestMapping(method = { GET })
    @ResponseStatus(value = OK)
    public List<LanguageDTO> get() {
        return this.languageService.getAllDTO();
    }

    @RequestMapping(method = { POST })
    @ResponseStatus(value = CREATED)
    public LanguageDTO save(@RequestBody final LanguageDTO dto) {
        return this.languageService.save(dto);
    }

    @RequestMapping(value = "/{id}", method = { DELETE })
    @ResponseStatus(value = NO_CONTENT)
    public void delete(@PathVariable("id") final Long id)  {
        this.languageService.delete(id);
    }

}