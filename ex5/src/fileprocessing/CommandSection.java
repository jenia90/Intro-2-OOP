package fileprocessing;

import java.io.File;
import java.security.InvalidParameterException;
import java.util.Comparator;
import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;

/**
 * Created by jenia on 18/05/2016.
 */
public class CommandSection {

    private static final int FILTER_TYPE_IDX = 0;
    private static final int KB_CONVERSION = 1024;
    public static final String NOT = "NOT";

    private final List<String> filterRules;
    private final List<String> orderingRules;

    public CommandSection(List<String> filterRules, List<String> orderingRules) {
        this.filterRules = filterRules;
        this.orderingRules = orderingRules;
    }

    public Predicate<File> getFileFilter(){
        Predicate<File> fileFilter;

        switch (filterRules.get(FILTER_TYPE_IDX)){
            case "greater_than":
                fileFilter =
                        file -> file.isFile() && file.length() / KB_CONVERSION >= Double.parseDouble(filterRules.get(1));
                break;
            case "between":
                double lowerLimit = Double.parseDouble(filterRules.get(1));
                double upperLimit = Double.parseDouble(filterRules.get(2));

                if (upperLimit < lowerLimit)
                    throw new InvalidParameterException("Lower filter value should be smaller than upper value");

                Predicate<File> betweenSizeFilter =
                        file -> file.isFile() && (file.length() / KB_CONVERSION >= lowerLimit ||
                                file.length() / KB_CONVERSION <= upperLimit);

                return filterRules.get(3).equals(NOT) ? betweenSizeFilter.negate() : betweenSizeFilter;
            case "smaller_than":
                fileFilter =
                        file -> file.isFile() && file.length() / KB_CONVERSION <= Double.parseDouble(filterRules.get(1));
                break;
            case "file":
                fileFilter = file -> file.isFile() && file.getName().equals(filterRules.get(1));
                break;
            case "contains":
                fileFilter = file -> file.isFile() && file.getName().contains(filterRules.get(1));
                break;
            case "prefix":
                fileFilter = file -> file.isFile() && file.getName().startsWith(filterRules.get(1));
                break;
            case "suffix":
                fileFilter = file -> file.isFile() && file.getName().endsWith(filterRules.get(1));
                break;
            case "writeable":
                fileFilter = file -> file.isFile() &&
                        (filterRules.get(1).equals("YES") && file.canWrite()) || (filterRules.get(1).equals("NO") && !file.canWrite());
                break;
            case "executable":
                fileFilter = file -> file.isFile() &&
                        (filterRules.get(1).equals("YES") && file.canExecute()) || (filterRules.get(1).equals("NO") && !file.canExecute());
                break;
            case "hidden":
                fileFilter = file -> file.isFile() &&
                        (filterRules.get(1).equals("YES") && file.isHidden()) || (filterRules.get(1).equals("NO") && !file.isHidden());
                break;
            case "all":
                return filterRules.get(1).equals(NOT) ? file -> false : file -> true;

            default:
                throw new InvalidParameterException("Wrong filter name command");
        }

        return filterRules.get(1).equals(NOT) ? fileFilter.negate() : fileFilter;
    }

    public Comparator<File> getFileComparator(){
        Comparator<File> fileComparator;
        switch (orderingRules.get(0)){
            case "abs":
                fileComparator = (f1, f2) -> f1.getName().compareTo(f2.getName());
                break;
            case "type":
                fileComparator = (f1, f2) -> {
                    String f1Name = f1.getName();
                    String f2Name = f2.getName();

                    return f1Name.substring(f1Name.lastIndexOf(".")).compareTo(f2Name.substring(f2Name.lastIndexOf(".")));
                };
                break;
            case "size":
                fileComparator = (f1, f2) -> Long.compare(f1.length(), f2.length());
                break;

            default:
                throw new InvalidParameterException("Wrong order name command.");
        }

        return orderingRules.get(1).equals("REVERSE") ? fileComparator.reversed() : fileComparator;
    }
}
