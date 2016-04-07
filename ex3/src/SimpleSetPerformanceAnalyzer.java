import java.util.HashSet;
import java.util.LinkedList;
import java.util.StringJoiner;
import java.util.TreeSet;
import java.util.concurrent.SynchronousQueue;


public class SimpleSetPerformanceAnalyzer {

    private static final String NEG_COMP = "-13170890158";
    private static final String POS_COMP = "23";
    private static final String WARM_UP_WORD = "hi";
    private static final int NANO_TO_MILLI = 1000000;
    private static final String DATA1_PATH = "C:\\Users\\jenia\\IdeaProjects\\Intro-2-OOP\\ex3\\data\\data1.txt";
    private static final String DATA2_PATH = "C:\\Users\\jenia\\IdeaProjects\\Intro-2-OOP\\ex3\\data\\data2.txt";
    private static final String[] CollectionTypes = {"OpenHashSet", "ClosedHashSet", "TreeSet", "LinkedList", "HashSet"};
    private static final int CONTAINS_ITERS = 70000;
    private static final int CONTAINS_ITERS_LL = 7000;

    private String[][] dataSets;
    private SimpleSet[][] allSets;

    public static void main(String[] args) {
        String[] data1 = Ex3Utils.file2array(DATA1_PATH);
        String[] data2 = Ex3Utils.file2array(DATA2_PATH);
        String[][] dataSets = new String[][]{data1, data2};

        // Creates a new performance analyzer object and passes the data sets to it
        SimpleSetPerformanceAnalyzer performanceAnalyzer = new SimpleSetPerformanceAnalyzer(dataSets);

        // runs all needed tests
        performanceAnalyzer.runTests();

    }

    /**
     * Default constructor for SimpleSetPerformanceAnalyzer
     */
    public SimpleSetPerformanceAnalyzer() {
        allSets = new SimpleSet[][]{initSets(), initSets()};
    }

    /**
     * Constructor for SimpleSetPerformanceAnalyzer which initializes the data sets.
     * @param dataSets datasets for performance analyzes
     */
    public SimpleSetPerformanceAnalyzer(String[][] dataSets){
        this();
        this.dataSets = dataSets;
    }


    /**
     * Initializes the sets in the sets in the SimpleSet array.
     * @return
     */
    private SimpleSet[] initSets() {
        return new SimpleSet[]{
                new OpenHashSet(),
                new ClosedHashSet(),
                new CollectionFacadeSet(new TreeSet<String>()),
                new CollectionFacadeSet(new LinkedList<String>()),
                new CollectionFacadeSet(new HashSet<String>())
        };
    }

    /**
     * Runs performance tests on all sets.
     */
    private void runTests() {
        testAddDataToSets();

        // get collections initialized with different data sets.
        SimpleSet[] sets1 = allSets[0];
        SimpleSet[] sets2 = allSets[1];

        // performs all the needed set.contains() tests
        testContainsInSets(sets1, WARM_UP_WORD, 1);
        testContainsInSets(sets1, NEG_COMP, 1);
        testContainsInSets(sets2, POS_COMP, 2);
        testContainsInSets(sets2, WARM_UP_WORD, 2);

    }

    /**
     * Performs set.contains operation on each collection type and measures data.
     * Before measuring actual time the method performs a warm-up.
     * @param sets The sets to perform the operation on.
     * @param keyword The keyword to perform the operation with
     * @param data the dataSet which is loaded.
     */
    private void testContainsInSets(SimpleSet[] sets, String keyword, int data) {
        System.out.println(String.format(
                "#These values correspond to the time it takes (in ns) to check " +
                        "if given collection contains \"%s\" when loaded with data%d",
                keyword, data));

        String msg = "_Contains_";
        switch (keyword){
            case WARM_UP_WORD:
                msg += WARM_UP_WORD + data + " = ";
                break;
            case NEG_COMP:
                msg += "negative = ";
                break;
            default:
                msg += keyword + " = ";
                break;
        }

        for (int i = 0; i < sets.length; i++) {
            if(CollectionTypes[i].equals("LinkedList")) {
                measureContains(sets[i], keyword, CONTAINS_ITERS_LL);
                System.out.println(CollectionTypes[i] + msg + measureContains(sets[i], keyword, CONTAINS_ITERS_LL));
            } else {
                measureContains(sets[i], keyword, CONTAINS_ITERS);
                System.out.println(CollectionTypes[i] + msg + measureContains(sets[i], keyword, CONTAINS_ITERS));
            }
        }

        System.out.println();
    }

    /**
     * Adds data1 and data2 to all collection types and measures time.
     */
    private void testAddDataToSets() {
        for (int i = 0; i < allSets.length; i++) {
            SimpleSet[] sets = allSets[i];
            String[] data = dataSets[i];
            int dataNum = i + 1;

            System.out.println(String.format(
                    "#These values correspond to the time it takes (in ms) to insert data%d to all data structures:",
                    dataNum));

            for (int j = 0; j < sets.length; j++) {
                System.out.println(String.format("%s_AllData%d = %d",
                        CollectionTypes[j],
                        dataNum,
                        measureAddData(sets[j], data)));
            }

            System.out.println();
        }
    }

    /**
     * Add list of string to a set.
     * @param set set to add data to
     * @param data the list of strings to add.
     */
    private void addDataToSet(SimpleSet set, String[] data){
        for (String str : data) {
            set.add(str);
        }
    }

    /**
     * Measures how long it takes to add data to a given set.
     * @param set the set to measure the time for
     * @param data the array of Strings to add to the given set
     */
    private long measureAddData(SimpleSet set, String[] data){
        long sTime = System.nanoTime();
        addDataToSet(set, data);
        return (System.nanoTime() - sTime) / NANO_TO_MILLI;
    }

    /**
     * Measures the time it takes to check if a set contains a given string
     * @param set The set to perform the test on
     * @param value The string to lookup
     * @param iters number of iterations to run the test.
     * @return
     */
    private long measureContains(SimpleSet set, String value, int iters){
        long sTime = System.nanoTime();

        for (int i = 0; i < iters; i++) {
            set.contains(value);
        }

        // returns the average time it takes to find the item in the set.
        return (System.nanoTime() - sTime) / iters;
    }

}
