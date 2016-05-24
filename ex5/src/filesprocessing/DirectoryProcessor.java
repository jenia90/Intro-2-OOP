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

    public DirectoryProcessor(File commandFile) throws TypeTwoErrorException {
        if (commandFile == null || commandFile.length() == 0)
            throw new TypeTwoErrorException("Invalid path or command file.");

        sections = getCommandSections(commandFile);
    }

    private List<CommandSection> getCommandSections(File commandFile) throws TypeTwoErrorException {
        List<CommandSection> sections = new ArrayList<>();

        List<String> lines = new ArrayList<>();
        try (Scanner scanner = new Scanner(commandFile)) {
            while (scanner.hasNext()) {
                lines.add(scanner.next());
            }
        } catch (IOException e){
            System.err.println("ERROR: " + e.getMessage() + "\n");
        }

        int index = 0;
        while (index + 2 <= lines.size()){
            if (!lines.get(index).equals(FILTER))
                throw new TypeTwoErrorException("FILTER sub-section missing.");
            int orderingRulesIndex =
                    (index + ORDER_RULES_IDX) < lines.size() ? (index + ORDER_RULES_IDX) : lines.size() - 1;
            try{ //TODO: FIX THIS PIECE OF CRAP CODE!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
                if(!lines.get(index + 2).equals(ORDER)){
                    throw new TypeTwoErrorException("ORDER sub-section missing.");
                }
            } catch (IndexOutOfBoundsException e){
                throw new TypeTwoErrorException("ORDER sub-section missing.");
            }

            String filteringRules = lines.get(index + FILTER_RULES_IDX);
            String orderingRules = lines.get(orderingRulesIndex);

            sections.add(new CommandSection(filteringRules, orderingRules, index));

            if (lines.get(orderingRulesIndex).equals(FILTER)){
                index += 3;
                continue;
            }

            index += 4;
        }

        return sections;
    }

    private void processDirectory(String path) throws TypeTwoErrorException{
        File dir = new File(path);
        if (!dir.exists() || !dir.isDirectory())
            throw new TypeTwoErrorException("Invalid path.");
        if(sections.size() == 0) {
            Arrays.asList(dir.listFiles()).forEach(file -> System.out.println(file.getName()));
        }

        for (CommandSection section : sections) {
            section.getWarnings().forEach(System.err::println);
            List<File> fileList = Arrays.stream(dir.listFiles()).
                    filter(file -> section.getFileFilter().and(File::isFile).test(file)).collect(Collectors.toList());
            fileList.sort(section.getFileComparator());
            fileList.forEach(file -> System.out.println(file.getName()));
        }

    }

    public static void main(String[] args){
        try{
            if (args.length != 2)
                throw new TypeTwoErrorException("Please check that path of source dir and command file are correct.");
            DirectoryProcessor dp = new DirectoryProcessor(new File(args[COMMAND_FILE_IDX]));
            dp.processDirectory(args[SOURCE_DIR_IDX]);
        } catch (TypeTwoErrorException e){
            System.err.println(e.getMessage());
        }
    }
}
