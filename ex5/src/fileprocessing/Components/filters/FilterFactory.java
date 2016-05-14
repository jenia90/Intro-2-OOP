package fileprocessing.Components.filters;

import java.io.File;

/**
 * Created by jenia on 14/05/2016.
 */
public class FilterFactory {

    public static Filter getFilter(String filterType, String[] filterValues, boolean isNot){

        if (filterType == "greater_than" || filterType == "between" || filterType == "smaller_than")
            return new SizeFilter(filterType, filterValues, isNot);
        else if (filterType == "file" || filterType == "contains" || filterType == "prefix" || filterType == "suffix")
            return new NameFilter(filterType, filterValues, isNot);
        else if (filterType == "writeable" || filterType == "executable" || filterType == "hidden")
            return new AttributeFilter(filterType, filterValues, isNot);

        return null;
    }
}
