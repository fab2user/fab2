package eu.cehj.cdb2.entity;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "geo_area")
public class GeoArea extends BaseAuditedEntity<String> {

    private static final long serialVersionUID = -7834873765738494098L;

    @Column(name = "name", nullable=false)
    private String name;

    @OneToMany(mappedBy="geoArea")
    private List<Municipality> municipalities;

    public String getName() {
        return this.name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public List<Municipality> getMunicipalities() {
        return this.municipalities;
    }

    public void setMunicipalities(final List<Municipality> municipalities) {
        this.municipalities = municipalities;
    }
}
