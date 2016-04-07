import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Created by Yossi on 27/03/2016.
 */
public class SchoolTest {

    private String title;
    private SetConstructor constructor;
    private List<SetAction> actions = new ArrayList<>();

    public SchoolTest(InputStream inputStream) throws Exception {
        Scanner scanner = new Scanner(inputStream);

        String commentLine = scanner.nextLine();
        if(!commentLine.contains("#")) {
            throw new Exception("Line " + commentLine + " should start with a '#'");
        }

        this.title = commentLine.replace("#", "").trim();

        if(scanner.hasNextLine()) {
            String constructorLine = scanner.nextLine();
            this.constructor = new SetConstructor(constructorLine);

            while(scanner.hasNextLine()) {
                String actionLine = scanner.nextLine();
                if(!actionLine.equals("")) {
                    actions.add(new SetAction(actionLine));
                }
            }
        } else { // Workaround annoying test 001
            this.constructor = new SetConstructor("");
        }
    }

    public void run(SetConstructor.SetType setType) throws Exception {
        SimpleHashSet set;
        switch(setType) {
            case OPEN:
                set = constructor.construct(SetConstructor.SetType.OPEN);
                break;
            case CLOSED:
                set = constructor.construct(SetConstructor.SetType.CLOSED);
                break;
            default:
                throw new Exception();
        }

        for(SetAction action : actions) {
            action.test(set);
        }
    }

    public String getTitle() {
        return title;
    }
}
