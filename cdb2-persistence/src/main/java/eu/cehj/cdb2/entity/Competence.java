package eu.cehj.cdb2.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "competence")
public class Competence extends BaseAuditedEntity<String>{

    private static final long serialVersionUID = 1L;

    @Column(name = "code", nullable=false)
    private String code;

    @Column(name = "description", nullable=false)
    private String description;

    @ManyToOne
    @JoinColumn(name="instrument", referencedColumnName = "id")
    private Instrument instrument;

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

    public Instrument getInstrument() {
        return this.instrument;
    }

    public void setInstrument(final Instrument instrument) {
        this.instrument = instrument;
    }
}
