package eu.cehj.cdb2.hub.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.querydsl.binding.QuerydslPredicate;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.querydsl.core.types.Predicate;

import static org.springframework.http.HttpStatus.*;

import static org.springframework.web.bind.annotation.RequestMethod.*;

import eu.cehj.cdb2.common.dto.BailiffDTO;
import eu.cehj.cdb2.common.service.QueryDslPredicateAnalyzer;
import eu.cehj.cdb2.entity.Bailiff;
import eu.cehj.cdb2.hub.service.search.SearchService;
import eu.cehj.cdb2.hub.utils.ParamsInterceptor;

/**
 * Search interface provided by the Hub. Redirects users' requests to correct national database.<br/>
 * As we want to take benefit from national APIs features (search, pagination), we must intercept requests with {@link ParamsInterceptor}.
 *
 */
@RestController
@RequestMapping("api/search")
public class SearchController extends BaseController {

    @Autowired
    SearchService searchService;

    @Autowired
    QueryDslPredicateAnalyzer predicateAnalyzer;

    /**
     *
     * Public REST service providing Bailiff search. It's a single entry point for all Bailiff search requests, whatever the destination (Local webservice, managed service, or even CDB search).
     * @param predicate
     * available search terms are: <ul><li>name</li><li>city</li></ul>
     * @param pageable
     *
     * @param countryCode
     * <strong>Required</strong>. ISO code of country to search.
     * @param postalCode
     * @param transformedReq Added by {@link ParamsInterceptor}. Internal use.
     * @return {@link List} object containing search results
     */
    @RequestMapping(method = GET, value = "bailiff")
    @ResponseStatus(value = OK)
    @CrossOrigin()
    public Iterable<BailiffDTO> search(@QuerydslPredicate(root = Bailiff.class) final Predicate predicate, final Pageable pageable,
            @RequestParam(name = "country", required = true) final String countryCode,
            @RequestAttribute(name = "transformedReq", required = false) final MultiValueMap<String, String> transformedReq) {

        return  this.searchService.sendQuery(countryCode, transformedReq);
    }

}