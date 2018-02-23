package eu.cehj.cdb2.common.dto;

import java.util.ArrayList;
import java.util.List;

import eu.cehj.cdb2.entity.Address;
public class BailiffDTO extends BaseDTO{

    private static final long serialVersionUID = 6204512676632176957L;

    private String name;
    private String address;
    private String postalCode;
    private String city;
    private String phone;
    private String email;
    private Long addressId;
    private Long municipalityId;
    private List<Long> languages = new ArrayList<>();
    private Long langOfDetails;

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



}
