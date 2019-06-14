package eu.cehj.cdb2.hub.service;

import java.io.Serializable;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class LanguageService implements Serializable{

    private static final long serialVersionUID = -8930807992803160052L;

    @Value("${cdb.languages}")
    private List<String> languages;

    public List<String> getLanguages() {
        return this.languages;
    }

    public void setLanguages(final List<String> languages) {
        this.languages = languages;
    }



}
