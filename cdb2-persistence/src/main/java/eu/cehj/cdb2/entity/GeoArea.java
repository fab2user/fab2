package eu.cehj.cdb2.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Where;

@Entity
@Table(name = "geo_area")
@Where(clause="deleted=0 or deleted is null")
public class GeoArea extends BaseAuditedEntity<String> {

    private static final long serialVersionUID = -7834873765738494098L;

    @Column(name = "name", nullable=false)
    private String name;

    @OneToMany(mappedBy="geoArea")
    private List<Municipality> municipalities = new ArrayList<>();

    @ManyToMany(mappedBy = "areas")
    private final List<BailiffCompetenceArea> bailiffCompetenceAreas = new ArrayList<>();

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

    public List<BailiffCompetenceArea> getBailiffCompetenceAreas() {
        return this.bailiffCompetenceAreas;
    }

}
