import java.util.Iterator;
import java.util.Objects;


public abstract class SimpleHashSet implements SimpleSet {

    /* constants */
    private static final float DEFAULT_UPPER_LOADFACTOR = 0.75f;
    private static final float DEFAULT_LOWER_LOADFACTOR = 0.25f;
    protected static final int INITIAL_CAPACITY = 16;

    /* class fields */
    protected int capacityMinusOne;
    protected int numElements = 0;

    protected float lowerLoadFactor;
    protected float upperLoadFactor;

    /**
     * Constructs a new, empty table with the specified load factors, and the default initial capacity (16).
     * @param upperLoadFactor The upper load factor of the hash table.
     * @param lowerLoadFactor The lower load factor of the hash table.
     */
    public SimpleHashSet(float lowerLoadFactor, float upperLoadFactor){
        this.lowerLoadFactor = lowerLoadFactor;
        this.upperLoadFactor = upperLoadFactor;
        capacityMinusOne = INITIAL_CAPACITY - 1;

        initCollection();
    }

    /**
     * Default constructor
     */
    public SimpleHashSet(){
        this(DEFAULT_LOWER_LOADFACTOR, DEFAULT_UPPER_LOADFACTOR);

    }

    /**
     * Data constructor - builds the hash set by adding the elements one by one. Duplicate values should be ignored.
     * The new table has the default values of initial capacity (16), upper load factor (0.75), and lower load factor (0.25).
     * @param data Values to add to the set.
     */
    public SimpleHashSet(String[] data){
        this(DEFAULT_LOWER_LOADFACTOR, DEFAULT_UPPER_LOADFACTOR);

        for (String str : data){
            add(str);
        }
    }

    /**
     * Initializes all the needed parameters for a collection.
     */
    protected abstract void initCollection();

    /**
     * Add a specified element to the set if it's not already in it.
     *
     * @param newValue New value to add to the set
     * @return False iff newValue already exists in the set
     */
    @Override
    public abstract boolean add(String newValue);

    /**
     * Look for a specified value in the set.
     *
     * @param searchVal Value to search for
     * @return True iff searchVal is found in the set
     */
    @Override
    public abstract boolean contains(String searchVal);

    /**
     * Remove the input element from the set.
     *
     * @param toDelete Value to delete
     * @return True iff toDelete is found and deleted
     */
    @Override
    public abstract boolean delete(String toDelete);

    /**
     * @return The number of elements currently in the set
     */
    @Override
    public int size() {
        return numElements;
    }

    /**
     * Returns capacity of the collection.
     * @return capacity of the collection
     */
    public int capacity(){
        return capacityMinusOne + 1;
    }

    /**
     * Rehashes table for when the capacity is changed
     * @param newCapacity new HashSet capacity
     */
    protected abstract void rehashTable(int newCapacity);

    /**
     * Gets index of an item.
     * @param value item to calculate index for.
     * @return Hashed index
     */
    protected int indexOf(String value){
        return indexOf(value, 0);
    }
    /**
     * Gets index of an item.
     * @param value item to calculate index for.
     * @param i secondary index used in quadratic probing.
     * @return Hashed index
     */
    protected int indexOf(String value, int i){
        return (value.hashCode() + i * (i + 1) / 2) & capacityMinusOne;
    }

    /**
     * Checks if rehashing is needed prior to adding an item.
     */
    protected void isRehashNeeded() {
        float loadFactor = (size() + 1) / (float)capacity();

        if(loadFactor > upperLoadFactor)
            rehashTable(capacity() * 2);
    }

    /**
     * Checks if table needs to be rehashed after deleting an item from the collection
     */
    protected void postDeleteCheckup() {
        float loadFactor = (size() - 1) / (float)capacity();

        if(loadFactor < lowerLoadFactor && capacity() > 1)
            rehashTable(capacity() / 2);
    }
}
