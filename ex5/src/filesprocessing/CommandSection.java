package filesprocessing;

import java.io.File;
import java.util.*;
import java.util.function.*;

/**
 * Represents a section in command file.
 */
public class CommandSection {

    private static final String HASHTAG = "#";
    public static final int FILTER_RULE_LINE = 2;

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
        List<String> filterRules = new ArrayList<>(Arrays.asList(filteringRules.split(HASHTAG)));
        filterRules.add("");
        List<String> orderRules = new ArrayList<>(Arrays.asList(orderingRules.split(HASHTAG)));
        orderRules.add("");

        warnings = new ArrayList<>();

        /**
         * These next 2 try-catch blocks are intended to handle all Type I errors and adding them
         * to the warnings collection to be printed before each section processed files.
         */
        try {
            fileFilter = FilterFactory.getFilter(filterRules, index + FILTER_RULE_LINE); //TODO ?
        } catch (TypeOneErrorException e) {
            fileFilter = file -> true;
            warnings.add(e.getMessage());
        }

        try {
            this.fileComparator = ComparatorFactory.getComparator(orderRules, index + 4);
        } catch (TypeOneErrorException e){
            warnings.add(e.getMessage());
            this.fileComparator = ComparatorFactory.getDefaultComparator();
        }

    }

    public List<String> getWarnings() {
        return warnings;
    }

    public Predicate<File> getFileFilter(){
        return fileFilter;
    }

    public Comparator<File> getFileComparator(){
        return fileComparator;
    }
}
