package fileprocessing;

import java.io.File;
import java.security.InvalidParameterException;
import java.util.*;
import java.util.function.*;

/**
 * Created by jenia on 18/05/2016.
 */
public class CommandSection {

    private final List<String> filterRules;
    private final List<String> orderingRules;

    public CommandSection(List<String> filterRules, List<String> orderingRules) {
        this.filterRules = filterRules;
        this.orderingRules = orderingRules;
    }

    public Predicate<File> getFileFilter(){
        return FileFactory.getFilter(filterRules);
    }

    public Comparator<File> getFileComparator(){
        return ComparatorFactory.getComparator(orderingRules);
    }
}
