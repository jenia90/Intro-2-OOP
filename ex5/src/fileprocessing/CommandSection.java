package fileprocessing;

import java.io.File;
import java.security.InvalidParameterException;
import java.util.*;
import java.util.function.*;

/**
 * Represents a section in command file.
 */
public class CommandSection {

    private static final String HASHTAG = "#";

    private final Predicate<File> fileFilter;
    private final Comparator<File> fileComparator;

    public CommandSection(String filteringRules, String orderingRules) throws InvalidParameterException {
        List<String> filterRules = new ArrayList<>(Arrays.asList(filteringRules.split(HASHTAG)));
        filterRules.add("");
        List<String> orderRules = new ArrayList<>(Arrays.asList(orderingRules.split(HASHTAG)));
        orderRules.add("");

        this.fileFilter = FilterFactory.getFilter(filterRules);
        this.fileComparator = ComparatorFactory.getComparator(orderRules);
    }

    public Predicate<File> getFileFilter(){
        return fileFilter;
    }

    public Comparator<File> getFileComparator(){
        return fileComparator;
    }
}
