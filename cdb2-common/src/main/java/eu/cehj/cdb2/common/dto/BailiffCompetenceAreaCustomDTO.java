package eu.cehj.cdb2.common.dto;

import java.util.List;

public class BailiffCompetenceAreaCustomDTO extends BaseDTO{

    private static final long serialVersionUID = 7768012662022179569L;

    private BailiffDTO  bailiff;
    private List<GeoAreaSimpleDTO> areas;
    private SimpleCompetenceDTO competence;

    public BailiffCompetenceAreaCustomDTO() {
        super();
    }

    public BailiffDTO getBailiff() {
        return this.bailiff;
    }

    public void setBailiff(final BailiffDTO bailiff) {
        this.bailiff = bailiff;
    }

    public List<GeoAreaSimpleDTO> getAreas() {
        return this.areas;
    }

    public void setAreas(final List<GeoAreaSimpleDTO> areas) {
        this.areas = areas;
    }

    public SimpleCompetenceDTO getCompetence() {
        return this.competence;
    }

    public void setCompetence(final SimpleCompetenceDTO competence) {
        this.competence = competence;
    }


}
