package eu.cehj.cdb2.hub.utils;

import java.io.Serializable;
import java.util.Date;

public class TaskSearch implements Serializable{

    private static final long serialVersionUID = -3377490651331062352L;

    private String country;
    private Date dateAfter;
    private Date dateBefore;

    public String getCountry() {
        return this.country;
    }
    public void setCountry(final String country) {
        this.country = country;
    }
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
}
