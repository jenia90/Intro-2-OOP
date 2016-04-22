package oop.ex4.data_structures;
import java.util.Iterator;

/**
 * Created by jenia on 08/04/2016.
 */
public class AvlTree implements Iterable<Integer> {

    private int size = 0;
    private AvlTreeNode root;

    public AvlTree(){

    }

    public AvlTree(AvlTree avlTree){

    }

    public AvlTree(int[] data){
        for (int val :
                data) {
            add(val);
        }
    }

    public boolean add(int newValue){
        if(root == null){
            root = new AvlTreeNode(newValue, this, null);
            size++;
            return true;
        } else {
            return false;
        }
    }

    public int contains(int searchVal){
        return 0;
    }

    public boolean delete(int toDelete){
        return false;
    }

    public int size(){
        return size;
    }

    public static int findMinNodes(int h){
        return 0;
    }

    /**
     * Returns an iterator over elements of type {@code T}.
     *
     * @return an Iterator.
     */
    @Override
    public Iterator<Integer> iterator() {
        return null;
    }
}
