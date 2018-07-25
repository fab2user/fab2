package eu.cehj.cdb2.common.dto;

public class BatchDataUpdateDTO extends BaseDTO{


    private static final long serialVersionUID = 4598903693563574115L;

    private String field;

    private String value;

    private long countryOfSyncId;

    public String getField() {
        return this.field;
    }

    public void setField(final String field) {
        this.field = field;
    }

    public String getValue() {
        return this.value;
    }

    public void setValue(final String value) {
        this.value = value;
    }

    public long getCountryOfSyncId() {
        return this.countryOfSyncId;
    }

    public void setCountryOfSyncId(final long countryOfSyncId) {
        this.countryOfSyncId = countryOfSyncId;
    }

}
