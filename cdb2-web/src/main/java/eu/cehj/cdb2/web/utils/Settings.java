package eu.cehj.cdb2.web.utils;

import java.io.Serializable;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * Provides an interface to application.properties, meant to be used by client front end.
 */
@Component
public class Settings implements Serializable{

    private static final long serialVersionUID = -712848177011940608L;

    @Value("${country.name}")
    private String country;

    @Value("${country.code}")
    private String countryCode;

    @Value("${national.id.prefix}")
    private String nationalIdPrefix;

    public String getCountry() {
        return this.country;
    }

    public void setCountry(final String country) {
        this.country = country;
    }

    public String getCountryCode() {
        return this.countryCode;
    }

    public void setCountryCode(final String countryCode) {
        this.countryCode = countryCode;
    }

    public String getNationalIdPrefix() {
        return this.nationalIdPrefix;
    }

    public void setNationalIdPrefix(final String nationalIdPrefix) {
        this.nationalIdPrefix = nationalIdPrefix;
    }
}
