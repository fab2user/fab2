package eu.cehj.cdb2.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.HttpStatus.*;

import static org.springframework.web.bind.annotation.RequestMethod.*;

import eu.cehj.cdb2.business.service.db.CDBTaskService;
import eu.cehj.cdb2.common.dto.CDBTaskDTO;
import eu.cehj.cdb2.common.service.task.TaskManager;
import eu.cehj.cdb2.common.service.task.TaskStatus;

@RestController
@RequestMapping("api/task")
public class TaskController extends BaseController {

    @Autowired
    private TaskManager taskManager;

    @Autowired
    private CDBTaskService taskService;

    //TODO: remove this particular method and use CDBTask instead
    @RequestMapping(method = GET, value = "{code}")
    @ResponseStatus(value = OK)
    public TaskStatus get(@PathVariable final String code) throws Exception {

        return this.taskManager.getTask(code);
    }

    @RequestMapping(method = GET, value = "geoname/{id}")
    @ResponseStatus(value = OK)
    public CDBTaskDTO get(@PathVariable final Long id) throws Exception {

        return this.taskService.getDTO(id);
    }

}