package eu.cehj.cdb2.hub.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.querydsl.binding.QuerydslPredicate;
import org.springframework.util.MultiValueMap;
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
import eu.cehj.cdb2.hub.service.SearchService;
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
     * Public REST service providing Bailiff search.
     * @param predicate
     * available search terms are: <ul><li>name</li><li>city</li></ul>
     * @param pageable
     *
     * @param countryCode
     * <strong>Required</strong>. ISO code of country to search.
     * @param transformedReq Added by {@link ParamsInterceptor}. Internal use.
     * @return {@link List} object containing search results
     * @throws Exception
     */
    @RequestMapping(method = GET, value = "bailiff")
    @ResponseStatus(value = OK)
    public List<BailiffDTO> search(@QuerydslPredicate(root = Bailiff.class) final Predicate predicate, final Pageable pageable,
            @RequestParam(name = "country", required = true) final String countryCode,
            @RequestAttribute(name = "transformedReq", required = false) final MultiValueMap<String, String> transformedReq) throws Exception {

        // FIXME: Instead of calling directly SearchInterfaceService, get correct service from  a factory, by passing it country code
        return this.searchService.sendQuery(countryCode, transformedReq);

    }

}