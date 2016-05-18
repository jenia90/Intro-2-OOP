package fileprocessing;

import java.io.File;
import java.security.InvalidParameterException;
import java.util.function.Predicate;

/**
 * Created by jenia on 18/05/2016.
 */
public class CommandSection {

    private static final int FILTER_TYPE_IDX = 0;
    private static final int KB_CONVERSION = 1024;
    public static final String NOT = "NOT";

    private final String[] filterRules;
    private final String[] orderingRules;

    public CommandSection(String[] filterRules, String[] orderingRules) {
        this.filterRules = filterRules;
        this.orderingRules = orderingRules;
    }

    public Predicate<File> getFileFilter(){
        switch (filterRules[FILTER_TYPE_IDX]){
            case "greater_than":
                Predicate<File> greaterThanSizeFilter =
                        file -> file.isFile() && file.length() / KB_CONVERSION >= Double.parseDouble(filterRules[1]);

                return filterRules[2].equals(NOT) ? greaterThanSizeFilter.negate() : greaterThanSizeFilter;
            case "between":
                double lowerLimit = Double.parseDouble(filterRules[1]);
                double upperLimit = Double.parseDouble(filterRules[2]);

                if (upperLimit < lowerLimit)
                    throw new InvalidParameterException("Lower filter value should be smaller than upper value");

                Predicate<File> betweenSizeFilter =
                        file -> file.isFile() && (file.length() / KB_CONVERSION >= lowerLimit &&
                                file.length() / KB_CONVERSION <= upperLimit);

                return filterRules[3].equals(NOT) ? betweenSizeFilter.negate() : betweenSizeFilter;
            case "smaller_than":
                Predicate<File> smallerThanSizeFilter =
                        file -> file.isFile() && file.length() / KB_CONVERSION <= Double.parseDouble(filterRules[1]);

                return filterRules[2].equals(NOT) ? smallerThanSizeFilter.negate() : smallerThanSizeFilter;
            case "file":
                Predicate<File> exactNameFilter = file -> file.isFile() && file.getName().equals(filterRules[1]);

                return filterRules[2].equals(NOT) ? exactNameFilter.negate() : exactNameFilter;
            case "contains":
                Predicate<File> containsNameFilter = file -> file.isFile() && file.getName().contains(filterRules[1]);

                return filterRules[2].equals(NOT) ? containsNameFilter.negate() : containsNameFilter;
            case "prefix":
                Predicate<File> prefixNameFilter = file -> file.isFile() && file.getName().startsWith(filterRules[1]);

                return filterRules[2].equals(NOT) ? prefixNameFilter.negate() : prefixNameFilter;
            case "suffix":
                Predicate<File> suffixNameFilter = file -> file.isFile() && file.getName().endsWith(filterRules[1]);

                return filterRules[2].equals(NOT) ? suffixNameFilter.negate() : suffixNameFilter;
            case "writeable":
                Predicate<File> writeableAttrFilter = file -> file.isFile() && file.canWrite();

                return filterRules[1].equals(NOT) ? writeableAttrFilter.negate() : writeableAttrFilter;
            case "executable":
                Predicate<File> executableAttrFilter = file -> file.isFile() && file.canExecute();

                return filterRules[1].equals(NOT) ? executableAttrFilter.negate() : executableAttrFilter;
            case "hidden":
                Predicate<File> hiddenAttrFilter = file -> file.isFile() && file.isHidden();

                return filterRules[2].equals(NOT) ? hiddenAttrFilter.negate() : hiddenAttrFilter;
            case "all":
                return filterRules[1].equals(NOT) ? file -> false : file -> true;

            default:
                throw new InvalidParameterException("Wrong filter name command");
        }
    }
}
