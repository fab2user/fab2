package eu.cehj.cdb2.common.exception.dto;

public class CDBException extends RuntimeException {

    private static final long serialVersionUID = -8656301821370523780L;

    private String messageCode;

    // FIXME: Find a way to identify a message code
    public CDBException(final String message) {
        super(message);
    }

    public CDBException(final String message, final Exception e) {
        super(message, e);
    }

    public String getMessageCode() {
        return this.messageCode;
    }

    public void setMessageCode(final String messageCode) {
        this.messageCode = messageCode;
    }

}
