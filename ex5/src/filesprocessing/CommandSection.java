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
    private List<String> warnings;

    public CommandSection(String filteringRules, String orderingRules, int index)
            throws TypeTwoErrorException {

        if (filteringRules.isEmpty())
            throw new TypeTwoErrorException("FILTER sub-section missing.");
        List<String> filterRules = new ArrayList<>(Arrays.asList(filteringRules.split(HASHTAG)));
        filterRules.add("");
        List<String> orderRules = new ArrayList<>(Arrays.asList(orderingRules.split(HASHTAG)));
        orderRules.add("");

        this.warnings = new ArrayList<>();

        try {
            this.fileFilter = FilterFactory.getFilter(filterRules, index + 2);
        } catch (TypeOneErrorException e) {
            this.fileFilter = file -> true;
            warnings.add(e.getMessage());
        }

        try {
            this.fileComparator = ComparatorFactory.getComparator(orderRules, index + 4);
        } catch (TypeOneErrorException e){
            warnings.add(e.getMessage());
            this.fileComparator = ComparatorFactory.getNameComparator();
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
