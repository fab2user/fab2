package eu.cehj.cdb2.common.dto.cdb;

import java.io.Serializable;
import java.util.List;

public class CDBResponse implements Serializable{

    private static final long serialVersionUID = 5935674272077160227L;

    private List<CompetentBody> competentBodies;

    public List<CompetentBody> getCompetentBodies() {
        return this.competentBodies;
    }

    public void setCompetentBodies(final List<CompetentBody> competentBodies) {
        this.competentBodies = competentBodies;
    }

}
