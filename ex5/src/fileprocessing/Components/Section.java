package fileprocessing.Components;

import fileprocessing.Components.filters.*;
import fileprocessing.Components.orders.*;

/**
 * Created by jenia on 14/05/2016.
 */
public class Section {
    private Filter filter;
    private Order order;

    public Section(String filterType, String[] filterValues, boolean isNot,
                   String orderType, String orderBy, boolean isReverse){
        this.filter = FilterFactory.getFilter(filterType, filterValues, isNot);
    }

}
