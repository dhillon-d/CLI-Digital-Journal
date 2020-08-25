package p1;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.junit.Assert.*;

public class DisplayDigitalEntriesTest {
    private DisplayDigitalEntries displayDE;
    private HashMap<String, String> input;
    private HashMap<String, String> input1;
    private HashMap<String, String> input2;
    private HashMap<String, String> input3;
    private HashMap<String, String> input4;
    private HashMap<String, String> input5;
    private HashMap<String, String> input6;
    private HashMap<String, String> input7;

    private ArrayList<Map<String, String>> dE;
    private Map<String, String> d1;
    private Map<String, String> d2;
    private Map<String, String> d3;
    private Map<String, String> d4;
    private Map<String, String> d5;
    private Map<String, String> d6;


    private DisplayDigitalEntries posn;
    private DisplayDigitalEntries posn2;
    private DisplayDigitalEntries posnX;
    private DisplayDigitalEntries posnY;
    private DisplayDigitalEntries posnZ;

    @Before
    public void setUp() throws Exception {
        input = new HashMap<>();
        input.put("--display", null);

        input1 = new HashMap<>();
        input1.put("--display", null);
        input1.put("--show-incomplete", null);

        input2 = new HashMap<>();
        input2.put("--display", null);
        input2.put("--show-incomplete", null);

        input3 = new HashMap<>();
        input3.put("--display", null);
        input3.put("--show-category", "study");

        input4 = new HashMap<>();
        input4.put("--display", null);
        input4.put("--show-incomplete", null);
        input4.put("--show-category", "study");

        input5 = new HashMap<>();
        input5.put("--display", null);
        input5.put("--sort-by-date", null);

        input6 = new HashMap<>();
        input6.put("--display", null);
        input6.put("--sort-by-priority", null);

        input7 = new HashMap<>();
        input7.put("test", null);

        d1 = new HashMap<String, String>() {{
            put("text","test1");
            put("completed", "true");
            put("date", "01/01/2020");
            put("priority", "5");
            put("category", "seattle");
        }};

        d2 = new HashMap<String, String>() {{
            put("text","test2");
            put("completed", "true");
            put("date", "01/02/2020");
            put("priority", "4");
            put("category", "work");
        }};

        d3 = new HashMap<String, String>() {{
            put("text","test3");
            put("completed", "false");
            put("date", "01/03/2020");
            put("priority", "1");
            put("category", "work");
        }};

        d4 = new HashMap<String, String>() {{
            put("text","test4");
            put("completed", "false");
            put("date", "01/04/2020");
            put("priority", "1");
        }};

        d5 = new HashMap<String, String>() {{
            put("text","test4");
            put("completed", "false");
            put("date", "01/04/2020");
            put("priority", "1");
            put("category", "study");
        }};

        d5 = new HashMap<String, String>() {{
            put("text","test4");
            put("completed", "false");
            put("date", "01/04/2020");
            put("priority", "1");
            put("category", "study");
        }};

        d6 = new HashMap<String, String>() {{
            put("text","test5");
            put("completed", "true");
            put("date", "01/04/2020");
            put("priority", "1");
            put("category", "study");
        }};

        dE = new ArrayList<Map<String, String>>(){{
            add(d1);
            add(d2);
            add(d3);
            add(d4);
            add(d5);
            add(d6);
        }};

        posn = new DisplayDigitalEntries(input, dE);
        posn2 = new DisplayDigitalEntries(input, dE);
        posnX = new DisplayDigitalEntries(input2, dE);
        posnY = new DisplayDigitalEntries(input2, dE);
        posnZ = new DisplayDigitalEntries(input2, dE);

    }

    @Test
    public void getFilePath() {
        displayDE = new DisplayDigitalEntries(input, dE);
        Assert.assertTrue(displayDE.getFilePath().equals(input));
    }

    @Test
    public void getDigitalEntry() {
        displayDE = new DisplayDigitalEntries(input, dE);
        Assert.assertTrue(displayDE.getDigitalEntry().equals(dE));
    }

