package eu.cehj.cdb2.hub.utils;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

/**
 * Allows to handle request parameters, since we can't know in advance what we'll get in QueryDSL predicate.
 *
 */
public class ParamsInterceptor implements HandlerInterceptor {

    protected Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public boolean preHandle(final HttpServletRequest request, final HttpServletResponse response, final Object handler) throws Exception {
        final Enumeration<String> paramNames = request.getParameterNames();
        final Map<String, String> transformedReq = new HashMap<>();
        while(paramNames.hasMoreElements()) {
            final String key = paramNames.nextElement();
            this.logger.debug(key);
            for(final String value:request.getParameterValues(key)) {
                this.logger.debug(value);
                transformedReq.put(key,value);
            }
        }
        request.setAttribute("transformedReq", transformedReq);
        return true;
    }

    @Override
    public void postHandle(final HttpServletRequest request, final HttpServletResponse response, final Object handler, final ModelAndView modelAndView) throws Exception {
        // TODO Auto-generated method stub

    }

    @Override
    public void afterCompletion(final HttpServletRequest request, final HttpServletResponse response, final Object handler, final Exception ex) throws Exception {
        // TODO Auto-generated method stub

    }

}
