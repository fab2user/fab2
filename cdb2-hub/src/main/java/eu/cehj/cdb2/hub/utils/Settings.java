package eu.cehj.cdb2.hub.utils;

import java.io.Serializable;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * Provides an interface to application.properties, meant to be used by client front end.
 */
@Component
public class Settings implements Serializable{

    private static final long serialVersionUID = -9093373738912822195L;

    @Value("${settings.search.url}")
    private String searchUrl;

    @Value("${settings.bailiffs.url}")
    private String bailiffsUrl;

    @Value("${settings.areas.url}")
    private String areasUrl;

    public String getSearchUrl() {
        return this.searchUrl;
    }

    public void setSearchUrl(final String searchUrl) {
        this.searchUrl = searchUrl;
    }

    public String getBailiffsUrl() {
        return this.bailiffsUrl;
    }

    public void setBailiffsUrl(final String bailiffsUrl) {
        this.bailiffsUrl = bailiffsUrl;
    }

    public String getAreasUrl() {
        return this.areasUrl;
    }

    public void setAreasUrl(final String areasUrl) {
        this.areasUrl = areasUrl;
    }

}
