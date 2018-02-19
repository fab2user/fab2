package eu.cehj.cdb2.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.Where;

@Entity
@Table(name = "country")
@Where(clause="deleted=0 or deleted is null")
public class Country extends BaseAuditedEntity<String> {

    private static final long serialVersionUID = 1L;

    @Column(name = "code", nullable=false)
    private String code;

    @Column(name = "name", nullable=false)
    private String name;

    public String getCode() {
        return this.code;
    }

    public void setCode(final String code) {
        this.code = code;
    }

    public String getName() {
        return this.name;
    }

    public void setName(final String name) {
        this.name = name;
    }

}
