package fileprocessing;

import java.io.File;
import java.security.InvalidParameterException;
import java.util.List;
import java.util.function.Predicate;

/**
 * Created by jenia on 19/05/2016.
 */
public class FilterFactory {

    private static final int FILTER_TYPE_IDX = 0;
    private static final int KB_CONVERSION = 1024;
    private static final int LOWER_LIMIT_IDX = 1, UPPER_LIMIT_IDX = 2;
    private static final int VALUE_IDX = 1;
    public static final String NOT = "NOT";

    public static Predicate<File> getFilter(List<String> filterRules) {
        Predicate<File> fileFilter;
        int lastIndex = filterRules.size() - 1;

        switch (filterRules.get(FILTER_TYPE_IDX)){
            case "greater_than":
                fileFilter =
                        file -> file.isFile() && file.length() / KB_CONVERSION >= Double.parseDouble(filterRules.get(1));
                break;
            case "between":
                double lowerLimit = Double.parseDouble(filterRules.get(LOWER_LIMIT_IDX));
                double upperLimit = Double.parseDouble(filterRules.get(UPPER_LIMIT_IDX));

                if (upperLimit < lowerLimit)
                    throw new InvalidParameterException("Lower filter value should be smaller than upper value");

                Predicate<File> betweenSizeFilter =
                        file -> file.isFile() && (file.length() / KB_CONVERSION >= lowerLimit ||
                                file.length() / KB_CONVERSION <= upperLimit);

                return filterRules.get(lastIndex).equals(NOT) ? betweenSizeFilter.negate() : betweenSizeFilter;
            case "smaller_than":
                fileFilter =
                        file -> file.isFile() && file.length() / KB_CONVERSION <= Double.parseDouble(filterRules.get(VALUE_IDX));
                break;
            case "file":
                fileFilter = file -> file.isFile() && file.getName().equals(filterRules.get(VALUE_IDX));
                break;
            case "contains":
                fileFilter = file -> file.isFile() && file.getName().contains(filterRules.get(VALUE_IDX));
                break;
            case "prefix":
                fileFilter = file -> file.isFile() && file.getName().startsWith(filterRules.get(VALUE_IDX));
                break;
            case "suffix":
                fileFilter = file -> file.isFile() && file.getName().endsWith(filterRules.get(VALUE_IDX));
                break;
            case "writeable":
                fileFilter = file -> file.isFile() &&
                        (filterRules.get(VALUE_IDX).equals("YES") && file.canWrite()) ||
                        (filterRules.get(VALUE_IDX).equals("NO") && !file.canWrite());
                break;
            case "executable":
                fileFilter = file -> file.isFile() &&
                        (filterRules.get(VALUE_IDX).equals("YES") && file.canExecute()) ||
                        (filterRules.get(VALUE_IDX).equals("NO") && !file.canExecute());
                break;
            case "hidden":
                fileFilter = file -> file.isFile() &&
                        (filterRules.get(VALUE_IDX).equals("YES") && file.isHidden()) ||
                        (filterRules.get(VALUE_IDX).equals("NO") && !file.isHidden());
                break;
            case "all":
                return filterRules.get(lastIndex).equals(NOT) ? file -> false : file -> true;

            default:
                throw new InvalidParameterException("Wrong filter type command");
        }

        return filterRules.get(lastIndex).equals(NOT) ? fileFilter.negate() : fileFilter;
    }
}