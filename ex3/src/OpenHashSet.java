import java.util.Iterator;
import java.util.LinkedList;

public class OpenHashSet extends SimpleHashSet{

    private CollectionFacadeSet[] collections;
    /**
     * Default constructor.
     */
    public OpenHashSet(){
        super();
    }

    /**
     * Constructs a new, empty table with the specified load factors, and the default initial capacity (16).
     * @param upperLoadFactor The upper load factor of the hash table.
     * @param lowerLoadFactor The lower load factor of the hash table.
     */
    public OpenHashSet(float upperLoadFactor, float lowerLoadFactor){
        super(lowerLoadFactor, upperLoadFactor);
    }

    /**
     * Data constructor - builds the hash set by adding the elements one by one. Duplicate values should be ignored.
     * The new table has the default values of initial capacity (16), upper load factor (0.75), and lower load factor (0.25).
     * @param data Values to add to the set.
     */
    public OpenHashSet(String[] data){
        super(data);
    }

    /**
     * Initializes all the needed parameters for a collection.
     */
    @Override
    public void initCollection(){
        collections = new CollectionFacadeSet[INITIAL_CAPACITY];
    }

    /**
     * Add a specified element to the set if it's not already in it.
     *
     * @param newValue New value to add to the set
     * @return False iff newValue already exists in the set
     */
    @Override
    public boolean add(String newValue) {
        if(contains(newValue))
            return false;

        // performs rehashing of the table when needed.
        isRehashNeeded();

        // gets the index for the new item.
        int index = indexOf(newValue);

        // checks if the cell is null, if yes creates a new LinkedList<> object
        // inside.
        if(collections[index] == null)
            collections[index] = new CollectionFacadeSet(new LinkedList<String>());

        // gets the addition result.
        boolean addSuccess = collections[index].add(newValue);
        if(addSuccess)
            numElements++; // if successful increases size.

        return addSuccess;
    }

    /**
     * Remove the input element from the set.
     *
     * @param toDelete Value to delete
     * @return True iff toDelete is found and deleted
     */
    @Override
    public boolean delete(String toDelete) {
        if(!contains(toDelete))
            return false;

        // checks if rehashing is needed
        postDeleteCheckup();

        // gets the index of the item in question.
        int index = indexOf(toDelete);

        // gets the collection where the item is located
        CollectionFacadeSet collection = collections[index];

        // calls the remove() method on that collection
        boolean isRemoved = collection.delete(toDelete);
        numElements--; // decrease size.

        // null the cell if the collection there is empty.
        if(collection.size() == 0)
            collections[index] = null;

        return isRemoved;
    }

    /**
     * Checks if HashSet contains the String.
     * @param searchVal Value to search for
     * @return true if contains; false otherwise.
     */
    @Override
    public boolean contains(String searchVal){
        CollectionFacadeSet collection = collections[indexOf(searchVal)];

        return collection != null && collection.contains(searchVal);
    }

    /**
     * Rehashes table for when the capacity is changed
     * @param newCapacity new HashSet capacity
     */
    @Override
    protected void rehashTable(int newCapacity){
        // backup the old collections and create new once.
        CollectionFacadeSet[] oldCollections = collections;
        collections = new CollectionFacadeSet[newCapacity];
        capacityMinusOne = newCapacity - 1;
        numElements = 0;

        // add items from old collections to the new one.
        for (CollectionFacadeSet col : oldCollections) {
            if(col != null){
                Iterator<String> it = col.iterator();

                while (it.hasNext()){
                    add(it.next());
                }
            }
        }
    }
}