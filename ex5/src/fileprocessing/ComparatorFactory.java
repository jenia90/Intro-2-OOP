package fileprocessing;

import java.io.File;
import java.security.InvalidParameterException;
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

    public static Comparator<File> getComparator(List<String> orderingRules){
        Comparator<File> fileComparator;
        switch (orderingRules.get(ORDER_BY_IDX)){
            case "abs":
                fileComparator = (f1, f2) -> f1.getName().compareTo(f2.getName());
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

            default:
                throw new InvalidParameterException();
        }

        return orderingRules.get(REVERSE_KWD_IDX).equals(REVERSE) ? fileComparator.reversed() : fileComparator;
    }
}
