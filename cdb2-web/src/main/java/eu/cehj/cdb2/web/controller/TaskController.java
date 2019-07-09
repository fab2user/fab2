package eu.cehj.cdb2.web.controller;

import static org.springframework.http.HttpStatus.OK;
import static org.springframework.web.bind.annotation.RequestMethod.GET;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import eu.cehj.cdb2.business.service.db.CDBTaskService;
import eu.cehj.cdb2.common.dto.CDBTaskDTO;

@RestController
@RequestMapping("api/task")
public class TaskController extends BaseController {

	@Autowired
	private CDBTaskService taskService;

	@RequestMapping(method = GET, value = "{id}")
	@ResponseStatus(value = OK)
	@Secured(value = { "ROLE_VIEWER", "ROLE_USER", "ROLE_SUPER_USER", "ROLE_ADMIN"})
	public CDBTaskDTO getTask(@PathVariable final Long id) {

		return this.taskService.getDTO(id);
	}

}