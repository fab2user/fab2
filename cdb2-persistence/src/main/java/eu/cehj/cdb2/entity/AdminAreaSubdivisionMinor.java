package eu.cehj.cdb2.entity;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Where;

@Entity
@Table(name="admin_area_subdivision_minor")
@Where(clause="deleted=0 or deleted is null")
public class AdminAreaSubdivisionMinor  extends BaseAuditedEntity<String> {

    private static final long serialVersionUID = 6054044844537040037L;

    @Column(name = "code", nullable=false)
    private String code;

    @Column(name = "name", nullable=false)
    private String name;

    @ManyToOne
    @JoinColumn(name = "admin_area_subdivision_middle", referencedColumnName = "id")
    private AdminAreaSubdivisionMiddle adminAreaSubdivisionMiddle;

    @OneToMany(mappedBy="adminAreaSubdivisionMinor")
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

    public AdminAreaSubdivisionMiddle getAdminAreaSubdivisionMiddle() {
        return this.adminAreaSubdivisionMiddle;
    }

    public void setAdminAreaSubdivisionMiddle(final AdminAreaSubdivisionMiddle adminAreaSubdivisionMiddle) {
        this.adminAreaSubdivisionMiddle = adminAreaSubdivisionMiddle;
    }

    public List<Municipality> getMunicipalities() {
        return this.municipalities;
    }

    public void setMunicipalities(final List<Municipality> municipalities) {
        this.municipalities = municipalities;
    }

}
