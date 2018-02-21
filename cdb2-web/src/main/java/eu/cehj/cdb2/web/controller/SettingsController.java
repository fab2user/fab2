package eu.cehj.cdb2.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.HttpStatus.*;

import static org.springframework.web.bind.annotation.RequestMethod.*;

import eu.cehj.cdb2.web.utils.Settings;

@RestController
@RequestMapping("api/settings")
public class SettingsController extends BaseController {

    @Autowired
    Settings settings;

    @RequestMapping( method = GET)
    @ResponseStatus(value = OK)
    public Settings getSettings() throws Exception {

        //        return this.settingsService.getSettings();
        return this.settings;
    }

}