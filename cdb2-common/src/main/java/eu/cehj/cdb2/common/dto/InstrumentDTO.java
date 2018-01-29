package eu.cehj.cdb2.common.dto;

import java.util.List;

import eu.cehj.cdb2.entity.Instrument;

public class InstrumentDTO extends BaseDTO{

    private static final long serialVersionUID = 4393148391489294469L;

    private String code;
    private String description;
    private List<CompetenceDTO> competences;

    public InstrumentDTO() {
        super();
    }

    public InstrumentDTO(final Instrument instrument ) {
        super(instrument);
    }

    public String getCode() {
        return this.code;
    }

    public void setCode(final String code) {
        this.code = code;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(final String description) {
        this.description = description;
    }

    public List<CompetenceDTO> getCompetences() {
        return this.competences;
    }

    public void setCompetences(final List<CompetenceDTO> competences) {
        this.competences = competences;
    }

}
