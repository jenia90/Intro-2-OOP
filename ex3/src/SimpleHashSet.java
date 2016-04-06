import java.util.Iterator;
import java.util.Objects;

/**
 * Created by jenia on 22/03/2016.
 */
public abstract class SimpleHashSet implements SimpleSet {

    protected final static int INITIAL_CAPACITY = 16;

    private static final float DEFAULT_UPPER_LOADFACTOR = 0.75f;
    private static final float DEFAULT_LOWER_LOADFACTOR = 0.25f;

    protected int capacityMinusOne;
    protected int numElements = 0;

    protected float lowerLoadFactor;
    protected float upperLoadFactor;

    public SimpleHashSet(float lowerLoadFactor, float upperLoadFactor){
        this.lowerLoadFactor = lowerLoadFactor;
        this.upperLoadFactor = upperLoadFactor;
        capacityMinusOne = INITIAL_CAPACITY - 1;

        initCollection();
    }

    public SimpleHashSet(){
        this(DEFAULT_LOWER_LOADFACTOR, DEFAULT_UPPER_LOADFACTOR);

    }

    public SimpleHashSet(String[] data){
        this();

        for (String str : data){
            add(str);
        }
    }

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
     * Rehashes table for when the capacity is changed
     * @param newCapacity new HashSet capacity
     */
    protected abstract void rehashTable(int newCapacity);

    public int capacity(){
        return capacityMinusOne + 1;
    }

    /**
     * Gets index of an item.
     * @param val item to calculate index for.
     * @return Hashed index
     */
    protected int indexOf(String val){
        return indexOf(val, 0);
    }
    /**
     * Gets index of an item.
     * @param val item to calculate index for.
     * @param i secondary index used in quadratic probing.
     * @return Hashed index
     */
    protected int indexOf(String val, int i){
        return (val.hashCode() + i*i/2 + i/2) & capacityMinusOne;
    }

    protected void prepForAdd() {
        float loadFactor = (numElements + 1) / capacity();

        if(loadFactor > upperLoadFactor)
            rehashTable(capacity() * 2);
    }

    protected void postDeleteCheckup() {
        float loadFactor = (size() - 1) / capacity();

        if(loadFactor < lowerLoadFactor && capacity() >  1)
            rehashTable(capacity() / 2);
    }
}
