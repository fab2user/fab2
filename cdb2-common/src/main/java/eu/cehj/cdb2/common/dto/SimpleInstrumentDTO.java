package eu.cehj.cdb2.common.dto;

import eu.cehj.cdb2.entity.Instrument;

public class SimpleInstrumentDTO extends BaseDTO{

    private static final long serialVersionUID = 4393148391489294469L;

    private String code;
    private String description;

    public SimpleInstrumentDTO() {
        super();
    }

    public SimpleInstrumentDTO(final Instrument instrument ) {
        super(instrument);
    }

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

}
