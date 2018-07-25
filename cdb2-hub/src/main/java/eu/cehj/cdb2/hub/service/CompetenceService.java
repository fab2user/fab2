package eu.cehj.cdb2.hub.service;

import java.io.Serializable;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class CompetenceService implements Serializable{

    private static final long serialVersionUID = -2219853515857498842L;

    @Value("${cdb.competences}")
    private List<String> competences;

    @Value("${cdb.instruments}")
    private List<String> instruments;

    public List<String> getCompetences() {
        return this.competences;
    }

    public void setCompetences(final List<String> competences) {
        this.competences = competences;
    }

    public List<String> getInstruments() {
        return this.instruments;
    }

    public void setInstruments(final List<String> instruments) {
        this.instruments = instruments;
    }

}
