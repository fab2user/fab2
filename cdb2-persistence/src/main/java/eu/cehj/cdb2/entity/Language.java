package eu.cehj.cdb2.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

@Entity
@Table(name = "language")
public class Language extends BaseAuditedEntity<String>{

    private static final long serialVersionUID = -1903127286011981312L;

    @Column(name = "code", nullable=false)
    private String code;

    @Column(name = "language", nullable=false)
    private String language;

    @ManyToMany(mappedBy = "languages")
    private final List<Bailiff> bailiffs = new ArrayList<>();

    public String getCode() {
        return this.code;
    }

    public void setCode(final String code) {
        this.code = code;
    }

    public String getLanguage() {
        return this.language;
    }

    public void setLanguage(final String language) {
        this.language = language;
    }

    public List<Bailiff> getBailiffs() {
        return this.bailiffs;
    }

}
