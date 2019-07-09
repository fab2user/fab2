package eu.cehj.cdb2.web.controller;

import static org.springframework.http.HttpStatus.OK;
import static org.springframework.web.bind.annotation.RequestMethod.GET;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import eu.cehj.cdb2.business.service.db.InstrumentService;
import eu.cehj.cdb2.common.dto.InstrumentDTO;

@RestController
@RequestMapping(value ="api/instrument")
public class InstrumentController extends BaseController {

	@Autowired
	InstrumentService instrumentService;


	@RequestMapping(method = { GET })
	@ResponseStatus(value = OK)
	@Secured(value = { "ROLE_VIEWER", "ROLE_USER", "ROLE_SUPER_USER", "ROLE_ADMIN"})
	public List<InstrumentDTO> get(){
		return this.instrumentService.getAllDTO();
	}

}