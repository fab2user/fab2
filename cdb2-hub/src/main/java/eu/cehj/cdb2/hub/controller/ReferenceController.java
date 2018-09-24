package eu.cehj.cdb2.hub.controller;

import static org.springframework.http.HttpStatus.OK;
import static org.springframework.web.bind.annotation.RequestMethod.GET;

import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import eu.cehj.cdb2.business.service.db.HubReferenceService;
import eu.cehj.cdb2.common.dto.HubReferenceDTO;
import eu.cehj.cdb2.hub.service.CompetenceService;
import eu.cehj.cdb2.hub.service.LanguageService;

@RestController
@RequestMapping("api/reference")
public class ReferenceController extends BaseController {

    @Autowired
    private HubReferenceService hubReferenceService;

    @Autowired
    private CompetenceService competenceService;

    @Autowired
    private LanguageService languageService;

    @RequestMapping(method = GET)
    @ResponseStatus(value = OK)
    public HubReferenceDTO get(){
        return this.hubReferenceService.getReference();
    }

    @RequestMapping(method = GET, value = "competences")
    @ResponseStatus(value = OK)
    public CompetenceService getCompetences() {

        return  this.competenceService;
    }


    @RequestMapping(method = GET, value = "languages")
    @ResponseStatus(value = OK)
    public List<String> getLanguages() {
        final List<String> languages = this.languageService.getLanguages();
        Collections.sort(languages, (el1, el2) -> el1.compareTo(el2));
        return languages;
    }
}