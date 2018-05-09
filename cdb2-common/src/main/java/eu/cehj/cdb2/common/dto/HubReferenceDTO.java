package eu.cehj.cdb2.common.dto;

import java.io.Serializable;
import java.util.List;

public class HubReferenceDTO implements Serializable {

    private static final long serialVersionUID = -553084743955286986L;

    private List<String> searchTypes;

    public List<String> getSearchTypes() {
        return this.searchTypes;
    }

    public void setSearchTypes(final List<String> searchTypes) {
        this.searchTypes = searchTypes;
    }

}
