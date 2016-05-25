package filesprocessing;

import java.io.File;
import java.util.List;
import java.util.function.Predicate;

/**
 * Created by jenia on 19/05/2016.
 */
public class FilterFactory {

    private static final int KB_CONVERSION = 1024;
    private static final int FILTER_TYPE_IDX = 0, LOWER_LIMIT_IDX = 1, UPPER_LIMIT_IDX = 2, VALUE_IDX = 1;
    private static final String NOT = "NOT", YES = "YES", NO = "NO";

    public static Predicate<File> getFilter(List<String> filterRules, int index) throws TypeOneErrorException {
        Predicate<File> fileFilter = null;

        switch (filterRules.get(FILTER_TYPE_IDX)){
            case "greater_than":
                fileFilter =
                        file -> file.length() / KB_CONVERSION > Double.parseDouble(filterRules.get(VALUE_IDX));
                break;
            case "between":
                double lowerLimit = Double.parseDouble(filterRules.get(LOWER_LIMIT_IDX));
                double upperLimit = Double.parseDouble(filterRules.get(UPPER_LIMIT_IDX));

                if (upperLimit < lowerLimit)
                    throw new TypeOneErrorException(index);

                Predicate<File> betweenSizeFilter =
                        file -> (file.length() / KB_CONVERSION >= lowerLimit &&
                                file.length() / KB_CONVERSION <= upperLimit);

                return filterRules.get(3).equals(NOT) ? betweenSizeFilter.negate() : betweenSizeFilter;
            case "smaller_than":
                fileFilter =
                        file -> file.length() / KB_CONVERSION < Double.parseDouble(filterRules.get(VALUE_IDX));
                break;
            case "file":
                fileFilter = file -> file.getName().equals(filterRules.get(VALUE_IDX));
                break;
            case "contains":
                fileFilter = file -> file.getName().contains(filterRules.get(VALUE_IDX));
                break;
            case "prefix":
                fileFilter = file -> file.getName().startsWith(filterRules.get(VALUE_IDX));
                break;
            case "suffix":
                fileFilter = file -> file.getName().endsWith(filterRules.get(VALUE_IDX));
                break;
            case "writable":
                switch (filterRules.get(VALUE_IDX)){
                    case YES:
                        fileFilter = File::canWrite;
                        break;
                    case NO:
                        fileFilter = file -> !file.canWrite();
                        break;
                    default:
                        throw new TypeOneErrorException(index);

                }
                break;
            case "executable":
                switch (filterRules.get(VALUE_IDX)){
                    case YES:
                        fileFilter = File::canExecute;
                        break;
                    case NO:
                        fileFilter = file -> !file.canExecute();
                        break;
                    default:
                        throw new TypeOneErrorException(index);
                }
                break;
            case "hidden":
                switch (filterRules.get(VALUE_IDX)){
                    case YES:
                        fileFilter = File::isHidden;
                        break;
                    case NO:
                        fileFilter = file -> !file.isHidden();
                        break;
                    default:
                        throw new TypeOneErrorException(index);
                }
                break;
            case "all":
                return filterRules.get(1).equals(NOT) ? file -> false : file -> true;

            default:
                throw new TypeOneErrorException(index);
        }

        return filterRules.get(2).equals(NOT) ? fileFilter.negate() : fileFilter;
    }
}