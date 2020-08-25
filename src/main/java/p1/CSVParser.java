package p1;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class CSVParser {

    //use singleton to write csvparser
    private static CSVParser csvParser = null;
    private final HashMap<String, String> filePath;

    private CSVParser(HashMap<String, String> filePath) {
        this.filePath = filePath;
    }

    public static CSVParser getInstance() {
        if(csvParser == null) {
            throw new AssertionError("You have to call init first");
        }

        return csvParser;
    }

    public synchronized static CSVParser init(HashMap<String, String> filePath) {
        if (csvParser != null)
        {
            // in my opinion this is optional, but for the purists it ensures
            // that you only ever get the same instance when you call getInstance
            throw new AssertionError("You already initialized me");
        }

        csvParser = new CSVParser(filePath);
        return csvParser;
    }

    /**
     * Return a list of HashMap with csv file data
     *
     * @return a list of HashMap with csv file data
     * @throws Exception
     */
    public ArrayList<Map<String, String>> readCSVFile() throws IOException {

        List<String> header = new ArrayList<>();
        ArrayList<Map<String, String>> csvMap = new ArrayList<>();

        int count = 0;

        /**
         * Get the fileDirectory for readCSV
         */
        String fileDirectory = this.filePath.get("--csv-file");

        /**
         * Try-catch block for inputFile
         */
        try (BufferedReader inputFile = new BufferedReader(new FileReader(fileDirectory));) {

            String line;

            while ((line = inputFile.readLine()) != null) {
                /* read the header and use them for key */
                if (count == 0) {
                    Pattern p = Pattern.compile("[\"](.+?)[\"]");
                    Matcher m = p.matcher(line);
                    while (m.find()) {
                        header.add(m.group(1));
                    }
                    count++;
                } else {
                    Map<String, String> map = new HashMap<String, String>();
                    Pattern p = Pattern.compile("[\"](.+?)[\"]");
                    Matcher m = p.matcher(line);
                    List<String> row = new ArrayList<>();
                    while (m.find()) {
                        row.add(m.group(1));
                    }
                    for (int i = 0; i < row.size(); i++) {
                        map.put(header.get(i), row.get(i));
                    }
                    csvMap.add(map);
                }
            }

            return csvMap;

        } catch (FileNotFoundException e) {
            throw e;
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        CSVParser csvParser = (CSVParser) o;
        return Objects.equals(filePath, csvParser.filePath);
    }

    @Override
    public int hashCode() {
        return Objects.hash(filePath);
    }

    @Override
    public String toString() {
        return "CVSParser{" +
                "filePath=" + filePath +
                '}';
    }

}