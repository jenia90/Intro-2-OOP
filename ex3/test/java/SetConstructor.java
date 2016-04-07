/**
 * Created by Yossi on 27/03/2016.
 */
public class SetConstructor {

    public enum SetConstructorType {
        EMPTY,
        LOADS,
        DATA
    }

    public enum SetType {
        OPEN,
        CLOSED
    }

    private SetConstructorType type;
    private float lowerLoadFactor;
    private float upperLoadFactor;
    private String[] data;

    public SetConstructor(String line) throws Exception {
        String[] parts = line.split(" ");

        if(line.equals("")) {
            type = SetConstructorType.EMPTY;
        } else if(parts[0].equals("A:")) {
            type = SetConstructorType.DATA;
            data = new String[parts.length - 1];
            for(int i = 0; i < data.length; i++) {
                data[i] = parts[i + 1];
            }
        } else if(parts[0].equals("N:")) {
            type = SetConstructorType.LOADS;
            upperLoadFactor = Float.parseFloat(parts[1]);
            lowerLoadFactor = Float.parseFloat(parts[2]);
        } else {
            throw new Exception("Couldn't parse line " + line);
        }
    }

    public SimpleHashSet construct(SetType setType) throws Exception {
        switch(type) {
            case EMPTY:
                if (setType == SetType.OPEN) {
                    return new OpenHashSet();
                } else {
                    return new ClosedHashSet();
                }

            case LOADS:
                if (setType == SetType.OPEN) {
                    return new OpenHashSet(upperLoadFactor, lowerLoadFactor);
                } else {
                    return new ClosedHashSet(upperLoadFactor, lowerLoadFactor);
                }

            case DATA:
                if (setType == SetType.OPEN) {
                    return new OpenHashSet(data);
                } else {
                    return new ClosedHashSet(data);
                }

            default:
                throw new Exception();
        }
    }
}
