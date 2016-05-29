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
    private static final String ORDER = "ORDER";

    /**
     * Member fields.
     */
    private final Predicate<File> fileFilter;
    private final Comparator<File> fileComparator;
    private List<String> warnings;

    /**
     * Constructor.
     * @param filteringRules File filtering rules string.
     * @param orderingRules File ordering rules string.
     * @param line Line in which this section begins in the commandFile.
     * @throws TypeTwoErrorException
     */
    public CommandSection(String filteringRules, String orderingRules, int line)
            throws TypeTwoErrorException {
        warnings = new ArrayList<>();
        boolean negate = filteringRules.endsWith(NOT);
        boolean reverse = orderingRules.endsWith(REVERSE);

        fileFilter = generateFilter(filteringRules, negate, line);
        fileComparator = generateComparator(orderingRules, reverse, line);
    }

    /**
     * Returns a Comparator for sorting list of files.
     * @param orderingRules rules for the Comparator creation.
     * @param reverse should the Comparator be negated.
     * @param line line index in the command file for the case when an exception is thrown.
     */
    private Comparator<File> generateComparator(String orderingRules, boolean reverse, int line) {
        // This block of code initializes the file comparator for the current section.
        if (orderingRules.equals(ORDER))
            return ComparatorFactory.getDefaultComparator();

        List<String> orderRules = new ArrayList<>(Arrays.asList(orderingRules.split(HASHTAG)));

        try {
            return ComparatorFactory.getComparator(orderRules,reverse, line + ORDER_RULE_LINE);
        } catch (TypeOneErrorException e){
            warnings.add(e.getMessage());
            return ComparatorFactory.getDefaultComparator();
        }
    }

    /**
     * Returns a predicate for filtering list of files.
     * @param filteringRules rules for the Predicate creation.
     * @param negate should the Predicate be negated.
     * @param line line index in the command file for the case when an exception is thrown.
     */
    private Predicate<File> generateFilter(String filteringRules, boolean negate, int line) {
        List<String> filterRules = new ArrayList<>(Arrays.asList(filteringRules.split(HASHTAG)));

        // This try-catch block of code initializes the file filter for the current section.
        try {
            return FilterFactory.getFilter(filterRules, negate, line + FILTER_RULE_LINE);
        } catch (TypeOneErrorException e) {
            warnings.add(e.getMessage());
            return file -> true;
        }
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
