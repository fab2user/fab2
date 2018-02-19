package eu.cehj.cdb2.common.dto;

import java.util.Date;

public class CountryOfSyncDTO extends BaseDTO{

    private static final long serialVersionUID = -6448266488847252561L;

    private String name;

    private String url;

    private boolean active;

    private Date lastSync;

    private String user;

    private String password;

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

}
