package eu.cehj.cdb2.common.dto;

public class BailiffCompetenceAreaDTO extends BaseDTO{

    private static final long serialVersionUID = -2685453922078212828L;

    private BailiffDTO  bailiffDTO;
    private GeoAreaDTO areaDTO;
    private CompetenceDTO competenceDTO;

    public BailiffCompetenceAreaDTO() {
        super();
    }

    public BailiffDTO getBailiffDTO() {
        return this.bailiffDTO;
    }

    public void setBailiffDTO(final BailiffDTO bailiffDTO) {
        this.bailiffDTO = bailiffDTO;
    }

    public GeoAreaDTO getAreaDTO() {
        return this.areaDTO;
    }

    public void setAreaDTO(final GeoAreaDTO areaDTO) {
        this.areaDTO = areaDTO;
    }

    public CompetenceDTO getCompetenceDTO() {
        return this.competenceDTO;
    }

    public void setCompetenceDTO(final CompetenceDTO competenceDTO) {
        this.competenceDTO = competenceDTO;
    }



}
