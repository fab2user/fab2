package eu.cehj.cdb2.business.service.data;

import eu.cehj.cdb2.entity.AdminAreaSubdivisionMajor;
import eu.cehj.cdb2.entity.AdminAreaSubdivisionMiddle;
import eu.cehj.cdb2.entity.AdminAreaSubdivisionMinor;
import eu.cehj.cdb2.entity.Municipality;

/**
 * Helps build a record by keeping track of all entities related to current record
 */
public class RecordBuilderHelper {

    private AdminAreaSubdivisionMajor majorArea;

    private AdminAreaSubdivisionMiddle middleArea;

    private AdminAreaSubdivisionMinor minorArea;

    private Municipality municipality;

    public AdminAreaSubdivisionMajor getMajorArea() {
        return this.majorArea;
    }

    public void setMajorArea(final AdminAreaSubdivisionMajor majorArea) {
        this.majorArea = majorArea;
    }

    public AdminAreaSubdivisionMiddle getMiddleArea() {
        return this.middleArea;
    }

    public void setMiddleArea(final AdminAreaSubdivisionMiddle middleArea) {
        this.middleArea = middleArea;
    }

    public AdminAreaSubdivisionMinor getMinorArea() {
        return this.minorArea;
    }

    public void setMinorArea(final AdminAreaSubdivisionMinor minorArea) {
        this.minorArea = minorArea;
    }

    public Municipality getMunicipality() {
        return this.municipality;
    }

    public void setMunicipality(final Municipality municipality) {
        this.municipality = municipality;
    }
}
