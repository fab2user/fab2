package eu.cehj.cdb2.common.dto;

import java.util.List;

public class CompetenceExportDTO {

	private String instrument;
	private String type;
	private List<String> geoAreaIds;
	private String geoAreaId; // backward compatibility


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
	public List<String> getGeoAreaIds() {
		return this.geoAreaIds;
	}
	public void setGeoAreaIds(final List<String> geoAreaId) {
		this.geoAreaIds = geoAreaId;
	}
	public String getGeoAreaId() {
		return this.geoAreaId;
	}
	public void setGeoAreaId(final String geoAreaId) {
		this.geoAreaId = geoAreaId;
	}

}
