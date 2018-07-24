package eu.cehj.cdb2.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Where;

import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
@Table(name = "country_sync")
@Where(clause="deleted=0 or deleted is null")
public class CountryOfSync extends BaseAuditedEntity<String> {

    private static final long serialVersionUID = 929915752303090598L;

    public enum SearchType{
        MANAGED,LOCAL_WS,CDB
    }

    @Column(name = "name", nullable=false)
    private String name;

    /**
     * URL used for search requests
     */
    @Column(name = "url")
    private String url;

    /**
     * URLL used to fetch all bailiffs from web service. If null, url property is used.
     */
    @Column(name = "fetch_url")
    private String fetchUrl;

    @Column(name = "active")
    private boolean active = true;

    @OneToMany(mappedBy="country")
    @JsonBackReference
    private final List<Synchronization> synchronizations = new ArrayList<>();

    @Column(name = "user")
    private String user;

    @Column(name = "password")
    private String password;

    @Column(name = "country_code", nullable = false)
    private String countryCode;

    @Column(name = "frequency", nullable = false, length = 5)
    private String frequency;

    @Column(name="days_of_week")
    private String daysOfWeek;

    @Column(name="search_type")
    @Enumerated(EnumType.STRING)
    private SearchType searchType;

    public String getName() {
        return this.name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public String getUrl() {
        return this.url;
    }

    public void setUrl(final String url) {
        this.url = url;
    }

    public boolean isActive() {
        return this.active;
    }

    public void setActive(final boolean active) {
        this.active = active;
    }

    public List<Synchronization> getSynchronizations() {
        return this.synchronizations;
    }

    public String getUser() {
        return this.user;
    }

    public void setUser(final String user) {
        this.user = user;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(final String password) {
        this.password = password;
    }

    public String getCountryCode() {
        return this.countryCode;
    }

    public void setCountryCode(final String countryCode) {
        this.countryCode = countryCode;
    }

    public String getFrequency() {
        return this.frequency;
    }

    public void setFrequency(final String frequency) {
        this.frequency = frequency;
    }

    public String getDaysOfWeek() {
        return this.daysOfWeek;
    }

    public void setDaysOfWeek(final String daysOfWeek) {
        this.daysOfWeek = daysOfWeek;
    }

    public SearchType getSearchType() {
        return this.searchType;
    }

    public void setSearchType(final SearchType searchType) {
        this.searchType = searchType;
    }

    public String getFetchUrl() {
        return this.fetchUrl;
    }

    public void setFetchUrl(final String fetchUrl) {
        this.fetchUrl = fetchUrl;
    }

}
