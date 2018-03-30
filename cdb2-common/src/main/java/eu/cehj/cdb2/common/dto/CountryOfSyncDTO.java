package eu.cehj.cdb2.common.dto;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

public class CountryOfSyncDTO extends BaseDTO{

    private static final long serialVersionUID = -6448266488847252561L;

    private String name;

    private String url;

    private boolean active;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date lastSync;

    private boolean lastSyncSuccess;

    private String user;

    private String password;

    private String countryCode;

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

    public Date getLastSync() {
        return this.lastSync;
    }

    public void setLastSync(final Date lastSync) {
        this.lastSync = lastSync;
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

    public boolean isLastSyncSuccess() {
        return this.lastSyncSuccess;
    }

    public void setLastSyncSuccess(final boolean lastSyncSuccess) {
        this.lastSyncSuccess = lastSyncSuccess;
    }

}
