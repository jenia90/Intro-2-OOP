/**
 * Created by jenia on 22/03/2016.
 */
public class ClosedHashSet extends SimpleHashSet {

    private String[] collection;
    private boolean[] isOccuppied;

    public ClosedHashSet(){
        super();
    }

    public ClosedHashSet(float upperLoadFactor, float lowerLoadFactor){
        super(lowerLoadFactor, upperLoadFactor);
    }

    public ClosedHashSet(String[] data){
        super(data);
    }

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
        System.out.println("adding: " + newValue);
        if(contains(newValue)){
            return false;
        }

        if(isRehashNeeded()){
            rehashTable(capacity() * 2);
        } else {
            return addHelper(newValue);
        }
        return false;
    }

    protected boolean addHelper(String newValue) {
        for (int i = 0; i < capacity(); i++) {
            int index = indexOf(newValue, i);

            if(!isOccuppied[index]){
                isOccuppied[index] = true;
                collection[index] = newValue;

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

        for (int i = 0; i < capacity(); i++) {
            int index = indexOf(toDelete, i);

            if(toDelete.equals(collection[index])){
                collection[index] = null;
                isOccuppied[index] = false;

                postDeleteCheckup();

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
        System.out.println("Rehashing..");
        System.out.println("New capacity: " + newCapacity);
        System.out.println("Old Capacity " + capacity());
        // backup old list and create new one
        String[] oldCollection = collection;
        collection = new String[newCapacity];

        //backup old isOccupied array and create new one
        boolean[] oldIsOccuppied = isOccuppied;
        isOccuppied = new boolean[newCapacity];

        capacityMinusOne = newCapacity - 1;
        System.out.println("Size: " + numElements);
        numElements = 0;

        // re-add all items from old list to the new one with new hash keys.
        for (int i = 0; i < oldCollection.length; i++) {
            if(oldIsOccuppied[i])
                add(oldCollection[i]);
        }
    }
}
