package filesprocessing;

import java.io.File;
import java.security.InvalidParameterException;
import java.util.*;
import java.util.function.*;

/**
 * Represents a section in command file.
 */
public class CommandSection {

    private static final String HASHTAG = "#";

    private Predicate<File> fileFilter;
    private Comparator<File> fileComparator;

    public CommandSection(String filteringRules, String orderingRules, int index)
            throws TypeTwoErrorException {
        if (filteringRules.isEmpty())
            throw new TypeTwoErrorException("FILTER sub-section missing.");
        List<String> filterRules = new ArrayList<>(Arrays.asList(filteringRules.split(HASHTAG)));
        filterRules.add("");
        List<String> orderRules = new ArrayList<>(Arrays.asList(orderingRules.split(HASHTAG)));
        orderRules.add("");

        try {
            this.fileFilter = FilterFactory.getFilter(filterRules, index + 2);
        } catch (TypeOneErrorException e) {
            this.fileFilter = file -> true;
            throw new TypeTwoErrorException("FILTER");
        }

        this.fileComparator = ComparatorFactory.getComparator(orderRules, index + 4);

    }

    public Predicate<File> getFileFilter(){
        return fileFilter;
    }

    public Comparator<File> getFileComparator(){
        return fileComparator;
    }
}
