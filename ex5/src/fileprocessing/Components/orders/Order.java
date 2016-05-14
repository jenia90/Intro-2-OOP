package fileprocessing.Components.orders;

import java.io.File;

/**
 * Created by jenia on 14/05/2016.
 */
public abstract class Order {

    /**
     * Orders a list of files in a specific order and returns the ordered list
     * @param files list of files to order
     * @return ordered list of files
     */
    public abstract File[] getOrderedFiles(File[] files);
}
