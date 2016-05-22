package fileprocessing;

import java.io.File;
import java.util.*;
import java.util.function.*;

/**
 * Represents a section in command file.
 */
public class CommandSection {

    private static final String HASHTAG = "#";

    private final List<String> filterRules;
    private final List<String> orderingRules;

    public CommandSection(String filterRules, String orderingRules) {
        this.filterRules = new ArrayList<>(Arrays.asList(filterRules.split(HASHTAG)));
        this.filterRules.add("");
        this.orderingRules = new ArrayList<>(Arrays.asList(orderingRules.split(HASHTAG)));
        this.orderingRules.add("");
    }

    public Predicate<File> getFileFilter(){
        return FilterFactory.getFilter(filterRules);
    }

    public Comparator<File> getFileComparator(){
        return ComparatorFactory.getComparator(orderingRules);
    }
}
