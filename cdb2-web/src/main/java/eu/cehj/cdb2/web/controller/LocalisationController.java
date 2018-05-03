package eu.cehj.cdb2.web.controller;

import java.util.Properties;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.core.io.Resource;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.HttpStatus.*;

import static org.springframework.web.bind.annotation.RequestMethod.*;

import pl.jalokim.propertiestojson.util.PropertiesToJsonParser;

/**
 * Provides i18n for front-end.<br/>
 * First tries to load lang properties from resources folder of the application. Then tries to load lang file from folder where Spring Boot jar runs.<br/>
 * External file overloads local one.
 *
 */
@RestController
@RequestMapping()
public class LocalisationController extends BaseController {

    @Autowired
    private ApplicationContext context;

    @Value("${i18n.files.location}")
    private String i18nFilesLocation;

    @RequestMapping(method = GET, value = "localisation", produces = "application/json")
    @ResponseStatus(value = OK)
    public @ResponseBody String getLocalisation(@RequestParam final String lang) throws Exception {
        final Resource resource = this.context.getResource(String.format("%s/%s.properties", this.i18nFilesLocation, lang ));
        final Properties props =   new Properties();
        props.load(resource.getInputStream());

        final String json = PropertiesToJsonParser.parseToJson(props);
        return json;
    }
}
