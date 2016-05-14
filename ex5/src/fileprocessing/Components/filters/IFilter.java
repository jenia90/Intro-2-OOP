package fileprocessing.Components.filters;

import java.io.File;
import java.nio.file.DirectoryStream;

/**
 * Created by jenia on 14/05/2016.
 */
interface IFilter {

    public File[] getFilteredFiles(File[] files);

}
