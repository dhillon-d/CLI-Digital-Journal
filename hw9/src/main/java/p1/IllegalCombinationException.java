package p1;

/**
 * Illegal combination of inputs exception.
 */
public class IllegalCombinationException extends Exception {

  public IllegalCombinationException() {
    super("Error: Typo, missing arguments, or illegal combination of inputs\n"
        + "Example command line arguments:\n"
        + "--csv-file ./digital_journal.csv --add-entry --entry-text hello"
        + " --completed --date 1-2-2020 --priority 1 --category home"
        + " --complete-entry 1 --complete-entry 2 --display --show-incomplete"
        + " --show-category home --sort-by-date --sort-by-priority");
  }
}
