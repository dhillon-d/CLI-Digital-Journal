package p1;

/**
 * Path not found exception.
 */
public class PathNotFoundException extends Exception {

  public PathNotFoundException() {
    super("Error: Path after --csv-file not found\n"
        + "Example command line arguments:\n"
        + "--csv-file ./digital_journal.csv --add-entry --entry-text hello"
        + " --completed --date 1-2-2020 --priority 1 --category home"
        + " --complete-entry 1 --complete-entry 2 --display --show-incomplete"
        + " --show-category home --sort-by-date --sort-by-priority");
  }
}
