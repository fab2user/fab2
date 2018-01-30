package eu.cehj.cdb2.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Where;

@Entity
@Table(name = "bailiff")
@Where(clause="deleted=0 or deleted is null")
public class Bailiff extends BaseAuditedEntity<String> {

    private static final long serialVersionUID = -7834873765738494098L;

    @Column(name = "name", nullable=false)
    private String name;

    @ManyToOne
    @JoinColumn(name = "address")
    private Address address;


    @ManyToMany
    @JoinTable(name = "rel_bailiff_lang",
    joinColumns = @JoinColumn(name = "bailiff_id"),
    inverseJoinColumns = @JoinColumn(name = "lang_id"))
    private final List<Language> languages = new ArrayList<>();

    @OneToMany(mappedBy="bailiff")
    private final List<BailiffCompetenceArea> bailiffCompetenceAreas = new ArrayList<>();

    @Column(name = "phone")
    private String phone;

    @Column(name = "email")
    private String email;

    public String getName() {
        return this.name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public Address getAddress() {
        return this.address;
    }

    public void setAddress(final Address address) {
        this.address = address;
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

    public List<Language> getLanguages() {
        return this.languages;
    }

    public List<BailiffCompetenceArea> getBailiffCompetenceAreas() {
        return this.bailiffCompetenceAreas;
    }

}
