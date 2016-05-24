package filesprocessing;

/**
 * Created by jenia on 24/05/2016.
 */
public class TypeTwoErrorException extends Exception {
    public TypeTwoErrorException(String message){
        super("ERROR: " + message + "\n");
    }
}
