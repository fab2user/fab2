package eu.cehj.cdb2.hub.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.HttpStatus.*;

import static org.springframework.web.bind.annotation.RequestMethod.*;

import eu.cehj.cdb2.entity.Synchronization;
import eu.cehj.cdb2.hub.service.central.push.PushDataServiceLauncher;

@RestController
@RequestMapping("api/cdb")
public class CdbSyncController extends BaseController{

    @Autowired
    private PushDataServiceLauncher pushDataService;

    @RequestMapping(method = { GET }, value = "send")
    @ResponseStatus(value = OK)
    public Synchronization startCDBSync(@RequestParam final String countryCode) throws Exception{
        return this.pushDataService.process(countryCode);
        // As pushDataService is async, controller will simply return ok to inform it received a request and started to process it
        // After processing pushDataService, will update sync table
        // In the meanwhile, front end will perform a polling. // TODO: develop the pollingback/front end, same as xml import.
    }
}
