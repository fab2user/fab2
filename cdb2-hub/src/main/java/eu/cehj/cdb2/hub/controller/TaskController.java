package eu.cehj.cdb2.hub.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.HttpStatus.*;

import static org.springframework.web.bind.annotation.RequestMethod.*;

import eu.cehj.cdb2.business.service.db.SynchronizationService;
import eu.cehj.cdb2.common.dto.SynchronizationDTO;

@RestController
@RequestMapping("api/task")
public class TaskController extends BaseController {

    @Autowired
    private SynchronizationService syncService;

    @RequestMapping(method = GET, value = "{taskId}")
    @ResponseStatus(value = OK)
    public SynchronizationDTO get(@PathVariable final Long taskId) throws Exception{
        return this.syncService.getDTO(taskId);
    }

    @RequestMapping(method = GET, value = "last")
    @ResponseStatus(value = OK)
    public List<SynchronizationDTO> getLastTasks() throws Exception{
        return this.syncService.getLastByCountry();
    }

}