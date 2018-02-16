package eu.cehj.cdb2.common.dto;

import java.util.Date;

public class SynchronizationDTO extends BaseDTO{

    private static final long serialVersionUID = 7089475544349832137L;

    private Date executionDate;

    private String countryName;

    private boolean active;

    private String status;

    public String getCountryName() {
        return this.countryName;
    }

    public void setCountryName(final String countryName) {
        this.countryName = countryName;
    }

    public Date getExecutionDate() {
        return this.executionDate;
    }

    public void setExecutionDate(final Date executionDate) {
        this.executionDate = executionDate;
    }

    public boolean isActive() {
        return this.active;
    }

    public void setActive(final boolean active) {
        this.active = active;
    }

    public String getStatus() {
        return this.status;
    }

    public void setStatus(final String status) {
        this.status = status;
    }

}
