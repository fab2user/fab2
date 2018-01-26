package eu.cehj.cdb2.entity;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
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
    @JoinColumn(name = "geo_area_id")
    private GeoArea geoArea;

    @ManyToOne
    @JoinColumn(name = "competence_id")
    private Competence competence;

}
