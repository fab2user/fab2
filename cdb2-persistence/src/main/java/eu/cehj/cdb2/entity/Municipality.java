package eu.cehj.cdb2.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "municipality")
public class Municipality extends BaseAuditedEntity<String> {

    private static final long serialVersionUID = -1302962165492457412L;

    @Column(name = "postal_code", nullable=false)
    private String postalCode;

    @Column(name = "name", nullable=false)
    private String name;

    @Column(name = "latitude")
    private String latitude;

    @Column(name = "longitude")
    private String longitude;

    @ManyToOne
    @JoinColumn(name = "admin_area_subdivision_major")
    private AdminAreaSubdivisionMajor adminAreaSubdivisionMajor;

    @ManyToOne
    @JoinColumn(name = "admin_area_subdivision_middle", referencedColumnName = "id")
    private AdminAreaSubdivisionMiddle adminAreaSubdivisionMiddle;

    @ManyToOne
    @JoinColumn(name = "admin_area_subdivision_minor", referencedColumnName = "id")
    private AdminAreaSubdivisionMinor adminAreaSubdivisionMinor;

    @ManyToOne
    @JoinColumn(name = "geo_area", referencedColumnName = "id")
    private GeoArea geoArea;

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

    public AdminAreaSubdivisionMajor getAdminAreaSubdivisionMajor() {
        return this.adminAreaSubdivisionMajor;
    }

    public void setAdminAreaSubdivisionMajor(final AdminAreaSubdivisionMajor adminAreaSubdivisionMajor) {
        this.adminAreaSubdivisionMajor = adminAreaSubdivisionMajor;
    }

    public AdminAreaSubdivisionMiddle getAdminAreaSubdivisionMiddle() {
        return this.adminAreaSubdivisionMiddle;
    }

    public void setAdminAreaSubdivisionMiddle(final AdminAreaSubdivisionMiddle adminAreaSubdivisionMiddle) {
        this.adminAreaSubdivisionMiddle = adminAreaSubdivisionMiddle;
    }

    public AdminAreaSubdivisionMinor getAdminAreaSubdivisionMinor() {
        return this.adminAreaSubdivisionMinor;
    }

    public void setAdminAreaSubdivisionMinor(final AdminAreaSubdivisionMinor adminAreaSubdivisionMinor) {
        this.adminAreaSubdivisionMinor = adminAreaSubdivisionMinor;
    }

    public GeoArea getGeoArea() {
        return this.geoArea;
    }

    public void setGeoArea(final GeoArea geoArea) {
        this.geoArea = geoArea;
    }


}
