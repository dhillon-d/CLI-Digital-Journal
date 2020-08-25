package p1;

public class EntryNotFoundException extends Exception {

  public EntryNotFoundException() {
    super("no entry present for that ID");
  }
}