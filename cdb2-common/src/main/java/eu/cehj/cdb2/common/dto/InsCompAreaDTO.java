package eu.cehj.cdb2.common.dto;

import java.util.ArrayList;
import java.util.List;

public class InsCompAreaDTO extends BaseDTO {

    private static final long serialVersionUID = 6428148670008512244L;

    private InstrumentDTO instrument;
    private CompetenceDTO competence;
    private List<GeoAreaBasicDTO> geoAreas = new ArrayList<>();
    public InstrumentDTO getInstrument() {
        return this.instrument;
    }
    public void setInstrument(final InstrumentDTO instrument) {
        this.instrument = instrument;
    }
    public CompetenceDTO getCompetence() {
        return this.competence;
    }
    public void setCompetence(final CompetenceDTO competence) {
        this.competence = competence;
    }
    public List<GeoAreaBasicDTO> getGeoAreas() {
        return this.geoAreas;
    }
    public void setGeoAreas(final List<GeoAreaBasicDTO> geoAreas) {
        this.geoAreas = geoAreas;
    }

}
