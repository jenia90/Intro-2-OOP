package fileprocessing;

import java.io.*;
import java.util.*;
import java.util.function.*;
import java.util.stream.Collectors;

/**
 * Created by jenia on 10/05/2016.
 */
public class DirectoryProcessor {
    private static final int SOURCE_DIR_IDX = 0;
    private static final int COMMAND_FILE_IDX = 1;
    public static final String FILTER_KWD = "FILTER";
    public static final String HASHTAG = "#";

    /*
    * TODO:
    * The idea i'm having now is to create a Section class which will hold the filterRules and orderRules
    * and a method called filter(File path) and order(String[] path).
    * Also I should transfer the getFilter method to the Section class.
    *
    * This class will hold a list of sections of type List<Section>.
    *
    * */


    private List<CommandSection> sections;

    public DirectoryProcessor(File commandFile) throws NullPointerException {
        if (commandFile == null)
            throw new NullPointerException("Invalid path for source directory or command file.");

        try {
            sections = parseCommandFile(commandFile);
        } catch (IOException e){
            System.err.println(e);
        }
    }

    private List<CommandSection> parseCommandFile(File commandFile) throws IOException{
        if (commandFile.isDirectory())
            throw new IOException("Invalid command file exception.");

        List<CommandSection> sections = new ArrayList<>();

        try {
            Scanner s = new Scanner(commandFile);

            while(s.hasNext(FILTER_KWD)){
                s.next();
                List<String> filterRules = new ArrayList<>(Arrays.asList(s.next().split(HASHTAG)));
                filterRules.add("");
                s.next();
                List<String> orderingRules =new ArrayList<>(Arrays.asList(s.next().split(HASHTAG)));
                orderingRules.add("");

                sections.add(new CommandSection(filterRules, orderingRules));
            }

            s.close();
        } catch (IOException ex){
            System.err.println("Unable to read command file.\n" + ex);
        }

        return sections;
    }

    private void processDirectory(String path){
        File dir = new File(path);
        for (CommandSection section : sections) {
            List<File> fileList = Arrays.stream(dir.listFiles()).filter(section.getFileFilter()).collect(Collectors.toList());
            fileList.sort(section.getFileComparator());
            fileList.forEach(f -> System.out.println(f.getName()));
        }

    }

    public static void main(String[] args){
        DirectoryProcessor dp = new DirectoryProcessor( new File(args[COMMAND_FILE_IDX]));
        dp.processDirectory(args[SOURCE_DIR_IDX]);
    }
}
