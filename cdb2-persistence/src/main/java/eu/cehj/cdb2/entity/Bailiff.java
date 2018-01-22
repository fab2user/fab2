package eu.cehj.cdb2.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "bailiff")
public class Bailiff extends BaseAuditedEntity<String> {

    private static final long serialVersionUID = -7834873765738494098L;

    @Column(name = "name", nullable=false)
    private String name;

    @ManyToOne
    @JoinColumn(name = "address")
    private Address address;


    @ManyToMany(cascade = {
            CascadeType.PERSIST,
            CascadeType.MERGE
    })
    @JoinTable(name = "rel_bailiff_lang",
    joinColumns = @JoinColumn(name = "bailiff_id"),
    inverseJoinColumns = @JoinColumn(name = "lang_id")
            )
    private final List<Language> languages = new ArrayList<>();

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

}
