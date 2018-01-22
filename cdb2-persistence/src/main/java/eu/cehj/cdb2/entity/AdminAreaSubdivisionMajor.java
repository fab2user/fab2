package eu.cehj.cdb2.entity;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name="admin_area_subdivision_major")
public class AdminAreaSubdivisionMajor  extends BaseAuditedEntity<String> {

    private static final long serialVersionUID = -30193309719357585L;


    @Column(name = "code", nullable=false)
    private String code;

    @Column(name = "name", nullable=false)
    private String name;

    @OneToMany(mappedBy="adminAreaSubdivisionMajor")
    private List<AdminAreaSubdivisionMiddle> adminAreaSubdivisionMiddles;

    @OneToMany(mappedBy="adminAreaSubdivisionMajor")
    private List<Municipality> municipalities;

    public String getCode() {
        return this.code;
    }

    public void setCode(final String code) {
        this.code = code;
    }

    public String getName() {
        return this.name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public List<AdminAreaSubdivisionMiddle> getAdminAreaSubdivisionMiddles() {
        return this.adminAreaSubdivisionMiddles;
    }

    public void setAdminAreaSubdivisionMiddles(final List<AdminAreaSubdivisionMiddle> adminAreaSubdivisionMiddles) {
        this.adminAreaSubdivisionMiddles = adminAreaSubdivisionMiddles;
    }

    public List<Municipality> getMunicipalities() {
        return this.municipalities;
    }

    public void setMunicipalities(final List<Municipality> municipalities) {
        this.municipalities = municipalities;
    }

}
