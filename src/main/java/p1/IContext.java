package p1;

import java.io.IOException;

/**
 * represents the context of a strategy
 */
public interface IContext {

  /**
   * runs a strategy
   *
   * @param s the strategy to execute
   * @throws MapsNotSetException if attempted on null values
   * @throws IOException         if fie handling results in an error
   * @throws BadFormatException  if format is incorrect
   */
  void executeStrategy(IEntryStrategy s)
      throws MapsNotSetException, IOException, BadFormatException;
}
