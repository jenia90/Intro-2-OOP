package fileprocessing;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Stream;

import fileprocessing.Components.*;

/**
 * Created by jenia on 10/05/2016.
 */
public class DirectoryProcessor {
    private static final int SOURSCE_DIR_IDX = 0;
    private static final int COMMAND_FILE_IDX = 1;


    private ArrayList<Section> sections;

    public DirectoryProcessor(File commandFile) throws NullPointerException {
        if (commandFile == null)
            throw new NullPointerException("Invalid path for source directory or command file.");

        try {
            sections = getSections(commandFile);
        } catch (IOException e){
            System.err.println(e);
        }
    }

    private ArrayList<Section> getSections(File commandFile) throws IOException{
        if (commandFile.isDirectory())
            throw new IOException("Invalid command file exception.");

        ArrayList<Section> sections = new ArrayList<>();
        try {
            Reader reader = new FileReader(commandFile);
            BufferedReader bufferedReader = new BufferedReader(reader);

            Scanner s = new Scanner(commandFile);

            while(s.hasNext("FILTER")){
                System.out.println(s.next());
                String[] filterRules = s.next().split("#");
                s.next();
                String[] orderBy = s.next().split("#");
                sections.add(new Section(filterRules, orderBy));
            }

            reader.close();
            bufferedReader.close();
        } catch (IOException ex){
            System.err.println("Unable to read command file.\n" + ex);
        }

        return sections;
    }

    private void processDirectory(String path){

    }

    public static void main(String[] args){
        DirectoryProcessor dp = new DirectoryProcessor( new File(args[COMMAND_FILE_IDX]));
        dp.processDirectory(args[SOURSCE_DIR_IDX]);
    }
}