    @Test
    public void display1 () throws InvalidDisplayException {
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        displayDE = new DisplayDigitalEntries(input, dE);
        displayDE.display();

        String expected = "\n" +
                "The digital entry values are: \n" +
                "{date=01/01/2020, text=test1, completed=true, priority=5, category=seattle}\n" +
                "{date=01/02/2020, text=test2, completed=true, priority=4, category=work}\n" +
                "{date=01/03/2020, text=test3, completed=false, priority=1, category=work}\n" +
                "{date=01/04/2020, text=test4, completed=false, priority=1}\n" +
                "{date=01/04/2020, text=test4, completed=false, priority=1, category=study}\n" +
                "{date=01/04/2020, text=test5, completed=true, priority=1, category=study}" +
                "\n";

        assertEquals(expected, outContent.toString());
    }

    @Test
    public void display2 () throws InvalidDisplayException {
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        displayDE = new DisplayDigitalEntries(input2, dE);
        displayDE.display();

        String expected = "\n" +
                "The digital entry values are: \n" +
                "{date=01/03/2020, text=test3, completed=false, priority=1, category=work}\n" +
                "{date=01/04/2020, text=test4, completed=false, priority=1}\n" +
                "{date=01/04/2020, text=test4, completed=false, priority=1, category=study}\n";

        assertEquals(expected, outContent.toString());
    }

    //@Test (expected = Exception.class)
    @Test
    public void display3 () throws InvalidDisplayException {

        displayDE = new DisplayDigitalEntries(input7, dE);
        displayDE.display();

    }

    @Test
    public void displayWithOption1() throws InvalidDisplayException{
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        displayDE = new DisplayDigitalEntries(input1, dE);
        displayDE.displayWithOption();

        String expected = "\n" +
                "The digital entry values are: \n" +
                "{date=01/03/2020, text=test3, completed=false, priority=1, category=work}\n" +
                "{date=01/04/2020, text=test4, completed=false, priority=1}\n" +
                "{date=01/04/2020, text=test4, completed=false, priority=1, category=study}\n";

        assertEquals(expected, outContent.toString());
    }

    @Test
    public void displayWithOption2() throws InvalidDisplayException {
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        displayDE = new DisplayDigitalEntries(input3, dE);
        displayDE.displayWithOption();

        String expected = "\n" +
                "The digital entry values are: \n" +
                "{date=01/04/2020, text=test4, completed=false, priority=1, category=study}\n" +
                "{date=01/04/2020, text=test5, completed=true, priority=1, category=study}\n";

        assertEquals(expected, outContent.toString());
    }

    @Test
    public void displayWithOption3() throws InvalidDisplayException{
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        displayDE = new DisplayDigitalEntries(input4, dE);
        displayDE.displayWithOption();

        String expected = "\n" +
                "The digital entry values are: \n" +
                "{date=01/04/2020, text=test4, completed=false, priority=1, category=study}\n";

        assertEquals(expected, outContent.toString());

    }

    @Test
    public void displayWithOption4() throws InvalidDisplayException {
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        displayDE = new DisplayDigitalEntries(input5, dE);
        displayDE.displayWithOption();

        String expected = "\n" +
                "The digital entry values are: \n" +
                "{date=01/01/2020, text=test1, completed=true, priority=5, category=seattle}\n" +
                "{date=01/02/2020, text=test2, completed=true, priority=4, category=work}\n" +
                "{date=01/03/2020, text=test3, completed=false, priority=1, category=work}\n" +
                "{date=01/04/2020, text=test4, completed=false, priority=1}\n" +
                "{date=01/04/2020, text=test4, completed=false, priority=1, category=study}\n" +
                "{date=01/04/2020, text=test5, completed=true, priority=1, category=study}\n";

        assertEquals(expected, outContent.toString());
    }

    @Test
    public void displayWithOption5() throws InvalidDisplayException{
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        displayDE = new DisplayDigitalEntries(input6, dE);
        displayDE.displayWithOption();

        String expected = "\n" +
                "The digital entry values are: \n" +
                "{date=01/03/2020, text=test3, completed=false, priority=1, category=work}\n" +
                "{date=01/04/2020, text=test4, completed=false, priority=1}\n" +
                "{date=01/04/2020, text=test4, completed=false, priority=1, category=study}\n" +
                "{date=01/04/2020, text=test5, completed=true, priority=1, category=study}\n" +
                "{date=01/02/2020, text=test2, completed=true, priority=4, category=work}\n" +
                "{date=01/01/2020, text=test1, completed=true, priority=5, category=seattle}\n";

        assertEquals(expected, outContent.toString());
    }

