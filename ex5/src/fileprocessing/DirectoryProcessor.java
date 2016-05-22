package fileprocessing;

import java.io.*;
import java.nio.file.InvalidPathException;
import java.security.InvalidParameterException;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by jenia on 10/05/2016.
 */
public class DirectoryProcessor {

    private static final int SOURCE_DIR_IDX = 0, COMMAND_FILE_IDX = 1;
    private static final String FILTER_KWD = "FILTER", ORDER_KWD = "ORDER";
    private static final int FILTER_IDX = 0, FILTER_RULES_IDX = 1, ORDER_IDX = 2, ORDER_RULES_IDX = 3;

    private List<CommandSection> sections;

    public DirectoryProcessor(File commandFile) throws IOException {
        if (commandFile == null || commandFile.length() == 0)
            throw new FileNotFoundException("Invalid path or command file.");

        try {
            sections = createCommandSections(commandFile);
        } catch (IOException e){
            System.err.println(e.getMessage());
        }
    }

    private List<CommandSection> createCommandSections(File commandFile) throws IOException{
        List<CommandSection> sections = new ArrayList<>();

        List<String> lines = new ArrayList<>();
        try (Scanner scanner = new Scanner(commandFile)) {
            while (scanner.hasNext()) {
                lines.add(scanner.next());
            }
        }

        for (int i = 0; i < lines.size(); i+=4) {
            if (!lines.get(i + FILTER_IDX).equals(FILTER_KWD) || !lines.get(i + ORDER_IDX).equals(ORDER_KWD))
                System.err.println("ERROR: Wrong section structure.");
            else
                sections.add(new CommandSection(lines.get(i + FILTER_RULES_IDX), lines.get(i + ORDER_RULES_IDX)));
        }

        return sections;
    }

    private List<CommandSection> parseCommandFile(File commandFile) throws IOException {
        List<CommandSection> sections = new ArrayList<>();
        int fileKwdMod = 2;
        int orderKwdMod = 3;

        List<String> lines = new ArrayList<>();
        try (Scanner scanner = new Scanner(commandFile)) {
            while (scanner.hasNext()) {
                lines.add(scanner.next());
            }
        }

        return sections;
    }

    private void processDirectory(String path)throws InvalidPathException{
        File dir = new File(path);
        if (!dir.exists() || !dir.isDirectory())
            throw new InvalidPathException(dir.getAbsolutePath(), "Invalid path.");
        List<File> fileList = Arrays.asList(dir.listFiles(File::isFile));

        try {
            for (CommandSection section : sections) {
                fileList = Arrays.stream(dir.listFiles(file -> section.getFileFilter().test(file))).collect(Collectors.toList());
                fileList.sort(section.getFileComparator());
            }
        }catch (NullPointerException e){
            System.err.println(e.getMessage());
        } catch (InvalidParameterException e){
            System.err.println("Bad filter or order command definition.");
        }

        fileList.forEach(file -> System.out.println(file.getName()));
    }

    public static void main(String[] args){
        try{
            DirectoryProcessor dp = new DirectoryProcessor(new File(args[COMMAND_FILE_IDX]));
            dp.processDirectory(args[SOURCE_DIR_IDX]);
        } catch (IOException | InvalidPathException e){
            System.err.println(e.getMessage());
            System.exit(-1);
        }
    }
}
