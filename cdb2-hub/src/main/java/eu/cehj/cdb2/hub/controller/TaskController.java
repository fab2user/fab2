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
import com.querydsl.core.types.Predicate;

import static org.springframework.http.HttpStatus.*;

import static org.springframework.web.bind.annotation.RequestMethod.*;

import eu.cehj.cdb2.business.service.db.SynchronizationService;
import eu.cehj.cdb2.common.dto.SynchronizationDTO;
import eu.cehj.cdb2.entity.QSynchronization;
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
        final Predicate dateBefore = null;
        if(search.getDateBefore() != null) {
            QSynchronization.synchronization.endDate.before(search.getDateBefore());
        }
        final Predicate dateAfter = null;
        if(search.getDateAfter()!=null) {
            QSynchronization.synchronization.endDate.after(search.getDateAfter());
        }
        final Predicate country = null;
        if(search.getCountry() != null) {
            QSynchronization.synchronization.country.countryCode.eq(search.getCountry());
        }
        final BooleanBuilder booleanBuilder = new BooleanBuilder();

        return this.syncService.findAll(booleanBuilder.and(dateBefore).and(dateAfter).and(country), pageable);
    }

    @RequestMapping(method = GET, value = "last")
    @ResponseStatus(value = OK)
    public List<SynchronizationDTO> getLastTasks() throws Exception{
        return this.syncService.getLastByCountry();
    }

}