package p1;

/**
 * Required arguments missing exception.
 */
public class RequiredArgMissingException extends Exception {

  public RequiredArgMissingException() {
    super("Error: --csv-file, --entry-text, or --date missing.\n"
        + "Example command line arguments:\n"
        + "--csv-file ./digital_journal.csv --add-entry --entry-text hello"
        + " --completed --date 1-2-2020 --priority 1 --category home"
        + " --complete-entry 1 --complete-entry 2 --display --show-incomplete"
        + " --show-category home --sort-by-date --sort-by-priority");
  }
}
