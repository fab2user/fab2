package eu.cehj.cdb2.hub.utils;

/**
 *  Represents JSON response from CDB when sending national data.
 */
public class GlobalCdbSyncResponse {

    private String importStatus;
    private String failureCode;
    private String failureDescription;
    private String[] failureDetails;

    public String getImportStatus() {
        return this.importStatus;
    }
    public void setImportStatus(final String importStatus) {
        this.importStatus = importStatus;
    }
    public String getFailureCode() {
        return this.failureCode;
    }
    public void setFailureCode(final String failureCode) {
        this.failureCode = failureCode;
    }
    public String getFailureDescription() {
        return this.failureDescription;
    }
    public void setFailureDescription(final String failureDescription) {
        this.failureDescription = failureDescription;
    }
    public String[] getFailureDetails() {
        return this.failureDetails;
    }
    public void setFailureDetails(final String[] failureDetails) {
        this.failureDetails = failureDetails;
    }
}
