import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * Created by Yossi on 27/03/2016.
 */
@RunWith(Parameterized.class)
public class SchoolProvidedTests {

    private final SchoolTest test;
    private final SetConstructor.SetType setType;

    @Parameterized.Parameters(name = "{4}: {1} ({2})")
    public static Iterable<Object[]> data() throws Exception {
        List<Object[]> lst = new ArrayList<>();
        for(int i = 1; i < 30; i++) {
            String filename = i < 10 ? "00" + i : "0" + i;
            SchoolTest schoolTest = new SchoolTest(SchoolProvidedTests.class.getResourceAsStream(filename + ".txt"));
            lst.add(new Object[] { schoolTest, filename, schoolTest.getTitle(),
                    SetConstructor.SetType.OPEN, "OpenHashSet" });
            lst.add(new Object[] { schoolTest, filename, schoolTest.getTitle(),
                    SetConstructor.SetType.CLOSED, "ClosedHashSet" });
        }

        return lst;
    }

    public SchoolProvidedTests(SchoolTest test, String filename, String title,
                               SetConstructor.SetType setType, String setTypeName) {
        this.test = test;
        this.setType = setType;
    }

    @Test
    public void runSchoolTests() throws Exception {
        test.run(SetConstructor.SetType.OPEN);
        test.run(SetConstructor.SetType.CLOSED);
    }
}
