import java.util.LinkedList;

/**
 * Created by jenia on 22/03/2016.
 */
public class OpenHashSet extends SimpleHashSet{

    private CollectionFacadeSet[] collections;
    private int numElements = 0;
    // private CollectionFacadeSet collection = new CollectionFacadeSet(list);
    private float upperLoadFactor = 0;
    private float lowerLoadFactor = 0;
    /**
     * Default constructor.
     */
    public OpenHashSet(){
        capacityMinusOne = INITIAL_CAPACITY - 1;
        collections = new CollectionFacadeSet[INITIAL_CAPACITY];
    }

    /**
     * Constructs a new, empty table with the specified load factors, and the default initial capacity (16).
     * @param upperLoadFactor The upper load factor of the hash table.
     * @param lowerLoadFactor The lower load factor of the hash table.
     */
    public OpenHashSet(float upperLoadFactor, float lowerLoadFactor){
        this.upperLoadFactor = upperLoadFactor;
        this.lowerLoadFactor = lowerLoadFactor;
    }

    /**
     * Data constructor - builds the hash set by adding the elements one by one. Duplicate values should be ignored.
     * The new table has the default values of initial capacity (16), upper load factor (0.75), and lower load factor (0.25).
     * @param data Values to add to the set.
     */
    public OpenHashSet(String[] data){

    }

    /**
     * Add a specified element to the set if it's not already in it.
     *
     * @param newValue New value to add to the set
     * @return False iff newValue already exists in the set
     */
    @Override
    public boolean add(String newValue) {
        int index = getHashedIndex(newValue);

        if(collections[index] == null) {
            collections[index] = new CollectionFacadeSet(new LinkedList<String>());
            return collections[index].add(newValue);
        }
        return collections[index].add(newValue);
    }

    /**
     * Gets hashed index of current item.
     * @param val item calculate index for.
     * @return Hashed index
     */
    private int getHashedIndex(String val){
        return val.hashCode() & capacityMinusOne;
    }
    
    private void rehash(){
    }

    public int capacity(){
        return capacityMinusOne + 1;
    }
}