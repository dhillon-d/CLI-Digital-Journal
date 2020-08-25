package p1;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * represents a strategy to be used on an entry manager object
 */
public interface IEntryStrategy {

  /**
   * executes a strategy
   *
   * @param args    the command line arguments to use
   * @param entries the list of entries
   * @throws IOException        if file handling error
   * @throws BadFormatException if format of an attribute is incorrect
   */
  void execute(Map<String, String> args, List<Map<String, String>> entries)
      throws IOException, BadFormatException;
}
