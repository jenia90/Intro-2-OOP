package filesprocessing;

/**
 * Represents Type I Error.
 */
public class TypeOneErrorException extends Exception {

    private static final String WARNING_STRING = "Warning in line %d";

    /**
     * Constructor which initializes the message to be the passed to it line number and message.
     * @param line the line where an exception occurred.
     */
    public TypeOneErrorException(int line){
        super(String.format(WARNING_STRING, line));
    }
}
