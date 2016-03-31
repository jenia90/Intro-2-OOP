import java.util.LinkedList;

/**
 * Created by jenia on 26/03/2016.
 */
public class AutoTests {
    public static void main(String[] args){
        LinkedList<String> list = new LinkedList<String>();

        CollectionFacadeSet cfs = new CollectionFacadeSet(list);
        long startTime = System.nanoTime();
        for (String s :
                Ex3Utils.file2array("C:\\Users\\jenia\\IdeaProjects\\University\\Intro-2-OOP\\ex3\\src\\data1.txt")) {
            cfs.add(s);
        }
        System.out.println((System.nanoTime() - startTime) / 1000000);

        startTime = System.nanoTime();
        for (String s :
                cfs.collection){
            System.out.println(s);
        }
        System.out.println((System.nanoTime() - startTime) / 1000000);

        System.out.println(cfs.add("hello"));
        System.out.println(cfs.contains("hello"));
        System.out.println(cfs.size());
        System.out.println(cfs.delete("hello"));

    }
}
