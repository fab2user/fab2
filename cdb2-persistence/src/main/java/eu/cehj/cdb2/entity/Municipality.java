package eu.cehj.cdb2.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "municipality")
public class Municipality extends BaseAuditedEntity<Long> {

    private static final long serialVersionUID = -1302962165492457412L;

    @Column(name = "postal_code")
    private String postalCode;

    @Column(name = "name")
    private String name;

    @Column(name = "latitude")
    private String latitude;

    @Column(name = "longitude")
    private String longitude;

    @Column(name = "major_area")
    @ManyToOne
    private AdminAreaSubdivision majorArea;

    @Column(name = "middle_area")
    @ManyToOne
    private AdminAreaSubdivision middleArea;

    @Column(name = "minor_area")
    @ManyToOne
    private AdminAreaSubdivision minorArea;

    public Municipality() {
        super();
    }

    public String getPostalCode() {
        return this.postalCode;
    }

    public void setPostalCode(final String postalCode) {
        this.postalCode = postalCode;
    }

    public String getName() {
        return this.name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public String getLatitude() {
        return this.latitude;
    }

    public void setLatitude(final String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return this.longitude;
    }

    public void setLongitude(final String longitude) {
        this.longitude = longitude;
    }

    public AdminAreaSubdivision getMajorArea() {
        return this.majorArea;
    }

    public void setMajorArea(final AdminAreaSubdivision majorArea) {
        this.majorArea = majorArea;
    }

    public AdminAreaSubdivision getMiddleArea() {
        return this.middleArea;
    }

    public void setMiddleArea(final AdminAreaSubdivision middleArea) {
        this.middleArea = middleArea;
    }

    public AdminAreaSubdivision getMinorArea() {
        return this.minorArea;
    }

    public void setMinorArea(final AdminAreaSubdivision minorArea) {
        this.minorArea = minorArea;
    }

}
