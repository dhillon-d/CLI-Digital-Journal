package p1;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Main {

  public static void main(String[] arg)
      throws BadFormatException, IOException, MapsNotSetException, EntryNotFoundException,
      IllegalCombinationException, RequiredArgMissingException, PathNotFoundException,
      InvalidDisplayException {

    try {
      CLIParser parse = new CLIParser(arg);
      Map<String, String> args = new HashMap<>();
      args = parse.getMap();

      ArrayList<Map<String, String>> readEntries = new ArrayList<>();
      CSVParser.init((HashMap<String, String>) args);
      readEntries = CSVParser.getInstance().readCSVFile();

      EntryManager e = new EntryManager();
      e.processMaps(args, readEntries);
      if (args.containsKey("--add-entry") || args.containsKey("--complete-entry")) {
        // doesn't allow for file creation - future strategies may
        e.executeStrategy(new WriteCSV());
      }

      if (args.containsKey("--display")) {
        DisplayDigitalEntries display = new DisplayDigitalEntries((HashMap<String, String>) args, e.getEntries());
        display.display();
      }

    } catch (BadFormatException | IOException | MapsNotSetException | EntryNotFoundException
        | IllegalCombinationException | RequiredArgMissingException
        | PathNotFoundException | InvalidDisplayException e) {
      e.printStackTrace();
      System.out.println(e.getMessage());
    }
  }
}