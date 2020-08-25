package p1;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;

public class CSVParserTest {

    private HashMap<String,String> input = new HashMap<String, String>(){{
        put("test", "test.txt");
        put("--csv-file", "csv_unit_test.csv");
    }};
    private ArrayList<Map<String, String>> result = new ArrayList<>();
    private Map<String, String> line1;
    private Map<String, String> line2;

    private HashMap<String,String> input2;
//    private CSVParser posn;
//    private CSVParser posn2;
//    private CSVParser posnX;
//    private CSVParser posnY;
//    private CSVParser posnZ;

    private CSVParser csvParser;
    //because it is static, so no need for new CSVParser.getInstance();;

    @Before
    public void setUp() throws Exception {

        line1 = new HashMap<String, String>();
        line1.put("first_name", "James");
        line1.put("last_name", "Butt");
        line1.put("company_name", "Benton, John B Jr");
        line2 = new HashMap<String, String>();
        line2.put("first_name", "Josephine");
        line2.put("last_name", "Darakjy");
        line2.put("company_name", "Chanay, Jeffrey A Esq");

        result.add(line1);
        result.add(line2);

        //csvParser1 = CSVParser.getInstance();

        input2 = new HashMap<String,String>();
        input2.put("test", "test.txt");
        input2.put("--csv-file", "text1.csv");


//        posn2 = CSVParser.getInstance();
//        posnX = CSVParser.getInstance();
//        posnY = CSVParser.getInstance();
//        posnZ = CSVParser.getInstance();
    }


    @Test
    public void readCSVFile() throws IOException {
        CSVParser test = csvParser.getInstance();
      assertTrue(test.readCSVFile().equals(result));
    }

//    @Test (expected = Exception.class)
//    public void readCSVFile1() throws IOException {
//        csvParser1.readCSVFile();
//    }
//     due to singleton pattern, the different names of varaibles will refer to the same objet no matter what.

    @Test
    public void testReflexive() {
        CSVParser posn = csvParser.getInstance();
        assertTrue(posn.equals(posn));
    }

    //a.eqauls(b), b.equals(a)
    @Test
    public void testSymmetric() {
        CSVParser posnX = csvParser.getInstance();
        CSVParser posnY = csvParser.getInstance();

        assertTrue(posnX.equals(posnY) && posnY.equals(posnX));
    }

    //a.equals(b), b.equals(c)
    @Test
    public void testTransmissive() {
        CSVParser posnX = csvParser.getInstance();
        CSVParser posnY = csvParser.getInstance();
        CSVParser posnZ = csvParser.getInstance();

        assertTrue(posnX.equals(posnY) && posnY.equals(posnZ) && posnX.equals(posnZ));
    }

    @Test
    public void testConsistent() {
        csvParser = CSVParser.init(input);
        CSVParser posnX = csvParser.getInstance();
        CSVParser posnZ = csvParser.getInstance();

        for (int i = 0; i < 10; i++) {
            assertTrue(posnX.equals(posnZ));
        }
    }

    @Test
    public void testIsNull() {
        CSVParser posn = csvParser.getInstance();

        assertFalse(posn.equals(null));
    }


    @Test
    public void testIsClass() {
        CSVParser posn2 = csvParser.getInstance();

        assertFalse(posn2.equals(new Object()));
    }

//    @Test
//    public void testDifference() {
//        assertFalse(posn.equals(posnX));
//    }


    @Test
    public void testHashCode() {

        CSVParser posn = csvParser.getInstance();
        CSVParser posn2 = csvParser.getInstance();
        CSVParser posnX = csvParser.getInstance();

        assertEquals("These should be equal", posn, posn2);
        int oneCode = posn.hashCode();
        assertEquals("HashCodes should be equal", oneCode, posn2.hashCode());
        assertEquals("HashCode should not change", oneCode, posn.hashCode());

        assertEquals(posn.equals(posn2), posn.hashCode() == posn2.hashCode());

        assertEquals(posn.equals(posnX), posnX.hashCode() == posn.hashCode());

    }

    @Test
    public void testtoString() {
        CSVParser csv = csvParser.getInstance();
       Assert.assertTrue(csv.toString().equals("CVSParser{filePath={test=test.txt, --csv-file=csv_unit_test.csv}}"));
       //System.out.println(csvParser.toString());
    }
}