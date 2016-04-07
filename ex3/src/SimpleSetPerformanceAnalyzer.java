import java.util.HashSet;
import java.util.LinkedList;
import java.util.TreeSet;

/**
 * Created by jenia on 22/03/2016.
 */
public class SimpleSetPerformanceAnalyzer {

    private static final String D1_HASH_COMP = "-13170890158";
    private static final String WARM_UP_WORD = "hi";
    private static final int NANO_TO_MILLI = 1000000;
    private static final String DATA1_PATH = "C:\\Users\\jenia\\IdeaProjects\\University\\Intro-2-OOP\\ex3\\data\\data1.txt";
    private static final String DATA2_PATH = "C:\\Users\\jenia\\IdeaProjects\\University\\Intro-2-OOP\\ex3\\data\\data2.txt";

    private SimpleSet[] sets;
    
    public static void main(String[] args){
        SimpleSetPerformanceAnalyzer performanceAnalyzer = new SimpleSetPerformanceAnalyzer();

        performanceAnalyzer.runTests();

    }

    public SimpleSetPerformanceAnalyzer(){
        initSets();
    }

    private void runTests(){
        sets = initSets();

        testAddData();

        //sets = initSets();

        //addDataWithoutTime(sets, DATA1_PATH);

    }

    private void timeContains(SimpleSet set, int iters){

    }

    private void addData(SimpleSet set, String[] data){
        for (String str : data) {
            set.add(str);
        }
        System.out.println(set.size()); //TODO REMOVE!
    }

    private void addDataWithoutTime(SimpleSet[] sets, String filename){
        String[] data = Ex3Utils.file2array(filename);
        for (SimpleSet set :
                sets) {
            addData(set, data);
        }
    }

    private void addDataWithTime(SimpleSet[] sets, String[] data, String filename){
        long sTime;
        for (int i = 0; i < sets.length; i++) {
            sTime = System.nanoTime();
            addData(sets[i], data);
            System.out.println(String.format("Adding data from %s to %s took: %dms",
                    filename, sets[i].getClass().toString(), (System.nanoTime() - sTime) / NANO_TO_MILLI));
        }
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

    private void testAddData() {
        sets = initSets();
        String[] data1 = Ex3Utils.file2array(DATA1_PATH);
        addDataWithTime(sets, data1, DATA1_PATH);

        sets = initSets();
        String[] data2 = Ex3Utils.file2array(DATA2_PATH);
        addDataWithTime(sets, data2, DATA2_PATH);
    }

    private void warmUp(SimpleSet set, int iters){
        for (int i = 0; i < iters; i++) {
            set.contains(WARM_UP_WORD);
        }
    }
}
