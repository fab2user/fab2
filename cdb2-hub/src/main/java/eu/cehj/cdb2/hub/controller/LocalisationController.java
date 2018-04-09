package eu.cehj.cdb2.hub.controller;

import java.util.Properties;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.HttpStatus.*;

import static org.springframework.web.bind.annotation.RequestMethod.*;

import eu.cehj.cdb2.common.service.ResourceService;
import pl.jalokim.propertiestojson.util.PropertiesToJsonParser;

@RestController
@RequestMapping()
public class LocalisationController extends BaseController{

    @Autowired
    private ResourceService resourceService;

    @RequestMapping(method = { GET }, value = "localisation", produces = {"application/json"})
    @ResponseStatus(value = OK)
    public @ResponseBody String getLocalisation(@RequestParam final String lang) throws Exception {
        final Properties props = this.resourceService.loadProperties("i18n/" + lang + ".properties", "./i18n/" + lang + ".properties");

        final String json = PropertiesToJsonParser.parseToJson(props);
        return json;
    }
}
