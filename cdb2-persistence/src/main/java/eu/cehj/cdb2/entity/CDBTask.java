package eu.cehj.cdb2.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name = "task")
public class CDBTask extends BaseAuditedEntity<String> {

    private static final long serialVersionUID = 6342766955575124252L;

    public static  enum  Status{
        OK,
        STARTED,
        ERROR,
        PENDING,
        IN_PROGRESS
    }

    public static  enum  Type{
        GEONAME_IMPORT,
        BAILIFF_IMPORT,
        BAILIFF_EXPORT
    }

    public CDBTask() {
        super();
        this.setStartDate(new Date());
        this.setStatus(Status.STARTED);
    }

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Column(name = "start_date")
    private Date startDate;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Column(name = "end_date")
    private Date endDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable=false)
    private Status status;

    @Enumerated(EnumType.STRING)
    @Column(name = "type", nullable=false)
    private Type type;

    @Column(name = "message")
    private String message;

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

    public Status getStatus() {
        return this.status;
    }

    public void setStatus(final Status status) {
        this.status = status;
    }

    public Type getType() {
        return this.type;
    }

    public void setType(final Type type) {
        this.type = type;
    }

    public String getMessage() {
        return this.message;
    }

    public void setMessage(final String message) {
        this.message = message;
    }
}
