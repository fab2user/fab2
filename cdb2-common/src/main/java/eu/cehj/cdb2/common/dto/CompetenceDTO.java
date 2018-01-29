package eu.cehj.cdb2.common.dto;

import eu.cehj.cdb2.entity.Competence;

public class CompetenceDTO extends BaseDTO {

    private static final long serialVersionUID = 8351597351634743014L;

    private String code;
    private String description;
    private InstrumentDTO instrument;

    public CompetenceDTO() {
        super();
    }

    public CompetenceDTO(final Competence competence) {
        super(competence);
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

    public InstrumentDTO getInstrument() {
        return this.instrument;
    }

    public void setInstrument(final InstrumentDTO instrument) {
        this.instrument = instrument;
    }

}
