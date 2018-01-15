package eu.cehj.cdb2.common.dto;

import eu.cehj.cdb2.entity.Municipality;

public class MunicipalityDTO extends BaseDTO{

    private static final long serialVersionUID = -1130739044811870280L;

    private String name;
    private String postalCode;
    private String adminAreaSubdivisionMajor;
    private String adminAreaSubdivisionMiddle;
    private String adminAreaSubdivisionMinor;

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

    public String getAdminAreaSubdivisionMajor() {
        return this.adminAreaSubdivisionMajor;
    }

    public void setAdminAreaSubdivisionMajor(final String adminAreaSubdivisionMajor) {
        this.adminAreaSubdivisionMajor = adminAreaSubdivisionMajor;
    }

    public String getAdminAreaSubdivisionMiddle() {
        return this.adminAreaSubdivisionMiddle;
    }

    public void setAdminAreaSubdivisionMiddle(final String adminAreaSubdivisionMiddle) {
        this.adminAreaSubdivisionMiddle = adminAreaSubdivisionMiddle;
    }

    public String getAdminAreaSubdivisionMinor() {
        return this.adminAreaSubdivisionMinor;
    }

    public void setAdminAreaSubdivisionMinor(final String adminAreaSubdivisionMinor) {
        this.adminAreaSubdivisionMinor = adminAreaSubdivisionMinor;
    }

}
