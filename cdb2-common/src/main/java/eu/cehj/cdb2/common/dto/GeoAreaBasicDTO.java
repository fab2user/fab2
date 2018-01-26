package eu.cehj.cdb2.common.dto;

public class GeoAreaBasicDTO extends BaseDTO{

    private static final long serialVersionUID = -7928640245598300957L;

    private String name;

    public GeoAreaBasicDTO() {
        super();
    }

    public String getName() {
        return this.name;
    }

    public void setName(final String name) {
        this.name = name;
    }
}
