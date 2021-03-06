package eu.cehj.cdb2.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
@Table(name = "sync")
public class Synchronization extends BaseAuditedEntity<String> {

    public enum  SyncStatus{
        OK,
        ERROR,
        PENDING,
        IN_PROGRESS,
        BUILDING_DATA,
        SENDING_TO_CDB
    }

    private static final long serialVersionUID = -3906005306114553599L;

    public Synchronization() {
        super();
        this.setStartDate(new Date());
    }

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Column(name = "start_date")
    private Date startDate;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Column(name = "end_date")
    private Date endDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable=false)
    private SyncStatus status;

    @Column(name = "active")
    private boolean active = true;

    @ManyToOne
    @JoinColumn(name = "country", nullable=false)
    @JsonManagedReference
    private CountryOfSync country;

    @Column(name = "message")
    private String message;

    public boolean isActive() {
        return this.active;
    }

    public void setActive(final boolean active) {
        this.active = active;
    }

    public CountryOfSync getCountry() {
        return this.country;
    }

    public void setCountry(final CountryOfSync country) {
        this.country = country;
    }

    public SyncStatus getStatus() {
        return this.status;
    }

    public void setStatus(final SyncStatus status) {
        this.status = status;
    }

    public String getMessage() {
        return this.message;
    }

    public void setMessage(final String message) {
        this.message = message;
    }

    public Date getStartDate() {
        return this.startDate;
    }

    public void setStartDate(final Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return this.endDate;
    }

    public void setEndDate(final Date endDate) {
        this.endDate = endDate;
    }

}
