package eu.cehj.cdb2.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Where;

@Entity
@Table(name = "address")
@Where(clause="deleted=0 or deleted is null")
public class Address extends BaseAuditedEntity<String> {

    private static final long serialVersionUID = 6631408359535998948L;

    @Column(name = "address_1", nullable=false)
    private String address1;

    @Column(name = "address_2")
    private String address2;

    @ManyToOne
    @JoinColumn(name = "municipality")
    private Municipality municipality;

    @ManyToOne
    @JoinColumn(name = "country")
    private Country country;

    public Municipality getMunicipality() {
        return this.municipality;
    }

    public void setMunicipality(final Municipality municipality) {
        this.municipality = municipality;
    }

    public Country getCountry() {
        return this.country;
    }

    public void setCountry(final Country country) {
        this.country = country;
    }

    public String getAddress1() {
        return this.address1;
    }

    public void setAddress1(final String address1) {
        this.address1 = address1;
    }

    public String getAddress2() {
        return this.address2;
    }

    public void setAddress2(final String address2) {
        this.address2 = address2;
    }

}
