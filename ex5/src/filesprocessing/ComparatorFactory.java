package filesprocessing;

import java.io.File;
import java.util.Comparator;
import java.util.List;

/**
 * Represents an object which uses factory pattern to create Comparators based on the parameters passed to it.
 */
public class ComparatorFactory {

    /**
     * Constants.
     */
    private static final int ORDER_BY_IDX = 0;
    private static final String FILE_TYPE_DLM = ".";
    private static final String ABS = "abs", TYPE = "type", SIZE = "size";

    /**
     * Creates a comparator object based on ordering rules passed to it.
     * In case wrong parameters passed, an exception about the corresponding line would be thrown.
     * @param orderingRules Ordering command
     * @param reverse true if comparator should be reversed; false otherwise.
     *@param index line index in the command line.  @return Returns a Comparator<> object.
     * @throws TypeOneErrorException if wrong parameters\rules were passed.
     */
    public static Comparator<File> getComparator(List<String> orderingRules, boolean reverse, int index)
            throws TypeOneErrorException {
        Comparator<File> fileComparator;

        switch (orderingRules.get(ORDER_BY_IDX)){
            case ABS:
                fileComparator = getDefaultComparator();
                break;
            case TYPE:
                fileComparator = (f1, f2) -> {
                    String f1Name = f1.getName();
                    String f2Name = f2.getName();

                    return f1Name.substring(f1Name.lastIndexOf(FILE_TYPE_DLM))
                            .compareTo(f2Name.substring(f2Name.lastIndexOf(FILE_TYPE_DLM)));
                };
                break;
            case SIZE:
                fileComparator = (f1, f2) -> Long.compare(f1.length(), f2.length());
                break;

            default:
                throw new TypeOneErrorException(index);
        }

        // Here we return the chosen Comparator with the only exception - when two file are of the same type or size
        // we also compare their names. Also we check if the sorting needs to be reversed.
        return reverse ? fileComparator.thenComparing(getDefaultComparator()).reversed() :
                fileComparator.thenComparing(getDefaultComparator());
    }

    /**
     * Returns the filename Comparator.
     * @return filename Comparator.
     */
    public static Comparator<File> getDefaultComparator(){
        return (f1, f2) -> f1.getAbsolutePath().compareTo(f2.getAbsolutePath());
    }
}
