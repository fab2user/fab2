package eu.cehj.cdb2.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "municipality")
public class Municipality extends BaseAuditedEntity<Long> {

    private static final long serialVersionUID = -1302962165492457412L;

    @Column(name = "postal_code")
    private String postalCode;

    @Column(name = "name")
    private String name;

    public Municipality() {
        super();
    }

    public String getPostalCode() {
        return this.postalCode;
    }

    public void setPostalCode(final String postalCode) {
        this.postalCode = postalCode;
    }

    public String getName() {
        return this.name;
    }

    public void setName(final String name) {
        this.name = name;
    }

}
