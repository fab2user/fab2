package eu.cehj.cdb2.web.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.HttpStatus.*;

import static org.springframework.web.bind.annotation.RequestMethod.*;

import eu.cehj.cdb2.business.service.db.BailiffService;
import eu.cehj.cdb2.business.service.db.CompetenceService;
import eu.cehj.cdb2.common.dto.CompetenceDTO;

@RestController
public class CompetenceController extends BaseController {

    @Autowired
    CompetenceService competenceService;

    @Autowired
    BailiffService bailiffService;

    @RequestMapping(value="api/competence", method = { GET })
    @ResponseStatus(value = OK)
    public List<CompetenceDTO> get() {
        return this.competenceService.getAllDTO();
    }


}