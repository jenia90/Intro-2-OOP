package fileprocessing.Components.filters;

import java.io.File;

/**
 * Created by jenia on 14/05/2016.
 */
public class NameFilter extends Filter {

    public NameFilter(String filterType, String[] filterValues, boolean isNot){
        super(filterType, filterValues, isNot);
    }
    /**
     * Receives a list of files and returns a list of filtered files.
     *
     * @param files the file list to filter
     * @return filtered file list.
     */
    @Override
    public File[] getFilteredFiles(File[] files) {
        return new File[0];
    }
}
