package eu.cehj.cdb2.web.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.HttpStatus.*;

import static org.springframework.web.bind.annotation.RequestMethod.*;

import eu.cehj.cdb2.business.service.db.BailiffCompetenceAreaService;
import eu.cehj.cdb2.business.service.db.BailiffService;
import eu.cehj.cdb2.common.dto.BailiffCompetenceAreaCustomDTO;
import eu.cehj.cdb2.common.dto.BailiffCompetenceAreaDTO;

@RestController
public class BailiffCompetenceAreaController extends BaseController {

    @Autowired
    BailiffCompetenceAreaService bcaService;

    @Autowired
    BailiffService bailiffService;

    @RequestMapping(value="api/bailiffcomparea", method = { GET })
    @ResponseStatus(value = OK)
    public List<BailiffCompetenceAreaDTO> get() throws Exception {
        return this.bcaService.getAllDTO();
    }

    @RequestMapping(value ="api/bailiff/{bailiffId}/competences", method = { GET })
    @ResponseStatus(value = OK)
    public List<BailiffCompetenceAreaCustomDTO> get(@PathVariable final Long bailiffId) throws Exception {

        return this.bcaService.getAllSimpleDTO(bailiffId);
    }

    @RequestMapping(value="api/bailiffcomparea", method = { POST })
    @ResponseStatus(value = OK)
    public BailiffCompetenceAreaDTO save(@RequestBody final BailiffCompetenceAreaDTO dto) throws Exception {

        return this.bcaService.save(dto);
    }

    @RequestMapping( method = DELETE, value = "api/bailiffcomparea/{id}" )
    @ResponseStatus(value = NO_CONTENT)
    public void delete(final Model model, @PathVariable(value = "id") final Long id) throws Exception {
        this.bcaService.delete(id);
    }

}