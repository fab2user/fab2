package eu.cehj.cdb2.web.controller;

import static org.springframework.http.HttpStatus.OK;
import static org.springframework.web.bind.annotation.RequestMethod.GET;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import eu.cehj.cdb2.business.service.db.CompetenceService;
import eu.cehj.cdb2.common.dto.CompetenceDTO;

@RestController
@RequestMapping("api/competence")
public class CompetenceController extends BaseController {

    @Autowired
    CompetenceService competenceService;

    @RequestMapping(method = { GET })
    @ResponseStatus(value = OK)
    public List<CompetenceDTO> get() throws Exception {
        return this.competenceService.getAllDTO();
    }

}