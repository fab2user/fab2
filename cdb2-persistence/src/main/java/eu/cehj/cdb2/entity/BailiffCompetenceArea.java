package eu.cehj.cdb2.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "bailiff_competence_area")
public class BailiffCompetenceArea extends BaseAuditedEntity<String> {

    private static final long serialVersionUID = -188291819445691380L;

    @ManyToOne
    @JoinColumn(name = "bailiff_id")
    private Bailiff bailiff;

    @ManyToOne
    @JoinColumn(name = "competence_id")
    private Competence competence;

    @ManyToMany
    @JoinTable(name = "rel_bailiff_comp_area_area",
    joinColumns = @JoinColumn(name = "bailiff_comp_area_id"),
    inverseJoinColumns = @JoinColumn(name = "area_id"))
    private final List<GeoArea> areas = new ArrayList<>();

    public Bailiff getBailiff() {
        return this.bailiff;
    }

    public void setBailiff(final Bailiff bailiff) {
        this.bailiff = bailiff;
    }

    public Competence getCompetence() {
        return this.competence;
    }

    public void setCompetence(final Competence competence) {
        this.competence = competence;
    }

    public List<GeoArea> getAreas() {
        return this.areas;
    }

}
