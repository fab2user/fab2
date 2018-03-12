package eu.cehj.cdb2.hub.utils;

import java.io.Serializable;
import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import eu.cehj.cdb2.entity.Synchronization.SyncStatus;

public class TaskSearch implements Serializable{

    private static final long serialVersionUID = -3377490651331062352L;

    private Long countryId;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date dateAfter;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date dateBefore;

    private String status;

    public Date getDateAfter() {
        return this.dateAfter;
    }
    public void setDateAfter(final Date dateAfter) {
        this.dateAfter = dateAfter;
    }
    public Date getDateBefore() {
        return this.dateBefore;
    }
    public void setDateBefore(final Date dateBefore) {
        this.dateBefore = dateBefore;
    }
    public Long getCountryId() {
        return this.countryId;
    }
    public void setCountryId(final Long countryId) {
        this.countryId = countryId;
    }
    public SyncStatus getStatus() {
        if(this.status == null) {
            return null;
        }
        return SyncStatus.valueOf(this.status);
    }
    public void setStatus(final String status) {
        this.status = status;
    }
}
