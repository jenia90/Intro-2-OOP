package fileprocessing.Components.filters;

import java.io.File;

/**
 * Created by jenia on 14/05/2016.
 */
public abstract class Filter {

    private final String filerType;
    private final String[] filterValues;
    private final boolean isNot;

    public Filter(String filterType, String[] filterValues, boolean isNot){
        this.filerType = filterType;
        this.filterValues = filterValues;
        this.isNot = isNot;
    }

    public String getFilerType() {
        return filerType;
    }

    protected String[] getFilterValues(){
        return filterValues;
    }

    /**
     * Receives a list of files and returns a list of filtered files.
     * @param files the file list to filter
     * @return filtered file list.
     */
    public abstract File[] getFilteredFiles(File[] files);

}
