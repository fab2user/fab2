package eu.cehj.cdb2.web.controller;

import static org.springframework.http.HttpStatus.OK;
import static org.springframework.web.bind.annotation.RequestMethod.GET;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import eu.cehj.cdb2.business.service.db.BailiffCompetenceAreaService;
import eu.cehj.cdb2.business.service.db.BailiffService;
import eu.cehj.cdb2.common.dto.BailiffCompetenceAreaDTO;
import eu.cehj.cdb2.common.dto.InsCompAreaDTO;
import eu.cehj.cdb2.entity.Bailiff;

@RestController
public class BailiffCompetenceAreaController extends BaseController {

    @Autowired
    BailiffCompetenceAreaService bcaService;

    @Autowired
    BailiffService bailiffService;

    @RequestMapping(value="api/competence", method = { GET })
    @ResponseStatus(value = OK)
    public List<BailiffCompetenceAreaDTO> get() throws Exception {
        return this.bcaService.getAllDTO();
    }

    @RequestMapping(value ="api/bailiff/{bailiffId}/competences", method = { GET })
    @ResponseStatus(value = OK)
    public List<InsCompAreaDTO> get(@PathVariable final Long bailiffId) throws Exception {
        final Bailiff bailiff = this.bailiffService.get(bailiffId);
        // JPA throws automatically an exception if it can't find an entity with this id
        //        final List<Competence> competences = bailiff.getCompetences();
        //        return competences
        //                .stream()
        //                .map(competence ->
        //                new CompetenceDTO(competence)
        //                        ).collect(Collectors.toList());
        return null;
    }

}