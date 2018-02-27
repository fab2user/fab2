package eu.cehj.cdb2.hub.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.HttpStatus.*;

import static org.springframework.web.bind.annotation.RequestMethod.*;

import eu.cehj.cdb2.common.service.CdbPushMessage;
import eu.cehj.cdb2.hub.service.central.push.PushDataService;


@RestController
@RequestMapping("test")
public class TestController extends BaseController {

    @Autowired
    PushDataService pushDataService;


    @RequestMapping(method = GET)
    @ResponseStatus(value = OK)
    public CdbPushMessage test() throws Exception {
        return this.pushDataService.process("FR");

    }

}