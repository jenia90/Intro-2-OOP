package fileprocessing.Components.orders;

import java.io.File;

/**
 * Created by jenia on 14/05/2016.
 */
public class OrderBySize extends Order {

    public OrderBySize(boolean isReverse){
        super(isReverse);
    }
    /**
     * Orders a list of files in a specific order and returns the ordered list
     *
     * @param files list of files to order
     * @return ordered list of files
     */
    @Override
    public File[] getOrderedFiles(File[] files) {
        return new File[0];
    }
}
