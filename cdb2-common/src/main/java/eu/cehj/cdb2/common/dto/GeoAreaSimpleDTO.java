package eu.cehj.cdb2.common.dto;

public class GeoAreaSimpleDTO extends BaseDTO{

    private static final long serialVersionUID = -6905423455133856283L;

    private String name;

    public GeoAreaSimpleDTO() {
        super();
    }

    public String getName() {
        return this.name;
    }

    public void setName(final String name) {
        this.name = name;
    }

}
