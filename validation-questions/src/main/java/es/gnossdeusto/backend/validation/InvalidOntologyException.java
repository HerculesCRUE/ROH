package es.gnossdeusto.backend.validation;

public class InvalidOntologyException extends Exception {
    
    /**
     *
     */
    private static final long serialVersionUID = 2312779613387361618L;

    public InvalidOntologyException(String errorMessage) {
        super(errorMessage);
    }
}
