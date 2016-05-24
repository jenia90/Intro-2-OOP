package filesprocessing;

/**
 * Created by jenia on 24/05/2016.
 */
public class TypeOneErrorException extends Exception {
    public TypeOneErrorException(int line){
        super("Warning in line " + line);
    }
}
