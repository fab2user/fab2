package eu.cehj.cdb2.common.service.task;

import java.io.Serializable;
import java.time.LocalDate;

public class TaskStatus implements Serializable{

    private static final long serialVersionUID = 6740817574176024113L;

    private String code;

    public static  enum  Status{
        OK,
        ERROR,
        IN_PROGRESS
    }

    private Status status;

    private String message;

    private LocalDate startDate;

    private LocalDate endDate;

    public TaskStatus() {
        super();
    }

    public TaskStatus(final String code) {
        this.code = code;
        this.setStatus(Status.IN_PROGRESS);
        this.startDate = LocalDate.now();
    }


    public String getCode() {
        return this.code;
    }

    public String getMessage() {
        return this.message;
    }

    public void setMessage(final String message) {
        this.message = message;
    }

    public LocalDate getStartDate() {
        return this.startDate;
    }

    public void setStartDate(final LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return this.endDate;
    }

    public void setEndDate(final LocalDate endDate) {
        this.endDate = endDate;
    }

    public Status getStatus() {
        return this.status;
    }

    public void setStatus(final Status status) {
        this.status = status;
        if((status == Status.OK) || (status == Status.ERROR)) {
            this.endDate = LocalDate.now();
        }else if(status == Status.IN_PROGRESS) {
            this.message = "";
        }
    }


}
