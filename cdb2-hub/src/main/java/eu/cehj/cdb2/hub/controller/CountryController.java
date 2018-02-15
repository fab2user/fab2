package eu.cehj.cdb2.hub.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.HttpStatus.*;

import static org.springframework.web.bind.annotation.RequestMethod.*;

import eu.cehj.cdb2.business.service.db.CountryService;
import eu.cehj.cdb2.common.dto.CountryDTO;

@RestController
@RequestMapping("api/country")
public class CountryController extends BaseController {


    @Autowired
    CountryService countryService;

    @RequestMapping(method = { GET })
    @ResponseStatus(value = OK)
    public List<CountryDTO> get() throws Exception {
        return this.countryService.getAllDTO();
    }

    @RequestMapping(method = { POST })
    @ResponseStatus(value = CREATED)
    public CountryDTO save(@RequestBody final CountryDTO dto) throws Exception {
        return this.countryService.save(dto);
    }

}