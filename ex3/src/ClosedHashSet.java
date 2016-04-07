
public class ClosedHashSet extends SimpleHashSet {

    // Holds the item collection itself.
    private String[] collection;

    // holds the status of each collection cell (empty\occupied)
    private boolean[] isOccuppied;

    /**
     * Default constructor.
     */
    public ClosedHashSet(){
        super();
    }

    /**
     * Constructs a new, empty table with the specified load factors, and the default initial capacity (16).
     * @param upperLoadFactor The upper load factor of the hash table.
     * @param lowerLoadFactor The lower load factor of the hash table.
     */
    public ClosedHashSet(float upperLoadFactor, float lowerLoadFactor){
        super(lowerLoadFactor, upperLoadFactor);
    }

    /**
     * Data constructor - builds the hash set by adding the elements one by one. Duplicate values should be ignored.
     * The new table has the default values of initial capacity (16), upper load factor (0.75), and lower load factor (0.25).
     * @param data Values to add to the set.
     */
    public ClosedHashSet(String[] data){
        super(data);
    }

    /**
     * Initializes all the needed parameters for a collection.
     */
    @Override
    public void initCollection(){
        collection = new String[INITIAL_CAPACITY];
        isOccuppied = new boolean[INITIAL_CAPACITY];
    }

    /**
     * Add a specified element to the set if it's not already in it.
     *
     * @param newValue New value to add to the set
     * @return False iff newValue already exists in the set
     */
    @Override
    public boolean add(String newValue) {
        if(contains(newValue)){
            return false;
        }

        isRehashNeeded(); //check if rehashing needed

        // finds a proper index for the given item by combining hashCode and secondary index.
        for (int i = 0; i < capacity(); i++) {
            int index = indexOf(newValue, i);

            // Check if current index is occupied by another item.
            if(!isOccuppied[index]){
                numElements++; // increase size
                isOccuppied[index] = true; // mark the cell as occupied
                collection[index] = newValue; // assign the value.

                return true;
            }
        }
        return false;
    }

    /**
     * Look for a specified value in the set.
     *
     * @param searchVal Value to search for
     * @return True iff searchVal is found in the set
     */
    @Override
    public boolean contains(String searchVal) {
        for (int i = 0; i < capacity(); i++) {
            int index = indexOf(searchVal, i);

            // continue the loop if the found index is null in case the actual
            // item is mapped to different secondary index
            if(collection[index] == null)
                continue;

            if(searchVal.equals(collection[index]))
                return true;
        }

        return false;
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

        postDeleteCheckup(); // when needed rehashes the table.

        for (int i = 0; i < capacity(); i++) {
            int index = indexOf(toDelete, i);

            // update the collection when the item is found.
            if(toDelete.equals(collection[index])){
                collection[index] = null; // null the cell
                isOccuppied[index] = false; // mark cell as empty
                numElements--; // decrease size.

                return true;
            }
        }

        return false;
    }

    /**
     * Rehashes table for when the capacity is changed
     * @param newCapacity new HashSet capacity
     */
    @Override
    public void rehashTable(int newCapacity) {
        // backup old list and create new one
        String[] oldCollection = collection;
        collection = new String[newCapacity];

        //backup old isOccupied array and create new one
        boolean[] oldIsOccuppied = isOccuppied;
        isOccuppied = new boolean[newCapacity];

        // update capacity field and reset size of collection which will be rebuilt by the add() method.
        capacityMinusOne = newCapacity - 1;
        numElements = 0;

        // re-add all items from old list to the new one with new hash keys.
        for (int i = 0; i < oldCollection.length; i++) {
            if(oldIsOccuppied[i])
                add(oldCollection[i]);
        }
    }
}
