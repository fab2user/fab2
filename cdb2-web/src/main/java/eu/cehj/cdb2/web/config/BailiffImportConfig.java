package eu.cehj.cdb2.web.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@ConfigurationProperties(prefix = "cell")
@PropertySource("${cellOrderPropertiesFile}")
public class BailiffImportConfig {

    private int name;
    private int lang;
    private int address1;
    private int address2;
    private int postalCode;
    private int municipality;
    private int phone;
    private int fax;
    private int email;
    private int webSite;

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
    public int getAddress1() {
        return this.address1;
    }
    public void setAddress1(final int address1) {
        this.address1 = address1;
    }
    public int getAddress2() {
        return this.address2;
    }
    public void setAddress2(final int address2) {
        this.address2 = address2;
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
