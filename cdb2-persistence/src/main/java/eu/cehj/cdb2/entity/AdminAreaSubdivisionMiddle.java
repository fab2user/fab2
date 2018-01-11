package eu.cehj.cdb2.entity;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name="admin_area_subdivision_middle")
public class AdminAreaSubdivisionMiddle extends BaseEntity {

    private static final long serialVersionUID = -6751495992407562325L;

    @Column(name = "code")
    private String code;

    @Column(name = "name")
    private String name;

    @ManyToOne
    @JoinColumn(name = "admin_area_subdivision_major", referencedColumnName = "id")
    private AdminAreaSubdivisionMajor adminAreaSubdivisionMajor;

    @OneToMany(mappedBy="adminAreaSubdivisionMiddle")
    private List<AdminAreaSubdivisionMinor> adminAreaSubdivisionMinors;

    @OneToMany(mappedBy="adminAreaSubdivisionMiddle")
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

    public AdminAreaSubdivisionMajor getAdminAreaSubdivisionMajor() {
        return this.adminAreaSubdivisionMajor;
    }

    public void setAdminAreaSubdivisionMajor(final AdminAreaSubdivisionMajor adminAreaSubdivisionMajor) {
        this.adminAreaSubdivisionMajor = adminAreaSubdivisionMajor;
    }

    public List<AdminAreaSubdivisionMinor> getAdminAreaSubdivisionMinors() {
        return this.adminAreaSubdivisionMinors;
    }

    public void setAdminAreaSubdivisionMinors(final List<AdminAreaSubdivisionMinor> adminAreaSubdivisionMinors) {
        this.adminAreaSubdivisionMinors = adminAreaSubdivisionMinors;
    }

    public List<Municipality> getMunicipalities() {
        return this.municipalities;
    }

    public void setMunicipalities(final List<Municipality> municipalities) {
        this.municipalities = municipalities;
    }

}
