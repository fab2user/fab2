package eu.cehj.cdb2.web.controller;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;
import static org.springframework.web.bind.annotation.RequestMethod.PUT;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import eu.cehj.cdb2.business.service.db.MunicipalityService;
import eu.cehj.cdb2.common.dto.MunicipalityDTO;
import eu.cehj.cdb2.entity.Municipality;

@RestController
@RequestMapping("api/municipality")
public class MunicipalityController extends BaseController {

    @Autowired
    MunicipalityService municipalityService;

    @RequestMapping(method = { POST, PUT })
    @ResponseStatus(value = CREATED)
    public MunicipalityDTO save(@RequestBody final MunicipalityDTO municipalityDTO) throws Exception {

        return this.municipalityService.save(municipalityDTO);
    }

    @RequestMapping(method = { GET })
    @ResponseStatus(value = OK)
    public List<MunicipalityDTO> get() throws Exception {
        return municipalityService.getAllDTO();
    }


}