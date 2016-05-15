package fileprocessing.Components.orders;

/**
 * Created by jenia on 14/05/2016.
 */
public class OrderFactory {
    public static Order getOrder(String orderBy, boolean isReverse){
        switch (orderBy){
            case "abs":
                return new OrderByName(isReverse);
            case "size":
                return new OrderBySize(isReverse);
            case "type":
                return new OrderByType(isReverse);
        }

        return null;
    }
}
