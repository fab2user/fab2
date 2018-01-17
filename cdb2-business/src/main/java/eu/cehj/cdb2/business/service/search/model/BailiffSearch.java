package eu.cehj.cdb2.business.service.search.model;

public class BailiffSearch {

    private String name;
    private String address;
    private String postalCode;
    private String municipality;

    public String getName() {
        return this.name;
    }
    public void setName(final String name) {
        this.name = name;
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
}
