package eu.cehj.cdb2.business.utils;

import java.util.Properties;

public class BailiffImportModel {

    private int id;
    private int name;
    private int lang;
    private int address;
    private int postalCode;
    private int municipality;
    private int phone;
    private int fax;
    private int email;
    private int webSite;

    public BailiffImportModel(final Properties propertiesFile) {
        this.setId(Integer.parseInt(propertiesFile.getProperty("id")));
        this.setName(Integer.parseInt(propertiesFile.getProperty("name")));
        this.setLang(Integer.parseInt(propertiesFile.getProperty("lang")));
        this.setAddress(Integer.parseInt(propertiesFile.getProperty("address")));
        this.setPostalCode(Integer.parseInt(propertiesFile.getProperty("postalCode")));
        this.setMunicipality(Integer.parseInt(propertiesFile.getProperty("municipality")));
        this.setPhone(Integer.parseInt(propertiesFile.getProperty("phone")));
        this.setFax(Integer.parseInt(propertiesFile.getProperty("fax")));
        this.setEmail(Integer.parseInt(propertiesFile.getProperty("email")));
        this.setWebSite(Integer.parseInt(propertiesFile.getProperty("webSite")));
    }

    public int getId() {
        return this.id;
    }

    public void setId(final int id) {
        this.id = id;
    }

    public int getName() {
        return this.name;
    }

    public void setName(final int name) {
        this.name = name;
    }

    public int getLang() {
        return this.lang;
    }

    public void setLang(final int lang) {
        this.lang = lang;
    }

    public int getAddress() {
        return this.address;
    }

    public void setAddress(final int address) {
        this.address = address;
    }

    public int getPostalCode() {
        return this.postalCode;
    }

    public void setPostalCode(final int postalCode) {
        this.postalCode = postalCode;
    }

    public int getMunicipality() {
        return this.municipality;
    }

    public void setMunicipality(final int municipality) {
        this.municipality = municipality;
    }

    public int getPhone() {
        return this.phone;
    }

    public void setPhone(final int phone) {
        this.phone = phone;
    }

    public int getFax() {
        return this.fax;
    }

    public void setFax(final int fax) {
        this.fax = fax;
    }

    public int getEmail() {
        return this.email;
    }

    public void setEmail(final int email) {
        this.email = email;
    }

    public int getWebSite() {
        return this.webSite;
    }

    public void setWebSite(final int webSite) {
        this.webSite = webSite;
    }


}
