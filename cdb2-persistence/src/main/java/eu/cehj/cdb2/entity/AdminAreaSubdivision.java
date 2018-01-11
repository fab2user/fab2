package eu.cehj.cdb2.entity;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name="admin_area_subdivision")
public class AdminAreaSubdivision extends BaseEntity {

    private static final long serialVersionUID = -30193309719357585L;

    @Enumerated(EnumType.STRING)
    @Column(name = "type")
    private AreaType type;

    @Column(name = "code")
    private String code;

    @Column(name = "name")
    private String name;

    @ManyToOne
    @Column(name = "parent_area")
    private AdminAreaSubdivision parentArea;

    @OneToMany(mappedBy="parent_area")
    private List<AdminAreaSubdivision> childrenAreas;

}
