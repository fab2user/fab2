package eu.cehj.cdb2.business.service.data;

public class GeoDataUpdateStructure implements DataStructure {

	private String countryCode;
	private String zipCode;
	private String cityName;
	private String majorAreaName;
	private String majorAreaCode;
	private String middleAreaName;
	private String middleAreaCode;
	private String minorAreaName;
	private String minorAreaCode;
	private String xPos;
	private String yPos;

	public String getCountryCode() {
		return this.countryCode;
	}
	public void setCountryCode(final String countryCode) {
		this.countryCode = countryCode;
	}
	public String getZipCode() {
		return this.zipCode;
	}
	public void setZipCode(final String zipCode) {
		this.zipCode = zipCode;
	}
	public String getCityName() {
		return this.cityName;
	}
	public void setCityName(final String cityName) {
		this.cityName = cityName;
	}
	public String getMajorAreaName() {
		return this.majorAreaName;
	}
	public void setMajorAreaName(final String majorAreaName) {
		this.majorAreaName = majorAreaName;
	}
	public String getMajorAreaCode() {
		return this.majorAreaCode;
	}
	public void setMajorAreaCode(final String majorAreaCode) {
		this.majorAreaCode = majorAreaCode;
	}
	public String getMiddleAreaName() {
		return this.middleAreaName;
	}
	public void setMiddleAreaName(final String middleAreaName) {
		this.middleAreaName = middleAreaName;
	}
	public String getMiddleAreaCode() {
		return this.middleAreaCode;
	}
	public void setMiddleAreaCode(final String middleAreaCode) {
		this.middleAreaCode = middleAreaCode;
	}
	public String getMinorAreaName() {
		return this.minorAreaName;
	}
	public void setMinorAreaName(final String minorAreaName) {
		this.minorAreaName = minorAreaName;
	}
	public String getMinorAreaCode() {
		return this.minorAreaCode;
	}
	public void setMinorAreaCode(final String minorAreaCode) {
		this.minorAreaCode = minorAreaCode;
	}
	public String getxPos() {
		return this.xPos;
	}
	public void setxPos(final String xPos) {
		this.xPos = xPos;
	}
	public String getyPos() {
		return this.yPos;
	}
	public void setyPos(final String yPos) {
		this.yPos = yPos;
	}

	@Override
	public String toString() {
		return "GeoDataStructure [countryCode=" + this.countryCode + ", zipCode=" + this.zipCode + ", cityName=" + this.cityName + ", majorAreaName=" + this.majorAreaName + ", majorAreaCode="
				+ this.majorAreaCode + ", middleAreaName=" + this.middleAreaName + ", middleAreaCode=" + this.middleAreaCode + ", minorAreaName=" + this.minorAreaName + ", minorAreaCode="
				+ this.minorAreaCode + ", xPos=" + this.xPos + ", yPos=" + this.yPos + "]";
	}


}
