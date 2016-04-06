import java.util.HashSet;
import java.util.LinkedList;
import java.util.TreeSet;

/**
 * Created by jenia on 22/03/2016.
 */
public class SimpleSetPerformanceAnalyzer {

    private static final String D1_HASH_COMP = "-13170890158";
    private static final String WARM_UP_WORD = "hi";
    private static final int NANO_DIVIDER = 1000000;
    private static final int MILLI_DIVIDER = 1000;
    private static final String DATA1_PATH = "C:\\Users\\jenia\\IdeaProjects\\University\\Intro-2-OOP\\ex3\\data\\data1.txt";
    private static final String DATA2_PATH = "C:\\Users\\jenia\\IdeaProjects\\University\\Intro-2-OOP\\ex3\\data\\data2.txt";
    
    public static void main(String[] args){
        SimpleSetPerformanceAnalyzer performanceAnalyzer = new SimpleSetPerformanceAnalyzer();

        performanceAnalyzer.runTests();

    }

    private void timeContains(SimpleSet set, int iters){

    }

    private void addData(SimpleSet set, String filename){
        String[] data = Ex3Utils.file2array(filename);

        long sTime = System.nanoTime();

        for (String str : data) {
            set.add(str);
        }

        System.out.println(String.format("Adding data from %s to %s took: %dms", filename, set.toString(), (System.nanoTime() - sTime) / MILLI_DIVIDER));
    }

    private SimpleSet[] initSets(){
        return new SimpleSet[]{
                new OpenHashSet(),
                new ClosedHashSet(),
                new CollectionFacadeSet(new TreeSet<String>()),
                new CollectionFacadeSet(new LinkedList<String>()),
                new CollectionFacadeSet(new HashSet<String>())
        };
    }

    private void runTests(){
        long sTime;
        SimpleSet[] sets = initSets();

        for (int i = 0; i < sets.length; i++) {
            addData(sets[i], DATA1_PATH);
        }

        sets = initSets();
    }

    private void warmUp(SimpleSet set, int iters){
        for (int i = 0; i < iters; i++) {
            set.contains(WARM_UP_WORD);
        }
    }
}
