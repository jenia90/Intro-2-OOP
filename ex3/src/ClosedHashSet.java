/**
 * Created by jenia on 22/03/2016.
 */
public class ClosedHashSet extends SimpleHashSet {

    private String[] collection;
    private boolean[] isOccupied;

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
        isOccupied = new boolean[INITIAL_CAPACITY];
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

        prepForAdd();

        for (int i = 0; i < capacity(); i++) {
            int index = indexOf(newValue, i);

            if(!isOccupied[i]){
                isOccupied[i] = true;
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
        for (int i = 0; i < capacity(); i++) {
            int index = indexOf(toDelete, i);

            if(toDelete.equals(collection[index])){
                numElements--;
                collection[index] = null;
                isOccupied[index] = false;

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
    protected void rehashTable(int newCapacity) {
        String[] oldCollection = collection;
        collection = new String[newCapacity];

        boolean[] oldIsOccuppied = isOccupied;
        isOccupied = new boolean[newCapacity];

        capacityMinusOne = newCapacity - 1;

        for (int i = 0; i < oldCollection.length; i++) {
            if(oldIsOccuppied[i])
                add(oldCollection[i]);
        }
    }
}
