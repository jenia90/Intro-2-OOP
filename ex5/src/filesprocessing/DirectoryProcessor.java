package filesprocessing;

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
    private static final String FILTER = "FILTER", ORDER = "ORDER";
    private static final int FILTER_RULES_IDX = 1, ORDER_RULES_IDX = 3;

    private List<CommandSection> sections;

    public DirectoryProcessor(File commandFile) throws IOException {
        if (commandFile == null || commandFile.length() == 0)
            throw new FileNotFoundException("Invalid path or command file.");

        try {
            sections = getCommandSections(commandFile);
        } catch (IOException e){
            System.err.println(e.getMessage());
        }
    }

    private List<CommandSection> getCommandSections(File commandFile) throws IOException, InvalidParameterException {
        List<CommandSection> sections = new ArrayList<>();

        List<String> lines = new ArrayList<>();
        try (Scanner scanner = new Scanner(commandFile)) {
            while (scanner.hasNext()) {
                lines.add(scanner.next());
            }
        }

        int index = 0;
        while (index + 2 <= lines.size()){
            if (!lines.get(index).equals(FILTER))
                throw new InvalidParameterException("FILTER sub-section missing.");
            else if (!lines.get(index + 2).equals(ORDER))
                throw new InvalidParameterException("ORDER sub-section missing.");

            try {
                sections.add(new CommandSection(lines.get(index + FILTER_RULES_IDX), lines.get(index + ORDER_RULES_IDX)));
            } catch (InvalidParameterException e){
                System.err.println("Warning in line: " + (index + 1));
            }

            if (lines.get(index + ORDER_RULES_IDX).equals(FILTER)){
                index += 3;
                continue;
            }

            index += 4;
        }

        return sections;
    }

    private void processDirectory(String path)throws InvalidPathException{
        File dir = new File(path);
        if (!dir.exists() || !dir.isDirectory())
            throw new InvalidPathException(dir.getAbsolutePath(), "Invalid path.");

        for (CommandSection section : sections) {
            List<File> fileList = Arrays.stream(dir.listFiles(file -> file.isFile() && section.getFileFilter().test(file))).collect(Collectors.toList());
            fileList.sort(section.getFileComparator());
            fileList.forEach(file -> System.out.println(file.getName()));
        }

    }

    public static void main(String[] args){
        try{
            if (args.length != 2)
                throw new IOException("Please check path of source dir and command file are correct.");
            DirectoryProcessor dp = new DirectoryProcessor(new File(args[COMMAND_FILE_IDX]));
            dp.processDirectory(args[SOURCE_DIR_IDX]);
        } catch (IOException | InvalidParameterException | InvalidPathException e){
            System.err.println("ERROR: " + e.getMessage() + "\n");
        }
    }
}
