package eu.cehj.cdb2.common.dto.cdb;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
//@JsonRootName("competentBodies")
public class CompetentBody implements Serializable {

    private static final long serialVersionUID = 2046129059818687586L;
    @JsonProperty
    private String id;
    @JsonProperty
    private String country;

    private List<CompetentBodyDetail> details;

    public String getId() {
        return this.id;
    }
    public void setId(final String id) {
        this.id = id;
    }
    public String getCountry() {
        return this.country;
    }
    public void setCountry(final String country) {
        this.country = country;
    }
    public List<CompetentBodyDetail> getDetails() {
        return this.details;
    }
    public void setDetails(final List<CompetentBodyDetail> details) {
        this.details = details;
    }
}
