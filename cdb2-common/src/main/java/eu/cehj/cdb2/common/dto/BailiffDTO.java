package eu.cehj.cdb2.common.dto;

import java.util.ArrayList;
import java.util.List;

import eu.cehj.cdb2.entity.Address;

public class BailiffDTO extends BaseDTO{

	private static final long serialVersionUID = 6204512676632176957L;

	private String name;
	private String nationalIdPrefix;
	private String nationalId;
	private String address1;
	private String address2;
	private String postalCode;
	private String city;
	private String phone;
	private String email;
	private Long addressId;
	private Long municipalityId;
	private List<Long> languages = new ArrayList<>();
	private List<String> languagesCode = new ArrayList<>();
	private Long langOfDetails;
	private String webSite;
	private String fax;
	private String openHours;
	private boolean videoConferenceAvailable = false;
	private String comments;
	private String langDisplay; // used only for CDB for now but maybe relevant for other cases too
	private List<CompetenceDTO> competences;
	private List<Long> instrumentIds;
	private List<Long> competenceIds;
	private GeoAreaSimpleDTO geo;
	private List<GeoAreaSimpleDTO> geoAreas  = new ArrayList<>();

	private boolean toBeUpdated = false;

	public BailiffDTO() {
		super();
	}

	public BailiffDTO(final Address address ) {
		super(address);
	}

	public String getName() {
		return this.name;
	}

	public void setName(final String name) {
		this.name = name;
	}

	public String getPostalCode() {
		return this.postalCode;
	}

	public void setPostalCode(final String postalCode) {
		this.postalCode = postalCode;
	}

	public String getPhone() {
		return this.phone;
	}

	public void setPhone(final String phone) {
		this.phone = phone;
	}

	public String getEmail() {
		return this.email;
	}

	public void setEmail(final String email) {
		this.email = email;
	}

	public Long getAddressId() {
		return this.addressId;
	}

	public void setAddressId(final Long addressId) {
		this.addressId = addressId;
	}

	public Long getMunicipalityId() {
		return this.municipalityId;
	}

	public void setMunicipalityId(final Long municipalityId) {
		this.municipalityId = municipalityId;
	}

	public String getCity() {
		return this.city;
	}

	public void setCity(final String city) {
		this.city = city;
	}

	public Long getLangOfDetails() {
		return this.langOfDetails;
	}

	public void setLangOfDetails(final Long langOfDetails) {
		this.langOfDetails = langOfDetails;
	}

	public List<Long> getLanguages() {
		return this.languages;
	}

	public void setLanguages(final List<Long> languages) {
		this.languages = languages;
	}

	public String getWebSite() {
		return this.webSite;
	}

	public void setWebSite(final String webSite) {
		this.webSite = webSite;
	}

	public String getFax() {
		return this.fax;
	}

	public void setFax(final String fax) {
		this.fax = fax;
	}

	public String getAddress1() {
		return this.address1;
	}

	public void setAddress1(final String address1) {
		this.address1 = address1;
	}

	public String getAddress2() {
		return this.address2;
	}

	public void setAddress2(final String address2) {
		this.address2 = address2;
	}

	public List<CompetenceDTO> getCompetences() {
		if(this.competences != null) {
			return this.competences;
		}
		return new ArrayList<>();
	}

	public void setCompetences(final List<CompetenceDTO> competences) {
		this.competences = competences;
	}

	public GeoAreaSimpleDTO getGeo() {
		return this.geo;
	}

	public void setGeo(final GeoAreaSimpleDTO geo) {
		this.geo = geo;
	}

	public boolean isToBeUpdated() {
		return this.toBeUpdated;
	}

	public void setToBeUpdated(final boolean toBeUpdated) {
		this.toBeUpdated = toBeUpdated;
	}

	public String getOpenHours() {
		return this.openHours;
	}

	public void setOpenHours(final String openHours) {
		this.openHours = openHours;
	}

	public boolean isVideoConferenceAvailable() {
		return this.videoConferenceAvailable;
	}

	public void setVideoConferenceAvailable(final boolean videoConferenceAvailable) {
		this.videoConferenceAvailable = videoConferenceAvailable;
	}

	public String getLangDisplay() {
		return this.langDisplay;
	}

	public void setLangDisplay(final String langDisplay) {
		this.langDisplay = langDisplay;
	}

	public String getNationalId() {
		return this.nationalId;
	}

	public void setNationalId(final String nationalId) {
		this.nationalId = nationalId;
	}

	public String getNationalIdPrefix() {
		return this.nationalIdPrefix;
	}

	public void setNationalIdPrefix(final String nationalIdPrefix) {
		this.nationalIdPrefix = nationalIdPrefix;
	}

	public String getComments() {
		return this.comments;
	}

	public void setComments(final String comments) {
		this.comments = comments;
	}

	public List<Long> getInstrumentIds() {
		return this.instrumentIds;
	}

	public void setInstrumentIds(final List<Long> instrumentIds) {
		this.instrumentIds = instrumentIds;
	}

	public List<Long> getCompetenceIds() {
		return this.competenceIds;
	}

	public void setCompetenceIds(final List<Long> competenceIds) {
		this.competenceIds = competenceIds;
	}

	public List<GeoAreaSimpleDTO> getGeoAreas() {
		return this.geoAreas;
	}

	public void setGeoAreas(final List<GeoAreaSimpleDTO> geoAreas) {
		this.geoAreas = geoAreas;
	}

	public List<String> getLanguagesCode() {
		return this.languagesCode;
	}

	public void setLanguagesCode(final List<String> languagesCode) {
		this.languagesCode = languagesCode;
	}

	@Override
	public String toString() {
		return "BailiffDTO [name=" + this.name + ", nationalIdPrefix=" + this.nationalIdPrefix + ", nationalId=" + this.nationalId + ", address1=" + this.address1 + ", address2=" + this.address2
				+ ", postalCode=" + this.postalCode + ", city=" + this.city + ", phone=" + this.phone + ", email=" + this.email + ", addressId=" + this.addressId + ", municipalityId=" + this.municipalityId
				+ ", languages=" + this.languages + ", langOfDetails=" + this.langOfDetails + ", webSite=" + this.webSite + ", fax=" + this.fax + ", openHours=" + this.openHours
				+ ", videoConferenceAvailable=" + this.videoConferenceAvailable + ", comments=" + this.comments + ", langDisplay=" + this.langDisplay + ", competences=" + this.competences + ", geo="
				+ this.geo + ", toBeUpdated=" + this.toBeUpdated + "]";
	}



}
