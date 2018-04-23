package eu.cehj.cdb2.common.dto;

import eu.cehj.cdb2.entity.Competence;

public class CompetenceForSelectDTO extends BaseDTO {

    private Long id;
    private String code;
    private String description;
    private Long instrumentId;
    private String instrumentCode;
    private String instrumentDescription;


    public CompetenceForSelectDTO() {
        super();
    }

    public CompetenceForSelectDTO(final Competence competence) {
        super(competence);
    }

    @Override
    public Long getId() {
        return this.id;
    }

    @Override
    public void setId(final Long id) {
        this.id = id;
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

    public Long getInstrumentId() {
        return this.instrumentId;
    }

    public void setInstrumentId(final Long instrumentId) {
        this.instrumentId = instrumentId;
    }

    public String getInstrumentCode() {
        return this.instrumentCode;
    }

    public void setInstrumentCode(final String instrumentCode) {
        this.instrumentCode = instrumentCode;
    }

    public String getInstrumentDescription() {
        return this.instrumentDescription;
    }

    public void setInstrumentDescription(final String instrumentDescription) {
        this.instrumentDescription = instrumentDescription;
    }

}