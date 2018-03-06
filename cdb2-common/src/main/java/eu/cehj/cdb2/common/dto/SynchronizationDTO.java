package eu.cehj.cdb2.common.dto;

import java.util.Date;

public class SynchronizationDTO extends BaseDTO{

    private static final long serialVersionUID = 7089475544349832137L;

    private Date endDate;

    private Date startDate;

    private String countryName;

    private boolean active;

    private String status;

    private String message;

    public String getCountryName() {
        return this.countryName;
    }

    public void setCountryName(final String countryName) {
        this.countryName = countryName;
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

    public Date getEndDate() {
        return this.endDate;
    }

    public void setEndDate(final Date endDate) {
        this.endDate = endDate;
    }

    public Date getStartDate() {
        return this.startDate;
    }

    public void setStartDate(final Date startDate) {
        this.startDate = startDate;
    }

    public String getMessage() {
        return this.message;
    }

    public void setMessage(final String message) {
        this.message = message;
    }

}
