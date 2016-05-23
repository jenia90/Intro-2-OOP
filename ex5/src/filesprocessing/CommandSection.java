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

    public CommandSection()  throws TypeTwoErrorException, TypeOneErrorException {
        this("", "");
    }

    public CommandSection(String filteringRules, String orderingRules)
            throws TypeTwoErrorException, TypeOneErrorException {
        if (filteringRules.isEmpty())
            throw new TypeTwoErrorException("FILTER sub-section missing.");
        List<String> filterRules = new ArrayList<>(Arrays.asList(filteringRules.split(HASHTAG)));
        filterRules.add("");
        List<String> orderRules = new ArrayList<>(Arrays.asList(orderingRules.split(HASHTAG)));
        orderRules.add("");

        try{
        this.fileFilter = FilterFactory.getFilter(filterRules);
        } catch (TypeOneErrorException e){
            this.fileFilter = file -> true;
            throw new TypeTwoErrorException("FILTER");
        }
        try {
            this.fileComparator = ComparatorFactory.getComparator(orderRules);
        } catch (TypeOneErrorException e){
            this.fileComparator = (f1, f2) -> f1.getName().compareTo(f2.getName());
            throw new TypeOneErrorException("ORDER");
        }
    }

    public void addWarning(String warning){
        warnings.add(warning);
    }

    public void printWarnings() {
        warnings.forEach(System.err::println);
    }

    public Predicate<File> getFileFilter(){
        return fileFilter;
    }

    public Comparator<File> getFileComparator(){
        return fileComparator;
    }
}
