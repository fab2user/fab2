package eu.cehj.cdb2.hub.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.querydsl.binding.QuerydslPredicate;
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
import eu.cehj.cdb2.hub.service.SearchInterfaceService;

@RestController
@RequestMapping("api/bailiff")
public class BailiffController extends BaseController {

    @Autowired
    SearchInterfaceService searchService;

    @Autowired
    QueryDslPredicateAnalyzer predicateAnalyzer;

    @RequestMapping(method = GET, value = "search")
    @ResponseStatus(value = OK)
    public Page<BailiffDTO> search(@QuerydslPredicate(root = Bailiff.class) final Predicate predicate, final Pageable pageable,
            @RequestParam(name = "country", required = true) final String countryCode,  @RequestAttribute(name="transformedReq", required = false) final Map<String, String> transformedReq) throws Exception {
        //        final List<Expression<?>> expressions = ((BooleanOperation) predicate).getArgs();
        //        for (final Expression<?> expression : expressions) {
        //            final String expressionPath = expression.accept(PathExtractor.DEFAULT, null).toString();
        //            final String value = expression.accept(ToStringVisitor.DEFAULT, null);
        //            this.logger.debug(expressionPath);
        //            this.logger.debug(value.toString());
        //        }
        this.searchService.sendQuery(countryCode, transformedReq);
        return null;

    }

}