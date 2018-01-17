package eu.cehj.cdb2.web.controller;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;
import static org.springframework.web.bind.annotation.RequestMethod.PUT;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import eu.cehj.cdb2.business.service.db.BailiffService;
import eu.cehj.cdb2.business.service.search.model.BailiffSearch;
import eu.cehj.cdb2.common.dto.BailiffDTO;

@RestController
@RequestMapping("api/bailiff")
public class BailiffController extends BaseController {

    @Autowired
    BailiffService bailiffService;

    @RequestMapping(method = { POST, PUT })
    @ResponseStatus(value = CREATED)
    public BailiffDTO save(@RequestBody final BailiffDTO bailiffDTO) throws Exception {

        return this.bailiffService.save(bailiffDTO);
    }

    @RequestMapping(method = { GET })
    @ResponseStatus(value = OK)
    public List<BailiffDTO> get() throws Exception {
        return this.bailiffService.getAllDTO();
    }

    @RequestMapping(method = { GET }, value = "search")
    @ResponseStatus(value = OK)
    public Iterable<BailiffDTO> search(final Model model, @ModelAttribute final BailiffSearch bailiffSearch) throws Exception {
        return this.bailiffService.searchDTO(bailiffSearch);
    }

}