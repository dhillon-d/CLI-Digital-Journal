package p1;

import sun.text.resources.ext.CollationData_et;

import java.security.DigestInputStream;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.Comparator;
import java.util.stream.Collectors;


public class DisplayDigitalEntries {

    public static final String SHOW_INCOMPLETE = "--show-incomplete";
    public static final String SHOW_CATEGORY = "--show-category";
    public static final String SORT_BY_DATE = "--sort-by-date";
    public static final String SORT_BY_PRIORITY = "--sort-by-priority";

    public HashMap<String, String> filePath;
    public ArrayList<Map<String, String>> digitalEntry;

    /**
     * Constructor create an object with filePath and digitalEntry
     * @param filePath map from cli parser
     * @param digitalEntry list of maps from digital entries
     */
    public DisplayDigitalEntries(HashMap<String, String> filePath, ArrayList<Map<String, String>> digitalEntry) {
        this.filePath = filePath;
        this.digitalEntry = digitalEntry;
    }

    /**
     * Return map from cli parser
     * @return map from cli parser
     */
    public HashMap<String, String> getFilePath() {
        return filePath;
    }

    /**
     * Return list of maps from data entries
     * @return list of maps from data entries
     */
    public ArrayList<Map<String, String>> getDigitalEntry() {
        return digitalEntry;
    }

    /**
     * Overall display function for digital entries
     * @throws InvalidDisplayException
     */
    public void display() throws InvalidDisplayException{

         if ( filePath.containsKey("--display") ) {

             if(  (!filePath.containsKey(SHOW_INCOMPLETE)) &&
                         (!filePath.containsKey(SHOW_CATEGORY)) &&
                                 (!filePath.containsKey(SORT_BY_DATE)) &&
                                    (!filePath.containsKey(SORT_BY_PRIORITY))) {

                     displayWithoutOptions();
                } else {
                    displayWithOption();
                 }

         }

    }

    /**
     * Display digital entry without options
     * @return print digital entry
     * @throws InvalidDisplayException
     */
    protected void displayWithoutOptions() throws InvalidDisplayException {
        if (!isEmpty(this.digitalEntry)) {
                printDigitalEntry(this.digitalEntry);
            } else {
                throw new InvalidDisplayException("Digital Entry requested does not exist!");
            }
    }

    /**
     * Return display digital entry fit option
      * @return display digital entry fit option
     * @throws InvalidDisplayException
     */
    protected void displayWithOption() throws InvalidDisplayException {
        if(filePath.containsKey(SHOW_INCOMPLETE) &&
                (!filePath.containsKey(SHOW_CATEGORY))) {
            showIncomplete();
        }

        if(filePath.containsKey(SHOW_CATEGORY) &&
                (!filePath.containsKey(SHOW_INCOMPLETE))) {
            showCategory();
        }

        if(filePath.containsKey(SHOW_CATEGORY) && filePath.containsKey(SHOW_INCOMPLETE)) {
            showIncompleteAndCategory();
        }

        if(filePath.containsKey(SORT_BY_DATE)) {
            sortByDate();
        }

        if(filePath.containsKey(SORT_BY_PRIORITY)) {
            sortByPriority();
        }
    }

    /**
     * Return incomplete digital entry
     * @throws InvalidDisplayException
     */
    protected void showIncomplete() throws InvalidDisplayException{
        ArrayList<Map<String, String>> newList = (ArrayList<Map<String, String>>) digitalEntry.stream()
                .filter(x -> x.get("completed").equals("false"))
                .collect(Collectors.toList());
        
        if (newList.size()>0) {
            printDigitalEntry(newList);
        } else {
            throw new InvalidDisplayException("There is no incomplete digital entry.");
        }
    }

    /**
     * Return incomplete digital entry
     * @throws InvalidDisplayException
     */
    protected void showCategory() throws InvalidDisplayException{
        String criterion = filePath.get("--show-category");

        ArrayList<Map<String, String>> newList = (ArrayList<Map<String, String>>) digitalEntry.stream()
                .filter(x -> x.get("category")== criterion)
                .collect(Collectors.toList());

        if (newList.size()>0) {
            printDigitalEntry(newList);
        } else {
            throw new InvalidDisplayException("There is no digital entry in the category.");
        }
    }

    /**
     * Return digital entry that meet the category
     * @throws InvalidDisplayException
     */
    protected void showIncompleteAndCategory() throws InvalidDisplayException {
        ArrayList<Map<String, String>> newList = new ArrayList<Map<String, String>>();

        newList = (ArrayList<Map<String, String>>) digitalEntry.stream()
                .filter(x -> x.get("category") == (String) filePath.get("--show-category"))
                .filter(x -> x.get("completed").equals("false"))
                .collect(Collectors.toList());

        if (newList.size() > 0) {
            printDigitalEntry(newList);
        } else {
            throw new InvalidDisplayException("There is no digital entry that meet the criteria.");
        }

    }

    /**
     * Return sorted by Date digital entry
     */
    void sortByDate() {

        //nested class for date sorter
        class DateSorter implements Comparator<Map<String, String>>
        {
            @Override
            public int compare(Map<String, String> o1, Map<String, String> o2) {
                DateTimeFormatter df = DateTimeFormatter.ofPattern("MM/dd/yyyy");

                LocalDate d1 = LocalDate.parse(o2.get("date"), df);
                LocalDate d2 = LocalDate.parse(o1.get("date"), df);
                return d2.compareTo(d1);
            }
        }

        digitalEntry.sort(new DateSorter());
        printDigitalEntry(digitalEntry);
    }

    /**
     * Return sorted by Priority digital entry
     */
    void sortByPriority () {

        //nested class for date sorter
        class PrioritySorter implements Comparator<Map<String, String>>
        {
            @Override
            public int compare(Map<String, String> o1, Map<String, String> o2) {
                Integer e1 = Integer.parseInt(o1.get("priority"));
                Integer e2 = Integer.parseInt(o2.get("priority"));
                return e1.compareTo(e2);
            }
        }
        
        digitalEntry.sort(new PrioritySorter());
        printDigitalEntry(digitalEntry);
     }


        /***
         * Print digital entry
         * @param digitalEntry
         */
    private void printDigitalEntry(ArrayList<Map<String,String>> digitalEntry) {

        System.out.println("\nThe digital entry values are: ");

        Iterator<Map<String, String>> iter = digitalEntry.iterator();
        while (iter.hasNext()) {
            System.out.print(iter.next() + "\n");
        }
    }

    /**
     * Check whether List of DigitalEntry is empty
     * @param digitalEntry
     * @return true - empty list; false - list with values
     */
    private Boolean isEmpty(ArrayList<Map<String, String>> digitalEntry) {
        if(digitalEntry.size()==0) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DisplayDigitalEntries that = (DisplayDigitalEntries) o;
        return Objects.equals(filePath, that.filePath) &&
                Objects.equals(digitalEntry, that.digitalEntry);
    }

    @Override
    public int hashCode() {
        return Objects.hash(filePath, digitalEntry);
    }

    @Override
    public String toString() {
        return "DisplayDigitalEntries{" +
                "filePath=" + filePath +
                ", digitalEntry=" + digitalEntry +
                '}';
    }
}
