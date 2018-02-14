package eu.cehj.cdb2.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "sync")
public class Synchronization extends BaseAuditedEntity<String> {

    public static  enum  SyncStatus{
        OK,
        ERROR,
        PENDING
    }


    private static final long serialVersionUID = -3906005306114553599L;

    @Column(name = "exec_date", nullable=false)
    private Date executionDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable=false)
    private SyncStatus status;

    @Column(name = "active")
    private boolean active = true;

    @ManyToOne
    @JoinColumn(name = "country", nullable=false)
    private Country country;

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

    public Country getCountry() {
        return this.country;
    }

    public void setCountry(final Country country) {
        this.country = country;
    }

    public SyncStatus getStatus() {
        return this.status;
    }

    public void setStatus(final SyncStatus status) {
        this.status = status;
    }

}
