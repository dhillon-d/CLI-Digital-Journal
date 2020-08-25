package p1;

public class MapsNotSetException extends Exception {

  public MapsNotSetException() {
    super("maps not present or invalid null values attempted");
  }
}
