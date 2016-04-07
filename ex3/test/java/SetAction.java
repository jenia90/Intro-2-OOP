import static org.junit.Assert.assertEquals;


/**
 * Created by Yossi on 27/03/2016.
 */
public class SetAction {

    public enum SetActionType {
        ADD,
        DELETE,
        CONTAINS,
        CAPACITY,
        SIZE
    }

    private SetActionType type;
    private Object data;
    private Object expected;

    public SetAction(String line) throws Exception {
        String[] parts = line.split(" ");
        String type = parts[0];

        if(type.equals("add")) {
            this.type = SetActionType.ADD;
            this.data = parts[1];
            this.expected = Boolean.parseBoolean(parts[2]);
        } else if(type.equals("contains")) {
            this.type = SetActionType.CONTAINS;
            this.data = parts[1];
            this.expected = Boolean.parseBoolean(parts[2]);
        } else if(type.equals("size")) {
            this.type = SetActionType.SIZE;
            this.expected = Integer.parseInt(parts[1]);
        } else if(type.equals("capacity")) {
            this.type = SetActionType.CAPACITY;
            this.expected = Integer.parseInt(parts[1]);
        } else if(type.equals("delete")) {
            this.type = SetActionType.DELETE;
            this.data = parts[1];
            this.expected = Boolean.parseBoolean(parts[2]);
        } else {
            throw new Exception("Couldn't parse line " + line);
        }
    }

    public SetActionType getType() {
        return type;
    }

    public Object getData() {
        return data;
    }

    public Object getExpected() {
        return expected;
    }

    public void test(SimpleHashSet set) {
        switch(type) {
            case ADD:
                assertEquals(expected, set.add((String) data));
                break;
            case CONTAINS:
                assertEquals(expected, set.contains((String) data));
                break;
            case DELETE:
                assertEquals(expected, set.delete((String) data));
                break;
            case CAPACITY:
                assertEquals(expected, set.capacity());
                break;
            case SIZE:
                assertEquals(expected, set.size());
                break;
        }
    }
}
