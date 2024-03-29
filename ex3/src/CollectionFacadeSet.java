import java.util.Collection;
import java.util.Iterator;

/**
 * Simple wrapper classes for java built in collection classes.
 */
public class CollectionFacadeSet implements SimpleSet {

    private Collection<String> collection;

    /**
     * Constructor which receives a single java.util.Collection object
     * and assigns it to the collection field.
     * @param collection Generic Collection object.
     */
    public CollectionFacadeSet(Collection<String> collection){
        this.collection = collection;
    }
    /**
     * Add a specified element to the set if it's not already in it.
     *
     * @param newValue New value to add to the set
     * @return False iff newValue already exists in the set
     */
    @Override
    public boolean add(String newValue) {
        return !contains(newValue) && collection.add(newValue);
    }

    /**
     * Look for a specified value in the set.
     *
     * @param searchVal Value to search for
     * @return True iff searchVal is found in the set
     */
    @Override
    public boolean contains(String searchVal) {
        return collection.contains(searchVal);
    }

    /**
     * Remove the input element from the set.
     *
     * @param toDelete Value to delete
     * @return True iff toDelete is found and deleted
     */
    @Override
    public boolean delete(String toDelete) {
        return collection.remove(toDelete);
    }

    /**
     * @return The number of elements currently in the set
     */
    @Override
    public int size() {
        return collection.size();
    }

    /**
     * Returns the LinkedList iterator();
     * @return
     */
    public Iterator<String> iterator() {
        return collection.iterator();
    }
}
