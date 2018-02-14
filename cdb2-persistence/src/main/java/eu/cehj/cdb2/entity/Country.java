package eu.cehj.cdb2.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "country")
public class Country extends BaseAuditedEntity<String> {

    private static final long serialVersionUID = 929915752303090598L;

    @Column(name = "name", nullable=false)
    private String name;

    @Column(name = "url")
    private String url;

    @Column(name = "active")
    private boolean active = true;

    @OneToMany(mappedBy="country")
    private final List<Synchronization> synchronizations = new ArrayList<>();


    public String getName() {
        return this.name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public String getUrl() {
        return this.url;
    }

    public void setUrl(final String url) {
        this.url = url;
    }

    public boolean isActive() {
        return this.active;
    }

    public void setActive(final boolean active) {
        this.active = active;
    }

    public List<Synchronization> getSynchronizations() {
        return this.synchronizations;
    }



}
