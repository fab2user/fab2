package eu.cehj.cdb2.entity;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "instrument")
public class Instrument extends BaseAuditedEntity<Long>{

    private static final long serialVersionUID = -8486637090683919492L;

    @Column(name = "code", nullable=false)
    private String code;

    @Column(name = "description", nullable=false)
    private String description;

    @OneToMany(mappedBy="instrument")
    private List<Competence> competences;

    public String getCode() {
        return this.code;
    }

    public void setCode(final String code) {
        this.code = code;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(final String description) {
        this.description = description;
    }

    public List<Competence> getCompetences() {
        return this.competences;
    }

    public void setCompetences(final List<Competence> competences) {
        this.competences = competences;
    }

}
