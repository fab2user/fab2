package eu.cehj.cdb2.business.exception;

public class CDBException extends Exception {

    private static final long serialVersionUID = -8656301821370523780L;

    public CDBException(final String message) {
        super(message);
    }

    public CDBException(final String message, final Exception e) {
        super(message, e);
    }

}
