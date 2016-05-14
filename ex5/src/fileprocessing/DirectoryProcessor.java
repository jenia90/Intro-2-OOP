package fileprocessing;

import java.io.*;
import java.util.Scanner;
import fileprocessing.Components.*;

/**
 * Created by jenia on 10/05/2016.
 */
public class DirectoryProcessor {
    private static final int SOURSCE_DIR_IDX = 0;
    private static final int COMMAND_FILE_IDX = 1;

    private final String sourcedir;
    private final String commandFile;

    public DirectoryProcessor(String sourceDir, String commandFile) throws NullPointerException {
        if (sourceDir == null || commandFile == null)
            throw new NullPointerException("Invalid path for source directory or command file.");

        this.sourcedir = sourceDir;
        this.commandFile = commandFile;
    }

    public static void main(String[] args){
        DirectoryProcessor dp = new DirectoryProcessor(args[SOURSCE_DIR_IDX], args[COMMAND_FILE_IDX]);
    }
}
