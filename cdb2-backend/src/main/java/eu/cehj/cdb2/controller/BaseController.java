package eu.cehj.cdb2.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RestController;

@RestController
public abstract class BaseController {

    protected Logger logger = LoggerFactory.getLogger(this.getClass());

}