    @Test (expected = Exception.class)
    public void displayWithOption6() throws InvalidDisplayException{
            ArrayList<Map<String, String>> newDE = new ArrayList<Map<String, String>>(){{
                add(d1);
                add(d2);
            }};
            displayDE = new DisplayDigitalEntries(input2, newDE);

            displayDE.displayWithOption();
        }

    @Test
    public void displayWithoutOption1() throws InvalidDisplayException{
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        displayDE = new DisplayDigitalEntries(input6, dE);
        displayDE.displayWithoutOptions();

        String expected = "\n" +
                "The digital entry values are: \n" +
                "{date=01/01/2020, text=test1, completed=true, priority=5, category=seattle}\n" +
                "{date=01/02/2020, text=test2, completed=true, priority=4, category=work}\n" +
                "{date=01/03/2020, text=test3, completed=false, priority=1, category=work}\n" +
                "{date=01/04/2020, text=test4, completed=false, priority=1}\n" +
                "{date=01/04/2020, text=test4, completed=false, priority=1, category=study}\n" +
                "{date=01/04/2020, text=test5, completed=true, priority=1, category=study}\n";

        assertEquals(expected, outContent.toString());
    }

    @Test (expected = Exception.class)
    public void displayWithoutOption2() throws InvalidDisplayException{
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        displayDE = new DisplayDigitalEntries(input3, new ArrayList<Map<String, String>>());
        displayDE.displayWithoutOptions();
    }

    @Test
    public void showIncomplete() throws InvalidDisplayException {
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        displayDE = new DisplayDigitalEntries(input2, dE);

        String expected = "\n" +
                "The digital entry values are: \n" +
                "{date=01/03/2020, text=test3, completed=false, priority=1, category=work}\n" +
                "{date=01/04/2020, text=test4, completed=false, priority=1}\n" +
                "{date=01/04/2020, text=test4, completed=false, priority=1, category=study}\n";

        displayDE.showIncomplete();

        assertEquals(expected, outContent.toString());
    }

    @Test (expected = Exception.class)
    public void showIncomplete1() throws InvalidDisplayException {
        ArrayList<Map<String, String>> newDE = new ArrayList<Map<String, String>>(){{
            add(d1);
            add(d2);
        }};
        displayDE = new DisplayDigitalEntries(input2, newDE);

        displayDE.showIncomplete();
    }

    @Test
    public void showCategory() throws InvalidDisplayException {
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        displayDE = new DisplayDigitalEntries(input3, dE);

        String expected = "\n" +
                "The digital entry values are: \n" +
                "{date=01/04/2020, text=test4, completed=false, priority=1, category=study}\n" +
                "{date=01/04/2020, text=test5, completed=true, priority=1, category=study}" +
                "\n";

        displayDE.showCategory();

        assertEquals(expected, outContent.toString());
    }

    //category is null -- test
    @Test
    public void showCategory1() throws InvalidDisplayException {
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        displayDE = new DisplayDigitalEntries(input2, dE);

        displayDE.showCategory();

        String expected = "\n" +
                "The digital entry values are: \n" +
                "{date=01/04/2020, text=test4, completed=false, priority=1}"+
                "\n";

        assertEquals(expected, outContent.toString());
    }

    @Test (expected = Exception.class)
    public void showCategory2() throws InvalidDisplayException {
        ArrayList<Map<String,String>> newDE = new ArrayList<Map<String, String>>(){{
            add(d1);
            add(d2);
        }};

        displayDE = new DisplayDigitalEntries(input2, newDE);

        displayDE.showCategory();
    }

    @Test
    public void showIncompleteAndCategory() throws InvalidDisplayException {
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        displayDE = new DisplayDigitalEntries(input4, dE);

        String expected = "\n" +
                "The digital entry values are: \n" +
                "{date=01/04/2020, text=test4, completed=false, priority=1, category=study}" +
                "\n";

        displayDE.showIncompleteAndCategory();

        assertEquals(expected, outContent.toString());
    }


