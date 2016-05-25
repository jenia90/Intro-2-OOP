package filesprocessing;

import java.io.File;
import java.util.List;
import java.util.function.Predicate;

/**
 * Represents an object which uses factory pattern to create Predicate based on the parameters passed to it.
 */
public class FilterFactory {

    private static final int KB_CONVERSION = 1024;
    private static final int FILTER_TYPE_IDX = 0, LOWER_LIMIT_IDX = 1, UPPER_LIMIT_IDX = 2, VALUE_IDX = 1,NOT_IDX = 2;
    private static final String NOT = "NOT", YES = "YES", NO = "NO";
    private static final int BETWEEN_FILTER_NOT_IDX = 3;
    public static final String GREATER_THAN = "greater_than", BETWEEN = "between", SMALLER_THAN = "smaller_than";
    public static final String FILE = "file", CONTAINS = "contains", PREFIX = "prefix", SUFFIX = "suffix";
    public static final String WRITABLE = "writable", EXECUTABLE = "executable", HIDDEN = "hidden";
    public static final String ALL = "all";

    /**
     * Returns a Predicate object based on ordering rules passed to it.
     * In case wrong parameters passed, an exception about the corresponding line would be thrown.
     * @param filterRules filtering rules and parameters
     * @param line line index of the rule in the commandFile
     * @return Predicate object
     * @throws TypeOneErrorException If filtering rule has a typo or uses undefined parameter or name.
     */
    public static Predicate<File> getFilter(List<String> filterRules, int line) throws TypeOneErrorException {
        Predicate<File> fileFilter;

        // This block of code deals with the different parameters for to create the desired file filter Predicate.
        switch (filterRules.get(FILTER_TYPE_IDX)){
            case GREATER_THAN:
                fileFilter =
                        file -> file.length() / KB_CONVERSION > Double.parseDouble(filterRules.get(VALUE_IDX));
                break;
            case BETWEEN:
                double lowerLimit = Double.parseDouble(filterRules.get(LOWER_LIMIT_IDX));
                double upperLimit = Double.parseDouble(filterRules.get(UPPER_LIMIT_IDX));

                if (upperLimit < lowerLimit)
                    throw new TypeOneErrorException(line);

                Predicate<File> betweenSizeFilter =
                        file -> file.length() / KB_CONVERSION >= lowerLimit &&
                                file.length() / KB_CONVERSION <= upperLimit;

                return filterRules.get(BETWEEN_FILTER_NOT_IDX).equals(NOT) ? betweenSizeFilter.negate() : betweenSizeFilter;
            case SMALLER_THAN:
                fileFilter =
                        file -> file.length() / KB_CONVERSION < Double.parseDouble(filterRules.get(VALUE_IDX));
                break;
            case FILE:
                fileFilter = file -> file.getName().equals(filterRules.get(VALUE_IDX));
                break;
            case CONTAINS:
                fileFilter = file -> file.getName().contains(filterRules.get(VALUE_IDX));
                break;
            case PREFIX:
                fileFilter = file -> file.getName().startsWith(filterRules.get(VALUE_IDX));
                break;
            case SUFFIX:
                fileFilter = file -> file.getName().endsWith(filterRules.get(VALUE_IDX));
                break;
            case WRITABLE:
                switch (filterRules.get(VALUE_IDX)){
                    case YES:
                        fileFilter = File::canWrite;
                        break;
                    case NO:
                        fileFilter = file -> !file.canWrite();
                        break;
                    default:
                        throw new TypeOneErrorException(line);

                }
                break;
            case EXECUTABLE:
                switch (filterRules.get(VALUE_IDX)){
                    case YES:
                        fileFilter = File::canExecute;
                        break;
                    case NO:
                        fileFilter = file -> !file.canExecute();
                        break;
                    default:
                        throw new TypeOneErrorException(line);
                }
                break;
            case HIDDEN:
                switch (filterRules.get(VALUE_IDX)){
                    case YES:
                        fileFilter = File::isHidden;
                        break;
                    case NO:
                        fileFilter = file -> !file.isHidden();
                        break;
                    default:
                        throw new TypeOneErrorException(line);
                }
                break;
            case ALL:
                return filterRules.get(VALUE_IDX).equals(NOT) ? file -> false : file -> true;

            default:
                throw new TypeOneErrorException(line);
        }

        return filterRules.get(NOT_IDX).equals(NOT) ? fileFilter.negate() : fileFilter;
    }
}