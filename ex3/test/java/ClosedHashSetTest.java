import org.junit.Assert;
import org.junit.Test;

/**
 * Created by Yossi on 24/03/2016.
 */
public class ClosedHashSetTest {

    @Test
    public void addOne() {
        ClosedHashSet set = new ClosedHashSet();
        String str = "hello";

        Assert.assertTrue("Inserting hello", set.add(str));
        Assert.assertTrue("Contains hello", set.contains(str));
        Assert.assertTrue("Size 1", set.size() == 1);
        Assert.assertTrue("Removing hello", set.delete(str));
        Assert.assertTrue("Size 0", set.size() == 0);
        Assert.assertFalse("No longer contains hello", set.contains(str));
    }

    @Test
    public void addTwenty() {
        ClosedHashSet set = new ClosedHashSet();

        for(int i = 0; i < 20; i++) {
            Assert.assertTrue("Inserting " + i, set.add(Integer.toString(i)));
            Assert.assertEquals("Size equals " + (i + 1), i + 1, set.size());
        }

        for(int i = 0; i < 20; i++) {
            Assert.assertTrue("Contains " + i, set.contains(Integer.toString(i)));
        }

        Assert.assertEquals("Reshashing happened", 32, set.capacity());

        for(int i = 0; i < 20; i++) {
            Assert.assertTrue("Removing " + i, set.delete(Integer.toString(i)));
        }

        Assert.assertEquals("Should be empty", 0, set.size());

        Assert.assertEquals("Reshashing happened again", 2, set.capacity());
    }
}
