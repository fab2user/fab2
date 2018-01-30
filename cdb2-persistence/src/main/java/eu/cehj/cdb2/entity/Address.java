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

    @Column(name = "address", nullable=false)
    private String address;

    @ManyToOne
    @JoinColumn(name = "municipality")
    private Municipality municipality;

    public String getAddress() {
        return this.address;
    }

    public void setAddress(final String address) {
        this.address = address;
    }

    public Municipality getMunicipality() {
        return this.municipality;
    }

    public void setMunicipality(final Municipality municipality) {
        this.municipality = municipality;
    }

}
