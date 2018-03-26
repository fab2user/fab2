package eu.cehj.cdb2.common.dto;

import eu.cehj.cdb2.entity.Competence;

public class SimpleCompetenceDTO extends BaseDTO {

    private static final long serialVersionUID = 8351597351634743014L;

    private String code;
    private String description;

    private SimpleInstrumentDTO instrument;

    public SimpleCompetenceDTO() {
        super();
    }

    public SimpleCompetenceDTO(final Competence competence) {
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

    public SimpleInstrumentDTO getInstrument() {
        return this.instrument;
    }

    public void setInstrument(final SimpleInstrumentDTO instrument) {
        this.instrument = instrument;
    }


}
