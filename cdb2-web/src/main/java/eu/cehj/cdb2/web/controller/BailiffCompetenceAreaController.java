package eu.cehj.cdb2.web.controller;

import static org.springframework.http.HttpStatus.NO_CONTENT;
import static org.springframework.http.HttpStatus.OK;
import static org.springframework.web.bind.annotation.RequestMethod.DELETE;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import eu.cehj.cdb2.business.service.db.BailiffCompetenceAreaService;
import eu.cehj.cdb2.business.service.db.BailiffService;
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
    public List<BailiffCompetenceAreaDTO> get(@PathVariable final Long bailiffId) throws Exception {

        // JPA throws automatically an exception if it can't find an entity with this id
        //        final List<Competence> competences = bailiff.getCompetences();
        //        return competences
        //                .stream()
        //                .map(competence ->
        //                new CompetenceDTO(competence)
        //                        ).collect(Collectors.toList());
        return this.bcaService.getAllDTO(bailiffId);
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