    @Test (expected = Exception.class)
    public void showIncompleteAndCategory1() throws InvalidDisplayException {
        ArrayList<Map<String,String>> newDE = new ArrayList<Map<String, String>>(){{
            add(d1);
            add(d2);
        }};

        displayDE = new DisplayDigitalEntries(input4, newDE);

        displayDE.showIncompleteAndCategory();
    }

    @Test
    public void testSortByDate(){
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        displayDE = new DisplayDigitalEntries(input4, dE);

        displayDE.sortByDate();

        String expected = "\n" +
                "The digital entry values are: \n" +
                "{date=01/01/2020, text=test1, completed=true, priority=5, category=seattle}\n" +
                "{date=01/02/2020, text=test2, completed=true, priority=4, category=work}\n" +
                "{date=01/03/2020, text=test3, completed=false, priority=1, category=work}\n" +
                "{date=01/04/2020, text=test4, completed=false, priority=1}\n" +
                "{date=01/04/2020, text=test4, completed=false, priority=1, category=study}\n" +
                "{date=01/04/2020, text=test5, completed=true, priority=1, category=study}\n" ;

        assertEquals(expected, outContent.toString());
    }

    @Test
    public void testSortByPriority(){
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        displayDE = new DisplayDigitalEntries(input4, dE);

        displayDE.sortByPriority();

        String expected = "\n" +
                "The digital entry values are: \n" +
                "{date=01/03/2020, text=test3, completed=false, priority=1, category=work}\n" +
                "{date=01/04/2020, text=test4, completed=false, priority=1}\n" +
                "{date=01/04/2020, text=test4, completed=false, priority=1, category=study}\n" +
                "{date=01/04/2020, text=test5, completed=true, priority=1, category=study}\n" +
                "{date=01/02/2020, text=test2, completed=true, priority=4, category=work}\n" +
                "{date=01/01/2020, text=test1, completed=true, priority=5, category=seattle}\n";

        assertEquals(expected, outContent.toString());
    }

    @Test
    public void testReflexive() {
        assertTrue(posn.equals(posn));
    }

    //a.eqauls(b), b.equals(a)
    @Test
    public void testSymmetric() {
        assertTrue(posnX.equals(posnY) && posnY.equals(posnX));
    }

    //a.equals(b), b.equals(c)
    @Test
    public void testTransmissive() {
        assertTrue(posnX.equals(posnY) && posnY.equals(posnZ) && posnX.equals(posnZ));
    }

    @Test
    public void testConsistent() {
        for (int i = 0; i < 10; i++) {
            assertTrue(posnX.equals(posnZ));
        }
    }

    @Test
    public void testIsNull() {
        assertFalse(posn.equals(null));
    }


    @Test
    public void testIsClass() {
        assertFalse(posn2.equals(new Object()));
    }

    @Test
    public void testDifference() {
        assertFalse(posn.equals(posnX));
    }


    @Test
    public void testHashCode() {

        assertEquals("These should be equal", posn, posn2);
        int oneCode = posn.hashCode();
        assertEquals("HashCodes should be equal", oneCode, posn2.hashCode());
        assertEquals("HashCode should not change", oneCode, posn.hashCode());

        assertEquals(posn.equals(posn2), posn.hashCode() == posn2.hashCode());

        assertEquals(posn.equals(posnX), posnX.hashCode() == posn.hashCode());

    }

    @Test
    public void testtoString() {
        String expected = "DisplayDigitalEntries{filePath={--display=null}, digitalEntry=[{date=01/01/2020, text=test1, completed=true, priority=5, category=seattle}, {date=01/02/2020, text=test2, completed=true, priority=4, category=work}, {date=01/03/2020, text=test3, completed=false, priority=1, category=work}, {date=01/04/2020, text=test4, completed=false, priority=1}, {date=01/04/2020, text=test4, completed=false, priority=1, category=study}, {date=01/04/2020, text=test5, completed=true, priority=1, category=study}]}";
        assertTrue(posn.toString().equals(expected));
        //System.out.println(posn.toString());
    }

}