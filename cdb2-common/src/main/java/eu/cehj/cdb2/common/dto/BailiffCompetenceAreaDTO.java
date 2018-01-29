package eu.cehj.cdb2.common.dto;

import java.util.List;

public class BailiffCompetenceAreaDTO extends BaseDTO{

    private static final long serialVersionUID = -2685453922078212828L;

    private BailiffDTO  bailiff;
    private List<GeoAreaDTO> areas;
    private CompetenceDTO competence;

    public BailiffCompetenceAreaDTO() {
        super();
    }

    public BailiffDTO getBailiff() {
        return this.bailiff;
    }

    public void setBailiff(final BailiffDTO bailiff) {
        this.bailiff = bailiff;
    }

    public List<GeoAreaDTO> getAreas() {
        return this.areas;
    }

    public void setAreas(final List<GeoAreaDTO> areas) {
        this.areas = areas;
    }

    public CompetenceDTO getCompetence() {
        return this.competence;
    }

    public void setCompetence(final CompetenceDTO competence) {
        this.competence = competence;
    }


}
