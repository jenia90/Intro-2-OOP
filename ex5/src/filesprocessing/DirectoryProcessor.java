package filesprocessing;

import java.io.*;
import java.util.*;

/**
 * Represents an object which process the list of files in a given directory based on commands in
 * special command file.
 */
public class DirectoryProcessor {

    private static final int SOURCE_DIR_IDX = 0, COMMAND_FILE_IDX = 1;
    private static final String FILTER = "FILTER", ORDER = "ORDER";
    private static final int FILTER_RULES_IDX = 1, ORDER_RULES_IDX = 3, ORDER_KWD_IDX = 2;
    private static final String ORDER_MISSING = "ORDER sub-section missing.",
            FILTER_MISSING = "FILTER sub-section missing.";
    private static final String DEFAULT_ORDER = "abs";
    private static final int MINIMUM_SECTION_STEP = 2, NO_FILTER_RULES_STEP = 3, COMPLETE_SECTION_STEP = 4;

    private List<CommandSection> sections;

    /**
     * Constructor
     * @param commandFile file containing rules based on which files will be processed.
     * @throws TypeTwoErrorException when the given command file is empty or non existent.
     */
    public DirectoryProcessor(File commandFile) throws TypeTwoErrorException {
        if (!commandFile.exists() || !commandFile.isFile() || commandFile.length() == 0)
            throw new TypeTwoErrorException("Invalid command file.");

        sections = parseCommandFile(commandFile);
    }

    /**
     * Parses the lines in the command file and creates a list of sections based on the rules in the
     * command file.
     * @param commandFile the file containing the filtering\ordering commands to parse.
     * @return List of CommandSection objects.
     * @throws TypeTwoErrorException if FILTER\ORDER sub-sections missing
     */
    private List<CommandSection> parseCommandFile(File commandFile) throws TypeTwoErrorException {
        List<CommandSection> sections = new ArrayList<>();

        /**
         * This block of code reads the command file and creates a list of its lines.
         */
        List<String> lines = new ArrayList<>();
        try (Scanner scanner = new Scanner(commandFile)) {
            while (scanner.hasNext()) {
                lines.add(scanner.next());
            }
        } catch (IOException e){
            throw new TypeTwoErrorException(e.getMessage());
        }

        /**
         * This block of code parses the lines from the command file and
         */
        int line = 0;
        while (line + MINIMUM_SECTION_STEP <= lines.size()){
            if (!lines.get(line).equals(FILTER))
                throw new TypeTwoErrorException(FILTER_MISSING);
            // Checks if order sub-section is missing
            else if(line + ORDER_KWD_IDX >= lines.size() || !lines.get(line + ORDER_KWD_IDX).equals(ORDER)){
                throw new TypeTwoErrorException(ORDER_MISSING);
            }

            // clamps the line indexes inside the List<> bounds.
            int orderingRulesIndex =
                    (line + ORDER_RULES_IDX) < lines.size() ? (line + ORDER_RULES_IDX) : lines.size() - 1;

            // checks if next line after the ORDER keyword is FILTER keyword.
            boolean noOrderRules = lines.get(orderingRulesIndex).equals(FILTER);

            String filteringRules = lines.get(line + FILTER_RULES_IDX);
            // if we get ORDER sub-section without rules we assign the default ordering; ordering rule otherwise.
            String orderingRules = noOrderRules ? DEFAULT_ORDER : lines.get(orderingRulesIndex);

            // creates a new CommandSection object and adds it to the list
            sections.add(new CommandSection(filteringRules, orderingRules, line));

            // if no ordering rule we step 3 lines forward; 4 otherwise.
            line += noOrderRules ? NO_FILTER_RULES_STEP : COMPLETE_SECTION_STEP;
        }

        return sections;
    }

    /**
     * Process the files in the directory according to the rules in each command file section.
     * @param dir the directory to be precessed.
     * @throws TypeTwoErrorException if the path is incorrect.
     */
    private void processDirectory(File dir) throws TypeTwoErrorException{
        // checs if directory exists and it is in fact, a directory.
        if (!dir.exists() || !dir.isDirectory())
            throw new TypeTwoErrorException("Invalid path.");

        // if no sections were parsed in the command file we print the files in the directory.
        if(sections.isEmpty()) {
            Arrays.asList(dir.listFiles()).forEach(file -> System.out.println(file.getName()));
            return;
        }

        // Iterates through the sections and processes the files in the directory according to the rules in
        // the current section.
        for (CommandSection section : sections) {

            // prints warnings for the current section.
            section.getWarnings().forEach(System.err::println);

            // filters the files in the directory.
            List<File> fileList = Arrays.asList(dir.listFiles(file -> section.getFileFilter().and(File::isFile).test(file)));

            //sorts the files in the directory.
            fileList.sort(section.getFileComparator());

            // and finally prints the filtered and sorted list of files.
            fileList.forEach(file -> System.out.println(file.getName()));
        }
    }

    /**
     * Main method for the DirectoryProcessor class. Given a directory and a command file
     * it prints a list of sorted and filtered files according to the rules in the command file.
     * @param args array of command line arguments. 1st index is the source dir and 2nd index is the command file.
     */
    public static void main(String[] args){
        try{
            // in case there are arguments missing we throw a Type II Error.
            if (args.length != 2)
                throw new TypeTwoErrorException("Wrong command line arguments usage.");

            // initializes the DirectoryProcessor object with the given command file.
            DirectoryProcessor dp = new DirectoryProcessor(new File(args[COMMAND_FILE_IDX]));

            // process the given directory according to the rules in the command file.
            dp.processDirectory(new File(args[SOURCE_DIR_IDX]));
        } catch (TypeTwoErrorException e){
            System.err.println(e.getMessage());
        }
    }
}
