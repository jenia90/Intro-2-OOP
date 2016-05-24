package filesprocessing;

import java.io.File;
import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

/**
 * Created by jenia on 19/05/2016.
 */
public class ComparatorFactory {

    private static final int ORDER_BY_IDX = 0;
    private static final String REVERSE = "REVERSE";
    private static final String FILE_TYPE_DLM = ".";
    private static final int REVERSE_KWD_IDX = 1;
    public static final String ORDER = "ORDER";

    public static Comparator<File> getComparator(List<String> orderingRules, int index) throws TypeOneErrorException {
        Comparator<File> fileComparator;
        switch (orderingRules.get(ORDER_BY_IDX)){
            case "abs":
                fileComparator = getNameComparator();
                break;
            case "type":
                fileComparator = (f1, f2) -> {
                    String f1Name = f1.getName();
                    String f2Name = f2.getName();

                    return f1Name.substring(f1Name.lastIndexOf(FILE_TYPE_DLM))
                            .compareTo(f2Name.substring(f2Name.lastIndexOf(FILE_TYPE_DLM)));
                };
                break;
            case "size":
                fileComparator = (f1, f2) -> Long.compare(f1.length(), f2.length());
                break;
            case "ORDER":
                return getNameComparator();

            default:
                throw new TypeOneErrorException(index);
        }

        return orderingRules.get(REVERSE_KWD_IDX).equals(REVERSE) ?
                fileComparator.thenComparing(getNameComparator()).reversed() :
                fileComparator.thenComparing(getNameComparator());
    }

    public static Comparator<File> getNameComparator(){
        return (f1, f2) -> f1.getAbsolutePath().compareTo(f2.getAbsolutePath());
    }
}
