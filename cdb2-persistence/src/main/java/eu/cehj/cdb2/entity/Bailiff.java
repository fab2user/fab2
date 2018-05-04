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

    @ManyToMany
    @JoinTable(name = "rel_bailiff_lang_detail",
    joinColumns = @JoinColumn(name = "bailiff_id"),
    inverseJoinColumns = @JoinColumn(name = "lang_id"))
    private final List<Language> langOfDetails = new ArrayList<>();

    @OneToMany(mappedBy="bailiff")
    @Where(clause = "deleted is null or deleted = false") // Highly important in order to pull only non-deleted records
    private final List<BailiffCompetenceArea> bailiffCompetenceAreas = new ArrayList<>();

    @Column(name = "phone")
    private String phone;

    @Column(name = "email")
    private String email;

    @Column(name = "fax")
    private String fax;

    @Column(name = "web_site")
    private String webSite;

    @Column(name = "open_hours")
    private String openHours;

    @Column(name = "video_conference", nullable = false)
    private boolean videoConference;

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

    public List<Language> getLangOfDetails() {
        return this.langOfDetails;
    }

    public String getFax() {
        return this.fax;
    }

    public void setFax(final String fax) {
        this.fax = fax;
    }

    public String getWebSite() {
        return this.webSite;
    }

    public void setWebSite(final String webSite) {
        this.webSite = webSite;
    }

    public boolean isVideoConference() {
        return this.videoConference;
    }

    public void setVideoConference(final boolean videoConference) {
        this.videoConference = videoConference;
    }

    public String getOpenHours() {
        return this.openHours;
    }

    public void setOpenHours(final String openHours) {
        this.openHours = openHours;
    }

}
