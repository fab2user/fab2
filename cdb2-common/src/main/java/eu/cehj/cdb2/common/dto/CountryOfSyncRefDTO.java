package eu.cehj.cdb2.common.dto;

public class CountryOfSyncRefDTO extends BaseDTO{


    private static final long serialVersionUID = -3513822896041220242L;

    private String name;

    private String countryCode;

    public String getName() {
        return this.name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public String getCountryCode() {
        return this.countryCode;
    }

    public void setCountryCode(final String countryCode) {
        this.countryCode = countryCode;
    }

}
