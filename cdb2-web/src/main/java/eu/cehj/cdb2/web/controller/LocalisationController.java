package eu.cehj.cdb2.web.controller;

import static org.springframework.http.HttpStatus.OK;
import static org.springframework.web.bind.annotation.RequestMethod.GET;

import java.io.FileInputStream;
import java.util.Properties;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import pl.jalokim.propertiestojson.util.PropertiesToJsonParser;

@RestController
@RequestMapping()
public class LocalisationController extends BaseController{

    @RequestMapping(method = { GET }, value = "localisation", produces = {"application/json"})
    @ResponseStatus(value = OK)
    public @ResponseBody String getLocalisation(@RequestParam final String lang) throws Exception{
        final String rootPath = Thread.currentThread().getContextClassLoader().getResource("").getPath();
        final String enPath = rootPath + "i18n/" + lang + ".properties";
        final Properties appProps = new Properties();
        try(FileInputStream fis = new FileInputStream(enPath)){
            appProps.load(fis);
            final String json = PropertiesToJsonParser.parseToJson(appProps);
            return json;
        }
    }
}
