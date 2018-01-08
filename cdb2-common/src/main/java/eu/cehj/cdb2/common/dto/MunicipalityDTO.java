package eu.cehj.cdb2.common.dto;

import eu.cehj.cdb2.db.Municipality;

public class MunicipalityDTO extends BaseDTO{

    private static final long serialVersionUID = -1130739044811870280L;

    private String name;
    private String postalCode;

    public MunicipalityDTO() {
        super();
    }

    public MunicipalityDTO(final Municipality municipality ) {
        super(municipality);
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

}
