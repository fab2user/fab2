package eu.cehj.cdb2.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Where;

import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
@Table(name = "batch_data_update")
@Where(clause="deleted=0 or deleted is null")
public class BatchDataUpdate extends BaseAuditedEntity<String> {

    private static final long serialVersionUID = -8936071558577632139L;

    @Column(name = "field", nullable=false)
    private String field;

    @Column(name = "value", nullable=false)
    private String value;

    @ManyToOne
    @JoinColumn(name = "country", referencedColumnName="id", nullable=false)
    @JsonManagedReference
    private CountryOfSync country;

    public String getField() {
        return this.field;
    }

    public void setField(final String field) {
        this.field = field;
    }

    public String getValue() {
        return this.value;
    }

    public void setValue(final String value) {
        this.value = value;
    }

    public CountryOfSync getCountry() {
        return this.country;
    }

    public void setCountry(final CountryOfSync country) {
        this.country = country;
    }



}
