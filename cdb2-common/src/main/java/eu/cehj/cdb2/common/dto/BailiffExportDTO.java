package eu.cehj.cdb2.common.dto;

import java.util.List;

public class BailiffExportDTO{

	private Long id;
	private String nationalId;
	private String name;
    private String lang;
    private String address1;
    private String address2;
    private String postalCode;
    private String municipality;
    private String tel;
    private String fax;
    private String email;
    private boolean videoConference;
    private List<CompetenceExportDTO> competences;

    public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
    public String getName() {
        return this.name;
    }
    public void setName(final String name) {
        this.name = name;
    }
    public String getLang() {
        return this.lang;
    }
    public void setLang(final String lang) {
        this.lang = lang;
    }
    public String getPostalCode() {
        return this.postalCode;
    }
    public void setPostalCode(final String postalCode) {
        this.postalCode = postalCode;
    }
    public String getMunicipality() {
        return this.municipality;
    }
    public void setMunicipality(final String municipality) {
        this.municipality = municipality;
    }
    public String getTel() {
        return this.tel;
    }
    public void setTel(final String tel) {
        this.tel = tel;
    }
    public String getFax() {
        return this.fax;
    }
    public void setFax(final String fax) {
        this.fax = fax;
    }
    public boolean isVideoConference() {
        return this.videoConference;
    }
    public void setVideoConference(final boolean videoConference) {
        this.videoConference = videoConference;
    }
    public List<CompetenceExportDTO> getCompetences() {
        return this.competences;
    }
    public void setCompetences(final List<CompetenceExportDTO> competences) {
        this.competences = competences;
    }
    public String getEmail() {
        return this.email;
    }
    public void setEmail(final String email) {
        this.email = email;
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
	public String getNationalId() {
		return nationalId;
	}
	public void setNationalId(String nationalId) {
		this.nationalId = nationalId;
	}
}
