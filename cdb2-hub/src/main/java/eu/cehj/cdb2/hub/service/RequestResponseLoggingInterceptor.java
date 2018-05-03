package eu.cehj.cdb2.hub.service;

import java.io.IOException;
import java.nio.charset.Charset;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.util.StreamUtils;

public class RequestResponseLoggingInterceptor implements ClientHttpRequestInterceptor {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @Override
    public ClientHttpResponse intercept(final HttpRequest request, final byte[] body, final ClientHttpRequestExecution execution) throws IOException {
        this.logRequest(request, body);
        final ClientHttpResponse response = execution.execute(request, body);
        this.logResponse(response);
        return response;
    }

    private void logRequest(final HttpRequest request, final byte[] body) throws IOException {
        if (this.log.isDebugEnabled()) {
            this.log.debug("===========================request begin================================================");
            this.log.debug("URI         : {}", request.getURI());
            this.log.debug("Method      : {}", request.getMethod());
            this.log.debug("Headers     : {}", request.getHeaders());
            this.log.debug("Request body: {}", new String(body, "UTF-8"));
            this.log.debug("==========================request end================================================");
        }
    }

    private void logResponse(final ClientHttpResponse response) throws IOException {
        if (this.log.isDebugEnabled()) {
            this.log.debug("============================response begin==========================================");
            this.log.debug("Status code  : {}", response.getStatusCode());
            this.log.debug("Status text  : {}", response.getStatusText());
            this.log.debug("Headers      : {}", response.getHeaders());
            this.log.debug("Response body: {}", StreamUtils.copyToString(response.getBody(), Charset.defaultCharset()));
            this.log.debug("=======================response end=================================================");
        }
    }
}
