package eu.cehj.cdb2.hub.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.querydsl.core.BooleanBuilder;

import static org.springframework.http.HttpStatus.*;

import static org.springframework.web.bind.annotation.RequestMethod.*;

import eu.cehj.cdb2.business.service.db.SynchronizationService;
import eu.cehj.cdb2.common.dto.SynchronizationDTO;
import eu.cehj.cdb2.entity.QSynchronization;
import eu.cehj.cdb2.entity.Synchronization.SyncStatus;
import eu.cehj.cdb2.hub.utils.TaskSearch;

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
    public Page<SynchronizationDTO> get(final TaskSearch search, final Pageable pageable) throws Exception{
        // Here we can't use standard querydsl/springboot web query parameters handling because of the date before/after stuff...
        final BooleanBuilder where = new BooleanBuilder();
        if(search.getDateBefore() != null) {
            where.and(QSynchronization.synchronization.endDate.before(search.getDateBefore()));
        }
        if(search.getDateAfter()!=null) {
            where.and(QSynchronization.synchronization.endDate.after(search.getDateAfter()));
        }
        if( search.getCountryId() != null) {
            where.and(QSynchronization.synchronization.country.id.eq(search.getCountryId()));
        }
        if( search.getStatus() != null) {
            where.and(QSynchronization.synchronization.status.eq(search.getStatus()));
        }


        return this.syncService.findAll(where, pageable);
    }

    @RequestMapping(method = GET, value = "last")
    @ResponseStatus(value = OK)
    public List<SynchronizationDTO> getLastTasks() throws Exception{
        return this.syncService.getLastByCountry();
    }

    @RequestMapping(method = GET, value = "status")
    @ResponseStatus(value = OK)
    public SyncStatus[] getAllStatus() throws Exception{
        return SyncStatus.values();
    }

}