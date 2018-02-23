package eu.cehj.cdb2.common.dto;

public class CompetenceExportDTO {

    private String instrument;
    private String type;
    private String geoAreaId;

    public String getInstrument() {
        return this.instrument;
    }
    public void setInstrument(final String instrument) {
        this.instrument = instrument;
    }
    public String getType() {
        return this.type;
    }
    public void setType(final String type) {
        this.type = type;
    }
    public String getGeoAreaId() {
        return this.geoAreaId;
    }
    public void setGeoAreaId(final String geoAreaId) {
        this.geoAreaId = geoAreaId;
    }
}
