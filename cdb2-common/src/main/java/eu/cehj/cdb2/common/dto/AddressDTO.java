package eu.cehj.cdb2.common.dto;

import eu.cehj.cdb2.entity.Address;

public class AddressDTO extends BaseDTO{

    private static final long serialVersionUID = 2598684988211269807L;
    private String address;
    private String postalCode;
    private String municipality;
    private String phone;
    private String email;

    public AddressDTO() {
        super();
    }

    public AddressDTO(final Address address ) {
        super(address);
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


}
