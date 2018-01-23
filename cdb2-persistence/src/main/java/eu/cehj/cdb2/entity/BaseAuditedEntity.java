package eu.cehj.cdb2.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class BaseAuditedEntity<U> extends BaseEntity {

    private static final long serialVersionUID = 4900704445331839780L;

    @Column(name = "created_by")
    @CreatedBy
    private U createdBy;

    @Column(name = "created_on")
    @Temporal(TemporalType.TIMESTAMP)
    @CreatedDate
    private Date createdOn;

    @Column(name = "modified_by")
    @LastModifiedBy
    private U modifiedBy;

    @Column(name = "modified_on")
    @Temporal(TemporalType.TIMESTAMP)
    @LastModifiedDate
    private Date modifiedOn;

    public U getCreatedBy() {
        return this.createdBy;
    }

    public void setCreatedBy(final U createdBy) {
        this.createdBy = createdBy;
    }

    public Date getCreatedOn() {
        return this.createdOn;
    }

    public void setCreatedOn(final Date createdOn) {
        this.createdOn = createdOn;
    }

    public U getModifiedBy() {
        return this.modifiedBy;
    }

    public void setModifiedBy(final U modifiedBy) {
        this.modifiedBy = modifiedBy;
    }

    public Date getModifiedOn() {
        return this.modifiedOn;
    }

    public void setModifiedOn(final Date modifiedOn) {
        this.modifiedOn = modifiedOn;
    }

}
