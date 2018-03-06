package eu.cehj.cdb2.hub.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.querydsl.binding.QuerydslPredicate;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.querydsl.core.types.Predicate;

import static org.springframework.http.HttpStatus.*;

import static org.springframework.web.bind.annotation.RequestMethod.*;

import eu.cehj.cdb2.business.service.db.SynchronizationService;
import eu.cehj.cdb2.common.dto.SynchronizationDTO;
import eu.cehj.cdb2.entity.Synchronization;

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

    @RequestMapping(method = GET)
    @ResponseStatus(value = OK)
    public Page<SynchronizationDTO> get(@QuerydslPredicate(root = Synchronization.class) final Predicate predicate, final Pageable pageable) throws Exception{
        return this.syncService.findAll(predicate, pageable);
    }

    @RequestMapping(method = GET, value = "last")
    @ResponseStatus(value = OK)
    public List<SynchronizationDTO> getLastTasks() throws Exception{
        return this.syncService.getLastByCountry();
    }

}