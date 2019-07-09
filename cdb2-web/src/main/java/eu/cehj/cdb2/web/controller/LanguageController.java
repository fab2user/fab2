package eu.cehj.cdb2.web.controller;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.NO_CONTENT;
import static org.springframework.http.HttpStatus.OK;
import static org.springframework.web.bind.annotation.RequestMethod.DELETE;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

import java.util.List;

import javax.annotation.security.RolesAllowed;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import eu.cehj.cdb2.business.service.db.LanguageService;
import eu.cehj.cdb2.common.dto.LanguageDTO;

@RestController
@RequestMapping("api/language")
public class LanguageController extends BaseController {

	@Autowired
	LanguageService languageService;

	@RequestMapping(method = { GET })
	@ResponseStatus(value = OK)
	@Secured(value = { "ROLE_VIEWER", "ROLE_USER", "ROLE_SUPER_USER", "ROLE_ADMIN"})
	public List<LanguageDTO> get() {
		return this.languageService.getAllDTO();
	}

	@RequestMapping(method = { POST })
	@ResponseStatus(value = CREATED)
	@RolesAllowed(value = {  "ROLE_USER", "ROLE_SUPER_USER", "ROLE_ADMIN"})
	public LanguageDTO save(@RequestBody final LanguageDTO dto) {
		return this.languageService.save(dto);
	}

	@RequestMapping(value = "/{id}", method = { DELETE })
	@ResponseStatus(value = NO_CONTENT)
	@RolesAllowed(value = {"ROLE_USER", "ROLE_SUPER_USER", "ROLE_ADMIN"})
	public void delete(@PathVariable("id") final Long id)  {
		this.languageService.delete(id);
	}

}