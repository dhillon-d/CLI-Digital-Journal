package p1;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * a write strategy that does not allow for new file creation
 */
public class WriteCSV implements IEntryStrategy {

  private static final String HEADER =
      "\"id\",\"text\",\"completed\",\"date\",\"priority\",\"category\"";

  /**
   * constructor
   */
  public WriteCSV() {
  }

  /**
   * use an entry manager's attributes to write entries
   *
   * @param args    the command line arguments to use
   * @param entries the list of entries
   * @throws IOException        if file handling results in an error
   * @throws BadFormatException if format of an entry attribute is incorrect
   */
  @Override
  public void execute(Map<String, String> args, List<Map<String, String>> entries)
      throws IOException, BadFormatException {
    String file = args.get("--csv-file");
    String eol = System.getProperty("line.separator");
    this.fileCheckCreate(file);

    try (BufferedWriter writer = new BufferedWriter
        (new FileWriter(file, false));) {

      // always write the header
      writer.write(HEADER);
      writer.append(eol);

      for (Map<String, String> m : entries) {
        writer.append("\"")
            .append(m.get("id"))
            .append("\"")
            .append(',')
            .append("\"")
            .append(m.get("text"))
            .append("\"")
            .append(',')
            .append("\"")
            .append(m.get("completed"))
            .append("\"")
            .append(',')
            .append("\"")
            .append(m.get("date"))
            .append("\"")
            .append(',')
            .append("\"")
            .append(m.get("priority"))
            .append("\"")
            .append(',')
            .append("\"")
            .append(m.get("category"))
            .append("\"")
            .append(eol);
      }
    } catch (IOException io) {
      io.printStackTrace();
      throw io;
    }
  }

  /**
   * checks for a file extension
   *
   * @param fileName the name of the file to test
   * @throws BadFormatException if file extension is incorrect
   */
  private void fileCheckCreate(String fileName) throws BadFormatException {
    // check for .csv
    String extension = fileName.substring(fileName.lastIndexOf(".") + 1, fileName.length());
    if (!extension.equals("csv")) {
      throw new BadFormatException();
    }
  }

  @Override
  public String toString() {
    return "EntryVisitor{}";
  }
}