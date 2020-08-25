package p1;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

public class CLIParserTest {

  CLIParser parser, parser2, parser3;

  @Before
  public void setUp() throws Exception {
    String[] args = {"--csv-file", "digital_journal.csv", "--add-entry", "--entry-text", "hello",
        "--completed", "--date", "1-2-2020", "--priority", "1", "--category", "home",
        "--complete-entry", "1", "--complete-entry", "2", "--display", "--show-incomplete",
        "--show-category", "home", "--sort-by-date", "--sort-by-priority"};

    String[] args3 = {"--csv-file", "digital_journal.csv", "--add-entry", "--entry-text", "Bob",
        "--completed", "--date", "1-2-2020", "--priority", "1", "--category", "home",
        "--complete-entry", "1", "--complete-entry", "2", "--display", "--show-incomplete",
        "--show-category", "home", "--sort-by-date", "--sort-by-priority"};
    parser = new CLIParser(args);
    parser2 = new CLIParser(args);
    parser3 = new CLIParser(args3);
  }

  @Test
  public void getMap() {
    parser.getMap();
  }

  @Test (expected = RequiredArgMissingException.class)
  public void testRequiredArgMissing()
      throws RequiredArgMissingException, PathNotFoundException, IllegalCombinationException {
    String[] args = {"hello"};
    parser = new CLIParser(args);
  }

  @Test (expected = RequiredArgMissingException.class)
  public void testRequiredArgMissing2()
      throws RequiredArgMissingException, PathNotFoundException, IllegalCombinationException {
    String[] args = {"--csv-file", "digital_journal.csv", "--add-entry", "--entry-text",
    "hello"};
    parser = new CLIParser(args);
  }

  @Test (expected = RequiredArgMissingException.class)
  public void testRequiredArgMissing3()
      throws RequiredArgMissingException, PathNotFoundException, IllegalCombinationException {
    String[] args = {"--csv-file", "digital_journal.csv", "--add-entry", "--date",
        "1-1-2020"};
    parser = new CLIParser(args);
  }

  @Test (expected = PathNotFoundException.class)
  public void testPathNotFound()
      throws RequiredArgMissingException, PathNotFoundException, IllegalCombinationException {
    String[] args = {"--date", "1-2-2020", "--csv-file", "./bogus.csv"};
    parser = new CLIParser(args);
  }

  @Test (expected = IllegalCombinationException.class)
  public void testTemplateNotSpecified1()
      throws RequiredArgMissingException, PathNotFoundException, IllegalCombinationException {
    String[] args = {"--csv-file", "./digital_journal.csv", "--non-existent", "--date", "1-2-2020"};
    parser = new CLIParser(args);
  }

  @Test (expected = IllegalCombinationException.class)
  public void testTemplateNotSpecified2()
      throws RequiredArgMissingException, PathNotFoundException, IllegalCombinationException {
    String[] args = {"--csv-file", "./digital_journal.csv", "--entry-text",
        "--completed", "--date", "1-2-2020"};
    parser = new CLIParser(args);
  }

  @Test (expected = IllegalCombinationException.class)
  public void testTemplateNotSpecified3()
      throws RequiredArgMissingException, PathNotFoundException, IllegalCombinationException {
    String[] args = {"--date", "1-2-2020", "--csv-file", "./digital_journal.csv", "--entry-text"};
    parser = new CLIParser(args);
  }

  @Test
  public void testEquals() {
    // Make first 2 objects have identical fields
    assertTrue(this.parser.equals(this.parser));
    assertTrue(this.parser.equals(this.parser2));
    // Null check and instantiated from same class
    assertFalse(this.parser.equals(null));
    assertFalse(this.parser.equals("Hello"));
    // Start comparing different individual attribute values
    assertFalse(this.parser.equals(this.parser3));
  }

  @Test
  public void testHashcode() {
    assertEquals(this.parser.hashCode(), this.parser.hashCode());
  }

  @Test
  public void testToString() {
    assertEquals("CLIParser{map={--entry-text=hello, --show-incomplete=null,"
        + " --show-category=home, --display=null, --category=home, --sort-by-priority=null,"
        + " --csv-file=digital_journal.csv, --completed=null, --sort-by-date=null,"
        + " --add-entry=null, --priority=1, --complete-entry=1, 2, --date=1-2-2020},"
        + " args=[--csv-file, digital_journal.csv, --add-entry, --entry-text, hello,"
        + " --completed, --date, 1-2-2020, --priority, 1, --category, home, --complete-entry, 1,"
        + " --complete-entry, 2, --display, --show-incomplete, --show-category, home,"
        + " --sort-by-date, --sort-by-priority]}", parser.toString());
  }
}