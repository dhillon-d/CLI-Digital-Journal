package p1;

import java.io.File;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

/**
 * Command line argument parser, puts arguments into a hashmap.
 */
public class CLIParser {

  private Map<String, String> map;
  private String[] args;
  private static final Set<String> FLAGS_FOLLOWED_BY_STRING = new HashSet<>();
  private static final Set<String> FLAGS_ALL = new HashSet<>();

  /**
   * Constructor expecting array of input arguments, calls parseArgs to parse array into hashmap.
   * @param args command line arguments
   * @throws RequiredArgMissingException required arguments missing exception
   * @throws PathNotFoundException path no found exception
   * @throws IllegalCombinationException illegal combination of inputs exception
   */
  public CLIParser(String[] args)
      throws RequiredArgMissingException, PathNotFoundException, IllegalCombinationException {
    this.args = args;
    this.populateSets();
    this.parseArgs();
  }

  /**
   * Private helper that populates the sets of legal commands.
   */
  private void populateSets() {
    FLAGS_FOLLOWED_BY_STRING.add("--csv-file");
    FLAGS_FOLLOWED_BY_STRING.add("--entry-text");
    FLAGS_FOLLOWED_BY_STRING.add("--date");
    FLAGS_FOLLOWED_BY_STRING.add("--priority");
    FLAGS_FOLLOWED_BY_STRING.add("--category");
    FLAGS_FOLLOWED_BY_STRING.add("--complete-entry");
    FLAGS_FOLLOWED_BY_STRING.add("--show-category");
    FLAGS_ALL.add("--csv-file");
    FLAGS_ALL.add("--add-entry");
    FLAGS_ALL.add("--entry-text");
    FLAGS_ALL.add("--completed");
    FLAGS_ALL.add("--date");
    FLAGS_ALL.add("--priority");
    FLAGS_ALL.add("--category");
    FLAGS_ALL.add("--complete-entry");
    FLAGS_ALL.add("--display");
    FLAGS_ALL.add("--show-incomplete");
    FLAGS_ALL.add("--show-category");
    FLAGS_ALL.add("--sort-by-date");
    FLAGS_ALL.add("--sort-by-priority");
  }

  /**
   * Parses command line arguments into hashmap, checks for correct input formatting.
   * @throws RequiredArgMissingException required arguments missing exception
   * @throws PathNotFoundException path not found exception
   * @throws IllegalCombinationException illegal combination of inputs exception
   */
  private void parseArgs() throws RequiredArgMissingException, PathNotFoundException,
      IllegalCombinationException {

    // Check required arguments exist, specified paths exist, and legal combination of inputs.
    this.checkRequiredArgs();
    this.checkPathsExist();
    this.checkCombination();

    // Create, populate and set map field
    Map<String, String> tempMap = new HashMap<>();
    for (int i = 0; i < this.args.length; i++) {
      // Concat value of --complete-entry if a value already exists, otherwise populate map normal
      if (this.args[i].matches("--complete-entry") &&
          tempMap.containsKey("--complete-entry")) {
        tempMap.put(this.args[i], tempMap.get(this.args[i]).concat(", " + this.args[i + 1]));
      } else if (FLAGS_FOLLOWED_BY_STRING.contains(this.args[i])) {
        tempMap.put(this.args[i], this.args[i +1]);
      } else if (this.args[i].matches("--.*")) {
        tempMap.put(this.args[i], null);
      }
    }
    this.map = tempMap;
  }

  /**
   * Private helper checks for required csv flag.
   * @throws RequiredArgMissingException required arguments missing exception
   */
  private void checkRequiredArgs() throws RequiredArgMissingException {
    // Check for required flags in args
    boolean requiredCSVFlag = false;
    boolean addEntryFlag = false;
    boolean dateFlag = false;
    boolean textFlag = false;
    for (String arg : this.args) {
      if (arg.equals("--csv-file")) {
        requiredCSVFlag = true;
      } else if (arg.equals("--add-entry")) {
        addEntryFlag = true;
      } else if (arg.equals("--date")) {
        dateFlag = true;
      } else if (arg.equals("--entry-text")) {
        textFlag = true;
      }
    }
    // If required flags conditions not met, throw exception
    if (!requiredCSVFlag) {
      throw new RequiredArgMissingException();
    }
    if (addEntryFlag && (!dateFlag || !textFlag)) {
      throw new RequiredArgMissingException();
    }
  }

  /**
   * Private helper checks for existence of specified paths.
   * @throws PathNotFoundException path not found exception
   */
  private void checkPathsExist() throws PathNotFoundException {
    // If path specified doesn't exist, throw exception
    for (int i = 0; i < this.args.length; i++) {
      if (this.args[i].matches("--csv-file")) {
        File file = new File(this.args[i + 1]);
        if (!file.exists()) {
          throw new PathNotFoundException();
        }
      }
    }
  }

  /**
   * Private helper checks spelling of command line flags and combination of inputs is legal.
   * @throws IllegalCombinationException template not specified exception
   */
  private void checkCombination() throws IllegalCombinationException {
    // Spell check of command line inputs
    for (String arg : this.args) {
      if (arg.matches("--.*") && !FLAGS_ALL.contains(arg)) {
        throw new IllegalCombinationException();
      }
    }
    // If string not specified after particular flags, throw exception
    for (int i = 0; i < this.args.length - 1; i++) {
      if (FLAGS_FOLLOWED_BY_STRING.contains(this.args[i]) &&
          this.args[i + 1].matches("--.*")) {
        throw new IllegalCombinationException();
      }
    }
    // Check last command line input, separately, to avoid out of range index error
    if (FLAGS_FOLLOWED_BY_STRING.contains(this.args[this.args.length - 1])) {
      throw new IllegalCombinationException();
    }
  }

  /**
   * Return hashmap.
   * @return hashmap
   */
  public Map<String, String> getMap() {
    return this.map;
  }

  /**
   * Override equals.
   * @param o input object
   * @return t or f
   */
  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    CLIParser cliParser = (CLIParser) o;
    return Objects.equals(map, cliParser.map) &&
        Arrays.equals(args, cliParser.args);
  }

  /**
   * Override hashcode.
   * @return int hashcode
   */
  @Override
  public int hashCode() {
    int result = Objects.hash(map);
    result = 31 * result + Arrays.hashCode(args);
    return result;
  }

  /**
   * Override to string.
   * @return string
   */
  @Override
  public String toString() {
    return "CLIParser{" +
        "map=" + map +
        ", args=" + Arrays.toString(args) +
        '}';
  }
}
