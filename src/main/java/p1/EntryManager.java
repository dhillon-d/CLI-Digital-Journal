package p1;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;


/**
 * represents logic to manage entries
 */
public class EntryManager implements IContext {

  private Map<String, String> clArgs = null;
  private ArrayList<Map<String, String>> entries = null;
  private static final int MAX_PRI = 3;
  private static final int LOWER = 1;

  /**
   * constructs a new manager object
   */
  public EntryManager() {
  }

  /**
   * sets the maps the class wil use and processes them. returns formatted maps
   *
   * @param clArgs  arg list represented as a map
   * @param entries list of entries to format
   * @return the formatted list of entries
   * @throws BadFormatException     if an argument is formatted incorrectly
   * @throws EntryNotFoundException if an attempt to modify a nonexistent entry
   * @throws MapsNotSetException    if attempt to operate on null
   */
  public ArrayList<Map<String, String>> processMaps(Map<String, String> clArgs,
      ArrayList<Map<String, String>> entries)
      throws BadFormatException, EntryNotFoundException, MapsNotSetException {
    // initial use check
    this.validateMaps(clArgs, entries);

    // route to methods
    if (this.clArgs.containsKey("--add-entry")) {
      this.addEntry();
    }
    if (this.clArgs.containsKey("--complete-entry")) {
      this.completeEntry();
    }
    return this.getEntries();
  }

  /**
   * helper method to handle input validation
   *
   * @param clArgs  the argument map to be processed
   * @param entries the list of entries to be processed
   * @throws MapsNotSetException if null arguments are attempted
   */
  private void validateMaps(Map<String, String> clArgs,
      ArrayList<Map<String, String>> entries) throws MapsNotSetException {
    if (clArgs == null) {
      throw new MapsNotSetException();
    }
    this.clArgs = clArgs;
    // allow null value to be passed in if the read file is empty
    this.entries = new ArrayList<>();
    if (entries != null) {
      // copy elements over. allows for passed list to remain unmodified
      this.entries.addAll(entries);
    }
  }

  /**
   * return final list of entries
   *
   * @return list of entries
   */
  public ArrayList<Map<String, String>> getEntries() {
    return this.entries;
  }

  /**
   * return the map of arguments
   *
   * @return map of arguments
   */
  public Map<String, String> getClArgs() {
    return this.clArgs;
  }

  /**
   * returns if any entries already exist
   *
   * @return true or false
   */
  public Boolean isEntries() {
    if (this.entries != null) {
      return (!this.entries.isEmpty());
    }
    return false;
  }

  /**
   * adds a new entry to the list of entries
   *
   * @throws BadFormatException if an attribute is formatted incorrectly
   */
  private void addEntry() throws BadFormatException {
    // run validators
    if (this.clArgs.containsKey("--priority")) {
      this.checkPriority(this.clArgs.get("--priority"));
    }
    this.checkDate(this.clArgs.get("--date"));

    Map<String, String> newEntry = new HashMap<String, String>() {
    };
    Integer newID;

    if (this.isEntries()) {
      // find id of last
      newID = Integer.parseInt(this.entries.get(this.entries.size() - 1).get("id"));
    } else {
      newID = 0;
    }
    newID++;

    newEntry.put("id", newID.toString());
    newEntry.put("text", this.clArgs.get("--entry-text"));
    if (this.clArgs.containsKey("--completed")) {
      newEntry.put("completed", "true");
    } else {
      newEntry.put("completed", "false");
    }
    // check for presence and assign placeholders accordingly
    // date format not specified - allows for broad format
    newEntry.put("date", this.clArgs.get("--date"));
    // 3 is default value
    newEntry.put("priority", this.clArgs.getOrDefault("--priority", "3"));
    newEntry.put("category", this.clArgs.getOrDefault("--category", "?"));
    this.entries.add(newEntry);
  }

  /**
   * checks if a date matches the correct format
   * @param date the string to check
   * @throws BadFormatException if the date doesn't match
   */
  private void checkDate(String date) throws BadFormatException {
    if (!date.matches("[0-1][0-9][\\/][0-3][0-9][\\/]\\d{4}")) {
      throw new BadFormatException();
    }
  }

  /**
   * checks for priority format
   * @param priority string representation of a priority
   * @throws BadFormatException if the format is incorrect
   */
  private void checkPriority(String priority) throws BadFormatException {
    int pri = Integer.parseInt(priority);
    if ((pri > MAX_PRI) || pri < LOWER) {
      throw new BadFormatException();
    }
  }

  /**
   * helper method to complete an entry
   */
  private void completeEntry() throws EntryNotFoundException {
    String[] toModify = this.clArgs.get("--complete-entry").split("[,]\\s");
    int count = 0;
    for (Map<String, String> e : this.entries) {
      for (String p : toModify) {
        if (e.get("id").equals(p)) {
          e.put("completed", "true");
          count++;
        }
      }
    }
    if (count != toModify.length) {
      throw new EntryNotFoundException();
    }
  }

  /**
   * executes a strategy on the class
   *
   * @throws MapsNotSetException if null exists where it shouldn't
   * @throws IOException         if file handling goes wrong
   * @throws BadFormatException  if an attribute is formatted incorrectly
   */
  @Override
  public void executeStrategy(IEntryStrategy s)
      throws MapsNotSetException, IOException, BadFormatException {
    if (this.clArgs == null || this.entries == null) {
      throw new MapsNotSetException();
    }
    s.execute(this.clArgs, this.entries);
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    EntryManager that = (EntryManager) o;
    return Objects.equals(clArgs, that.clArgs) &&
        Objects.equals(entries, that.entries);
  }

  @Override
  public int hashCode() {
    return Objects.hash(clArgs, entries);
  }

  @Override
  public String toString() {
    return "EntryManager{" +
        "clArgs=" + clArgs +
        ", entries=" + entries +
        '}';
  }
}
