package eu.cehj.cdb2.common.dto;

import java.util.ArrayList;
import java.util.List;

public class GeoAreaDTO extends BaseDTO{

    private static final long serialVersionUID = -6905423455133856283L;

    private String name;
    private List<MunicipalityDTO> municipalities = new ArrayList<MunicipalityDTO>();
    private String zipCodes = "";

    public GeoAreaDTO() {
        super();
    }

    public String getName() {
        return this.name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public List<MunicipalityDTO> getMunicipalities() {
        return this.municipalities;
    }

    public void setMunicipalities(final List<MunicipalityDTO> municipalities) {
        this.municipalities = municipalities;
    }

    public String getZipCodes() {
        return this.zipCodes;
    }

    public void setZipCodes(final String zipCodes) {
        this.zipCodes = zipCodes;
    }

    public void addZipCode(final String zipCode) {
        String punctuation = "";
        if (this.zipCodes != "") {
            punctuation = ", ";
        }
        this.zipCodes += punctuation + zipCode;
    }
}
