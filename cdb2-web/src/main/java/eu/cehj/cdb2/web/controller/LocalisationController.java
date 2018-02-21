package eu.cehj.cdb2.web.controller;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Properties;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;
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

    @RequestMapping(method = GET, value = "localisation", produces = "application/json")
    @ResponseStatus(value = OK)
    public @ResponseBody String getLocalisation(@RequestParam final String lang) throws Exception {
        final Properties props = new Properties();
        final ClassPathResource jarResource = new ClassPathResource("i18n/" + lang + ".properties");
        final FileSystemResource fileSystemResource = new FileSystemResource("./i18n/" + lang + ".properties");
        if(jarResource != null) {
            try (final InputStream is = jarResource.getInputStream()) {
                props.load(is);
            }catch(final FileNotFoundException err) {
                this.logger.warn(err.getMessage(), err);
            }
        }
        if(fileSystemResource != null) {
            try (InputStream is = fileSystemResource.getInputStream()) {
                props.load(is);
            }catch(final FileNotFoundException err) {
                this.logger.warn(err.getMessage(), err);
            }
        }

        final String json = PropertiesToJsonParser.parseToJson(props);
        return json;
    }
}
