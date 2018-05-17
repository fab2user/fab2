package eu.cehj.cdb2.common.dto;

import eu.cehj.cdb2.entity.CountryOfSync.SearchType;

public class CountryOfSyncDTO extends BaseDTO{

    private static final long serialVersionUID = -6448266488847252561L;

    private String name;

    private String url;

    private boolean active;

    //    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    //    private Date lastSync;
    //

    private String user;

    private String password;

    private String countryCode;

    private int[] daysOfWeek;

    private String frequency;

    private SynchronizationDTO lastSynchronization;

    private SearchType searchType;

    public String getName() {
        return this.name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public boolean isActive() {
        return this.active;
    }

    public void setActive(final boolean active) {
        this.active = active;
    }

    public String getUrl() {
        return this.url;
    }

    public void setUrl(final String url) {
        this.url = url;
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

    public int[] getDaysOfWeek() {
        return this.daysOfWeek;
    }

    public void setDaysOfWeek(final int[] daysOfWeek) {
        this.daysOfWeek = daysOfWeek;
    }

    public String getFrequency() {
        return this.frequency;
    }

    public void setFrequency(final String frequency) {
        this.frequency = frequency;
    }

    public SynchronizationDTO getLastSynchronization() {
        return this.lastSynchronization;
    }

    public void setLastSynchronization(final SynchronizationDTO lastSynchronization) {
        this.lastSynchronization = lastSynchronization;
    }

    public SearchType getSearchType() {
        return this.searchType;
    }

    public void setSearchType(final SearchType searchType) {
        this.searchType = searchType;
    }

}
