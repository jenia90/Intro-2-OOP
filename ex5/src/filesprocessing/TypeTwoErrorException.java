package filesprocessing;

/**
 * Represents Type II Error exception.
 */
public class TypeTwoErrorException extends Exception {

    private static final String ERROR_STRING = "ERROR: %s\n";

    /**
     * Constructor to create a custom message for this type of error.
     * @param message the error message to be added.
     */
    public TypeTwoErrorException(String message){
        super(String.format(ERROR_STRING, message));
    }
}
