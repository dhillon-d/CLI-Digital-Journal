package p1;

import static org.junit.Assert.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import org.junit.Before;
import org.junit.Test;

public class EntryManagerTest {

  EntryManager m;
  HashMap<String, String> existing = new HashMap<>();
  HashMap<String, String> existing2 = new HashMap<>();
  HashMap<String, String> existing3 = new HashMap<>();
  HashMap<String, String> existing4 = new HashMap<>();
  HashMap<String, String> cli = new HashMap<>();
  ArrayList<Map<String, String>> a = new ArrayList<>();

  /*
  to test 'write' method, relies on 'test.csv' generated in base folder
   */

  @Before
  public void setUp() throws Exception {
    m = new EntryManager();
    cli.put("--csv-file", "entry_unit_test.csv");
    cli.put("--add-entry", null);
    cli.put("--entry-text", "new entry :)");
    cli.put("--category", "my category");
    cli.put("--date", "11/11/1111");

    existing.put("id", "1");
    existing.put("text", "first");
    existing.put("completed", "false");
    existing.put("date", "11/11/1111");
    existing.put("priority", "1");
    existing.put("category", "first entry - will get completed set to true");

    existing2.put("id", "2");
    existing2.put("text", "second");
    existing2.put("completed", "false");
    existing2.put("date", "11/11/1111");
    existing2.put("priority", "2");
    existing2.put("category", "second - will not get completed set to true");

    existing3.put("id", "3");
    existing3.put("text", "third");
    existing3.put("completed", "false");
    existing3.put("date", "11/11/1111");
    existing3.put("priority", "3");
    existing3.put("category", "third - will get completed set to true");

    existing4.put("id", "4");
    existing4.put("text", "fourth");
    existing4.put("completed", "false");
    existing4.put("date", "11/11/1111");
    existing4.put("priority", "3");
    existing4.put("category", "fourth - will not get completed set to true");

    a.add(existing);
    a.add(existing2);
    a.add(existing3);
    a.add(existing4);
  }

  @Test
  public void isEntries() {
    assertTrue(m.isEntries() == false);
  }

  @Test(expected = BadFormatException.class)
  public void badAdd()
      throws EntryNotFoundException, MapsNotSetException, BadFormatException {
    cli.put("--priority", "4");
    m.processMaps(cli, a);
  }

  @Test(expected = BadFormatException.class)
  public void badAdd2()
      throws EntryNotFoundException, MapsNotSetException, BadFormatException {
    cli.put("--priority", "0");
    m.processMaps(cli, a);
  }

  @Test
  public void processEntries()
      throws EntryNotFoundException, MapsNotSetException, BadFormatException {
    assertTrue(m.processMaps(cli, a).size() == 5);
  }

  @Test
  public void completeEntries()
      throws EntryNotFoundException, MapsNotSetException, BadFormatException {
    cli.put("--complete-entry", "1");
    assertTrue(m.processMaps(cli, a).get(0).get("completed").equals("true"));
  }

  @Test(expected = EntryNotFoundException.class)
  public void badCompleteEntries()
      throws EntryNotFoundException, MapsNotSetException, BadFormatException {
    // no ID 10
    cli.put("--complete-entry", "10");
    m.processMaps(cli, a);
  }

  @Test(expected = MapsNotSetException.class)
  public void badSetMaps() throws EntryNotFoundException, MapsNotSetException, BadFormatException {
    m.processMaps(null, null);
  }

  @Test
  public void write()
      throws MapsNotSetException, EntryNotFoundException, IOException, BadFormatException {
    cli.put("--complete-entry", "1, 3");
    m.processMaps(cli, a);
    m.executeStrategy(new WriteCSV());
  }

  @Test(expected = BadFormatException.class)
  public void badWriteExtension()
      throws MapsNotSetException, EntryNotFoundException, IOException, BadFormatException {
    cli.put("--csv-file", "test.txt");
    m.processMaps(cli, a);
    m.executeStrategy(new WriteCSV());
  }

  @Test(expected = MapsNotSetException.class)
  public void badWrite() throws MapsNotSetException, IOException, BadFormatException {
    m.executeStrategy(new WriteCSV());
  }

  @Test
  public void equals() throws MapsNotSetException, EntryNotFoundException, BadFormatException {
    assertTrue(m.equals(new EntryManager()));
    assertFalse(m.equals(null));
  }
}