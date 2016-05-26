package filesprocessing;

import java.io.File;
import java.util.*;
import java.util.function.*;

/**
 * Represents a section in command file.
 */
public class CommandSection {

    /**
     * Constants.
     */
    private static final String HASHTAG = "#";
    private static final int FILTER_RULE_LINE = 2, ORDER_RULE_LINE = 4;
    private static final String NOT = "NOT",  REVERSE = "REVERSE";
    public static final String ORDER = "ORDER";

    /**
     * Member fields.
     */
    private Predicate<File> fileFilter;
    private Comparator<File> fileComparator;
    private List<String> warnings;

    /**
     * Constructor.
     * @param filteringRules File filtering rules string.
     * @param orderingRules File ordering rules string.
     * @param index Line in which this section begins in the commandFile.
     * @throws TypeTwoErrorException
     */
    public CommandSection(String filteringRules, String orderingRules, int index)
            throws TypeTwoErrorException {
        // Here we split the rules string into a list of parameters and add empty string to eliminate
        // IndexOutOfBoundsException in case there's no REVERS\NOT keyword.

        warnings = new ArrayList<>();
        boolean negate = filteringRules.endsWith(NOT);
        boolean reverse = orderingRules.endsWith(REVERSE);
        List<String> filterRules = new ArrayList<>(Arrays.asList(filteringRules.split(HASHTAG)));

        /**
         * This try-catch block of code initializes the file filter for the current section.
         */
        try {
            fileFilter = FilterFactory.getFilter(filterRules, negate, index + FILTER_RULE_LINE);
        } catch (TypeOneErrorException e) {
            warnings.add(e.getMessage());
            fileFilter = file -> true;
        }

        /**
         * This block of code initializes the file comparator for the current section.
         */
        if (!orderingRules.equals(ORDER)){
            List<String> orderRules = new ArrayList<>(Arrays.asList(orderingRules.split(HASHTAG)));

            try {
                fileComparator = ComparatorFactory.getComparator(orderRules,reverse, index + ORDER_RULE_LINE);
            } catch (TypeOneErrorException e){
                warnings.add(e.getMessage());
                fileComparator = ComparatorFactory.getDefaultComparator();
            }
        } else fileComparator = ComparatorFactory.getDefaultComparator();


    }

    /**
     * Gets a list of warnings in this section.
     * @return list of warnings.
     */
    public List<String> getWarnings() {
        return warnings;
    }

    /**
     * Gets the filefilter Predicate object in this section
     * @return filefilter Predicate
     */
    public Predicate<File> getFileFilter(){
        return fileFilter;
    }

    /**
     * Gets the file Comparator object in this section.
     * @return file Comparator.
     */
    public Comparator<File> getFileComparator(){
        return fileComparator;
    }
}
