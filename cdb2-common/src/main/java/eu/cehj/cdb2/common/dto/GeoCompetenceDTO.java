package eu.cehj.cdb2.common.dto;

public class GeoCompetenceDTO extends BaseDTO {

    private static final long serialVersionUID = -8197168029778664506L;

    private Long bailiffId;
    private Long competenceId;
    private Long instrumentId;
    private Long areaId;
    private Long bailiffCompetenceAreId;

    public Long getBailiffId() {
        return this.bailiffId;
    }
    public void setBailiffId(final Long bailiffId) {
        this.bailiffId = bailiffId;
    }
    public Long getCompetenceId() {
        return this.competenceId;
    }
    public void setCompetenceId(final Long competenceId) {
        this.competenceId = competenceId;
    }
    public Long getInstrumentId() {
        return this.instrumentId;
    }
    public void setInstrumentId(final Long instrumentId) {
        this.instrumentId = instrumentId;
    }
    public Long getAreaId() {
        return this.areaId;
    }
    public void setAreaId(final Long areaId) {
        this.areaId = areaId;
    }
    public Long getBailiffCompetenceAreId() {
        return this.bailiffCompetenceAreId;
    }
    public void setBailiffCompetenceAreId(final Long bailiffCompetenceAreId) {
        this.bailiffCompetenceAreId = bailiffCompetenceAreId;
    }
}
