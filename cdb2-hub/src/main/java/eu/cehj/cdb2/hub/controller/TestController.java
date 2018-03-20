package eu.cehj.cdb2.hub.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import eu.cehj.cdb2.hub.service.FranceSearchService;


@RestController
@RequestMapping("api/test")
public class TestController extends BaseController {

    @Autowired
    FranceSearchService searchService;

    //    @RequestMapping(method = GET)
    //    @ResponseStatus(value = OK)
    //    public List<BailiffDTO> test() throws Exception {
    //        final ListeEtudeByInseeResponse resp =  this.searchService.sendQuery(countryCode, params)();
    //        return new ArrayList<>();
    //    }
    //    @RequestMapping(method = GET)
    //    @ResponseStatus(value = OK)
    //    public CdbPushMessage test() throws Exception {
    //        return this.pushDataService.process("FR");
    //
    //    }

}