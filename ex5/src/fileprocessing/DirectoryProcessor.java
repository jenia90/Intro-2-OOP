package fileprocessing;

import java.io.*;
import java.util.*;
import java.util.function.Predicate;
import java.util.stream.*;

/**
 * Created by jenia on 10/05/2016.
 */
public class DirectoryProcessor {
    private static final int SOURSCE_DIR_IDX = 0;
    private static final int COMMAND_FILE_IDX = 1;
    public static final int FILTER_TYPE_IDX = 0;
    public static final int KB_CONVERSION = 1024;


    private ArrayList<String[]> sections;

    public DirectoryProcessor(File commandFile) throws NullPointerException {
        if (commandFile == null)
            throw new NullPointerException("Invalid path for source directory or command file.");

        try {
            sections = getSections(commandFile);
        } catch (IOException e){
            System.err.println(e);
        }
    }

    private ArrayList<String[]> getSections(File commandFile) throws IOException{
        if (commandFile.isDirectory())
            throw new IOException("Invalid command file exception.");

        ArrayList<String[]> sections = new ArrayList<>();
        Reader reader = new FileReader(commandFile);
        BufferedReader bufferedReader = new BufferedReader(reader);
        try {

            Scanner s = new Scanner(commandFile);

            while(s.hasNext("FILTER")){
                System.out.println(s.next());
                String[] filterRules = s.next().split("#");
                s.next();
                String[] orderBy = s.next().split("#");
            }

        } catch (IOException ex){
            System.err.println("Unable to read command file.\n" + ex);
        } finally {
            reader.close();
            bufferedReader.close();
        }

        return sections;
    }

    private Predicate<File> getFileFilter(String[] filterBy){
        switch (filterBy[FILTER_TYPE_IDX]){
            case "greater_than":
                Predicate<File> greaterThan =
                        file -> file.isFile() && file.length() / KB_CONVERSION >= Integer.parseInt(filterBy[1]);

                return filterBy[2].equals("NOT") ? greaterThan.negate() : greaterThan;
            case "between":
                Predicate<File> betweenFilter =
                        file -> file.isFile() && (file.length() / KB_CONVERSION >= Integer.parseInt(filterBy[1]) &&
                        file.length() / KB_CONVERSION <= Integer.parseInt(filterBy[2]));

                return filterBy[3].equals("NOT") ? betweenFilter.negate() : betweenFilter;
            case "smaller_than":
                Predicate<File> smallerThan =
                        file -> file.isFile() && file.length() / KB_CONVERSION <= Integer.parseInt(filterBy[1]);

                return filterBy[2].equals("NOT") ? smallerThan.negate() : smallerThan;
            case "file":
                break;
            case "contains":
                break;
            case "prefix":
                break;
            case "suffix":
                break;
            case "writeable":
                break;
            case "executable":
                break;
            case "hidden":
                break;
        }

        return null;
    }

    private void processDirectory(String path){
        File file = new File(path);
        File[] filteredFiles = file.listFiles();

    }

    public static void main(String[] args){
        DirectoryProcessor dp = new DirectoryProcessor( new File(args[COMMAND_FILE_IDX]));
        dp.processDirectory(args[SOURSCE_DIR_IDX]);
    }
}
