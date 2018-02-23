package eu.cehj.cdb2.common.dto;

import java.util.List;

public class BailiffExportDTO{

    private String name;
    private String lang;
    private String address;
    private String postalCode;
    private String municipality;
    private String tel;
    private String fax;
    private boolean videoConference;
    private List<CompetenceExportDTO> competences;

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
    public String getAddress() {
        return this.address;
    }
    public void setAddress(final String address) {
        this.address = address;
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
}
