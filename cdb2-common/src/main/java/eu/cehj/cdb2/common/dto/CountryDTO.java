package eu.cehj.cdb2.common.dto;

import java.util.Date;

public class CountryDTO extends BaseDTO{

    private static final long serialVersionUID = -6448266488847252561L;

    private String name;

    private String url;

    private boolean active;

    private Date lastSync;

    public CountryDTO(final String name, final String url, final boolean active) {
        this.name = name;
        this.url = url;
        this.active = active;
    }

    public CountryDTO() {
        super();
    }

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

}
