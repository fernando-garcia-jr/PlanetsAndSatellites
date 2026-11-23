import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;
import java.util.TreeSet;

public class Test implements ITest, ITest2, ITestSupport, IFile{ 

    // Key = file name, Value = bufferedReader for that file
    private TreeMap<String, BufferedReader> openFiles = new TreeMap<>();

    // The loaded data for each class:
    private TreeSet<Planet> planetSet = new TreeSet<>();
    private TreeSet<Satellite> satelliteSet = new TreeSet<>();

    // Column names read from first line of each file
    private ArrayList<String> planetColumnNames = new ArrayList<>();
    private ArrayList<String> satelliteColumnNames = new ArrayList<>();

    // Number of planet columns go on first row of table:
    private static final int firstRowColumns = 10;

 //IFile:
    public BufferedReader openFile(String fileName){ // Uses the fileName and opens a file and returns a BufferedReader object
    // The class that implements this must keep track of this BufferedReader too
    /* The class implementing this must use a TreeMap Value (BufferedReader object) &
    Key (file name) to keep track of the files/buffered reader pairs
    */

    try{
        BufferedReader br = new BufferedReader(new FileReader(fileName));
        openFiles.put(fileName, br); // places fileName and bufferedReader object into treeMap
        return br;
    }catch (IOException e){
        System.out.println("Could not open file: " + fileName);
        return null;
        }
    }


    public int closeFile(BufferedReader br){  
    // The buffered reader must be closed when done with (should first check the br if it is a match)
    // returns 0 if okay, returns -1 if br is not matching (immediately returns -1 if not found)
    // the K,V pair needs to be removed if 0 is going to be returned
        String fileName = getFileName(br);
        if(fileName == null){
            return -1; //not matching
        }

        try {
            br.close();
        } catch (IOException e ){
            return -1;
        }

        openFiles.remove(fileName); // only removes when returning 0
        return 0;
    }


    //Looks through map for exact reader and returns its file name.
    //Returns null if reader isn't being tracked.
    public String getFileName(BufferedReader br){
    // gets the file name based on the buffered reader
    /* A TreeMap of BufferedReader (Value), and file name (Key) should be maintained
    in the class that implements this interface */
        for(Map.Entry<String, BufferedReader> entry : openFiles.entrySet()){
            if(entry.getValue() == br){ // checks if same object
                return entry.getKey();
            }
        }
        return null;
    }

    //ITest
    public void initPlanetData(String fileName){  
    // opens the planet file and creates a list of planet objects and places it in a TreeSet
        BufferedReader br = openFile(fileName);
        if(br == null){
            return;
        }

        try{
            planetColumnNames = parseColumnNames(br.readLine());

            String line;
            while((line = br.readLine()) != null){
                if(line.trim().isEmpty()){
                    continue;
                } // adds planet objects to tree set
                String[] p = line.split(",", -1);
                try{
                    Planet planet = new Planet(
                        p[0].trim(),                        // planet
                        Float.parseFloat(p[1].trim()),      // mass
                        Integer.parseInt(p[2].trim()),      // diameter
                        Integer.parseInt(p[3].trim()),      // density
                        Double.parseDouble(p[4].trim()),     // gravity
                        Double.parseDouble(p[5].trim()),     // escapeVelocity
                        Double.parseDouble(p[6].trim()),     // rotationPeriod
                        Double.parseDouble(p[7].trim()),     // lengthOfDay
                        Double.parseDouble(p[8].trim()),     // distanceFromSun
                        Double.parseDouble(p[9].trim()),     // perihelion
                        Double.parseDouble(p[10].trim()),    // aphelion
                        Double.parseDouble(p[11].trim()),    // orbitalPeriod
                        Double.parseDouble(p[12].trim()),    // orbitalVelocity
                        Double.parseDouble(p[13].trim()),    // orbitalInclination
                        Double.parseDouble(p[14].trim()),    // orbitalEccentricity
                        Double.parseDouble(p[15].trim()),    // obliquityToOrbit
                        Double.parseDouble(p[16].trim()),    // meanTemperature
                        p[17].trim(),                       // surfacePressure
                        Integer.parseInt(p[18].trim()),     // numberOfMoons
                        p[19].trim(),                       // hasRingSystem                    
                        p[20].trim()                        // hasGlobalMagneticField
                    );
                    planetSet.add(planet); //adds planet objects to treeSet: planetSet
                }catch (NumberFormatException | ArrayIndexOutOfBoundsException e){
                    System.out.println("Skipping bad planet row: " + line);
                }
            }
        } catch (IOException e){
            System.out.println("Error reading file: " + fileName);
        } finally{
            closeFile(br);
        }
    
    }
    public void initSatelliteData(String fileName){
    // opens the satellite file and creates a list of satellite objects and places it in a TreeSet
        BufferedReader br = openFile(fileName);

        if(br == null){
            return;
        }

        try{
            satelliteColumnNames = parseColumnNames(br.readLine());

            String line;
            while((line = br.readLine()) != null){
                if(line.trim().isEmpty()){
                    continue;
                }

                String[] p = line.split(",", -1);

                try{
                    Satellite sat = new Satellite(
                        p[0].trim(),                        // planet
                        p[1].trim(),                        // name
                        parseDoubleOrDefault(p[2], Double.NaN),     // gm
                        parseDoubleOrDefault(p[3], Double.NaN),     // radius
                        parseDoubleOrDefault(p[4], Double.NaN),     // density
                        parseDoubleOrDefault(p[5], Double.NaN),     // magnitude
                        parseDoubleOrDefault(p[6], Double.NaN)     // albedo
                    );
                    satelliteSet.add(sat);
                    // attach moons to planets?
                    Planet owner = findPlanet(sat.getPlanetName());
                    if(owner != null){
                        owner.addSatellite(sat);
                    }
                } catch (ArrayIndexOutOfBoundsException e){
                    System.out.println("Skipping bad satellite row: " + line);
                }
            }
        }catch(IOException e){
            System.out.println("Error reading file: " + fileName);
        } finally{
            closeFile(br);
        }
    }
    public void showPlanetNames(){  
    // List all of the planet names from top to bottom (e.g. each name listed as:  e.g. "- Earth")
        System.out.println("Planets: ");
        for(Planet obj : planetSet){
            IPlanet planet = obj;
            System.out.println("- " + planet.getName());
        }
    }
    public void showSatelliteNames(){  
    // List all of the satellite names from top to bottom (e.g. each name list as: e.g. "- Moon")
        System.out.println("Satellites: ");
        for(Satellite obj : satelliteSet){
            ISatellite sat = obj;
            System.out.println("- " + sat.getName());
        }
    }

    // ITest Support:
    @Override
    public ArrayList<String> getPlanetColumnNames(){
        return planetColumnNames;
    }

    @Override
    public ArrayList<String> getSatelliteColumnNames(){
        return satelliteColumnNames;
    }

    // ITest2:

    /**
     * Prints a table of all 21 columns for every planet
     * 
     * Each planet takes two rows:
     * Columns 1-10 on the first row and columns 11-21 on the second.
     * The column names come from getPlanetColumnNames()
     * 
     * Values are read through IPlanet and IPlanet 2 interface variables
     */
    @Override
     public void showPlanetTable(){
        ArrayList<String> columns = getPlanetColumnNames();

        // Collect each planet's values as text using interface variables
        // The outer list holds the rows
        // The inner list holds one row's cells
        // Essentially a 2D ArrayList: a grid of rows and columns
        ArrayList<ArrayList<String>> rows = new ArrayList<>();
        for(Planet obj : planetSet){
            IPlanet ip = obj;
            IPlanet2 ip2 = obj;
            rows.add(planetValues(ip, ip2));
        }

        int[] widths = columnWidths(columns, rows);
        int split = firstRowColumns;
        int total = columns.size();

        String line = "-".repeat(200); // line separator is 200 dashes

        //Header: Column names, split across the two rows
        System.out.println(line);
        System.out.println(formatRow(columns, widths, 0, split));
        System.out.println(formatRow(columns, widths, split, total));
        System.out.println(line);

        // Data: two rows per planet, then a separator
        for(ArrayList<String> values : rows){
            System.out.println(formatRow(values, widths, 0, split));
            System.out.println(formatRow(values, widths, split, total));
            // The sepatator in the loop helps distinguish each planet
            System.out.println(line);
        }
    }

    /**
     * Prints a table of all 7 columns for every satellite, one row per moon
     * 
     * The column names come from getSatelliteColumnNames().
     * Values are read through ISatellite interface variable and 
     * any value that was blank in the data file is shown as "N/A"
     */
    @Override
    public void showSatellite(){
        ArrayList<String> columns = getSatelliteColumnNames();

        // The outer list holds the rows
        // The inner list holds one row's cells
        // Essentially a 2D ArrayList: a grid of rows and columns
        ArrayList<ArrayList<String>> rows = new ArrayList<>();
        for(Satellite obj : satelliteSet){
            ISatellite is = obj;
            rows.add(satelliteValues(is));
        }

        int[] widths = columnWidths(columns, rows);
        int total = columns.size();
        String line = "-".repeat(70); // separator of 70 dashes

        System.out.println(line);
        System.out.println(formatRow(columns, widths, 0, total));
        System.out.println(line);
        for(ArrayList<String> values : rows){
            System.out.println(formatRow(values, widths, 0, total));
            System.out.println(line); // separator helps distinguish each moon
        }
    }

    /**
     * HELPERS
     */


    /**
     * Turns a CSV header line into a list of column names.
     * 
     * @param headerLine the first line of a CSV file:
     * null if empty
     * @return the column names in file order or empty list if no header
     */
    private ArrayList<String> parseColumnNames(String headerLine){
        ArrayList<String> names = new ArrayList<>();
        if(headerLine == null){
            return names; // empty file
        }
        for(String name : headerLine.split(",", -1)){
            names.add(name.trim());
        }
        return names;
    }

    /**
     * Collects all 21 values of one planet as text
     * Columns 1-10 come from IPlanet getters
     * Columns 11-21 come from IPlanet2 getters
     * 
     * @param ip the planet through IPlanet interface
     * @param ip2 the same planet through IPlanet2 interface
     * @return a list of 21 strings, one per column
     */
    private ArrayList<String> planetValues(IPlanet ip, IPlanet2 ip2){
        ArrayList<String> v = new ArrayList<>();
        v.add(ip.getName());
        v.add(String.valueOf(ip.getMass()));
        v.add(Integer.toString(ip.getDiameter()));
        v.add(Integer.toString(ip.getDensity()));
        v.add(formatNumber(ip.getGravity()));
        v.add(formatNumber(ip.getEscapeVelocity()));
        v.add(formatNumber(ip.getRotationPeriod()));
        v.add(formatNumber(ip.getDayLength()));
        v.add(formatNumber(ip.getDistanceFromSun()));
        v.add(formatNumber(ip.getPerihelion()));
        v.add(formatNumber(ip2.getAphelion()));
        v.add(formatNumber(ip2.getOrbitalPeriod()));
        v.add(formatNumber(ip2.getOrbitalVelocity()));
        v.add(formatNumber(ip2.getOrbitalInclination()));
        v.add(formatNumber(ip2.getOrbitalEccentricity()));
        v.add(formatNumber(ip2.getObliquityToOrbit()));
        v.add(formatNumber(ip2.getMeanTemperature()));
        v.add(ip2.getSurfacePressure());
        v.add(formatNumber(ip2.getNumberOfMoons()));
        v.add(ip2.getHasRingSystem());
        v.add(ip2.getHasGlobalMagneticField());
        return v;
    }

    /**
     * Collects all 7 values of one satellite as text
     * 
     * @param is the satellite from ISatellite interface
     * @return a list of 7 strings, one per column
     */
    private ArrayList<String> satelliteValues(ISatellite is){
        ArrayList<String> v = new ArrayList<>();
        v.add(is.getPlanetName());
        v.add(is.getName());
        v.add(formatNumber(is.getGM()));
        v.add(formatNumber(is.getRadius()));
        v.add(formatNumber(is.getDensity()));
        v.add(formatNumber(is.getMagnitude()));
        v.add(formatNumber(is.getAlbedo()));
        return v;
    }

    /**
     * Converts a number to text
     * Blank cells in satellite file were stored as NaN -> "Not a Number"
     * NaN prints "N/A"
     * 
     * @param value the number to display
     * @return "N/a" for missing value, else the number as text
     */
    private String formatNumber(double value){
        if(Double.isNaN(value)){
            return "N/A";
        }
        return String.valueOf(value);
    }

    /**
     * Works out the width of each column 
     * A column is as wide as its longest entry
     * 
     * @param columns the number of column names
     * @param rows every row of values, each is a list in column order
     * @return one width per column measured in chars
     */
    private int[] columnWidths(ArrayList<String> columns, ArrayList<ArrayList<String>> rows){
        int[] widths = new int[columns.size()];
        for(int i = 0; i < columns.size(); i++){
            widths[i] = columns.get(i).length();
        }

        for(ArrayList<String> row : rows){
            for(int i = 0; i < widths.length && i < row.size(); i++){
                widths[i] = Math.max(widths[i], row.get(i).length());
            }
        } 
        return widths;
    }

    /**
     * Builds one printed line of the table from a range of columns
     * Each cell is padded with spaces to its columns width and 2 extra spaces of gap
     * so that cells in the same column line up every row
     * 
     * @param cells the names or values for one row
     * @param widths the width of each column from columnWidths()
     * @param from the first column to include
     * @param to one past the last column to include
     * @return the formatted line 
     */
    private String formatRow(ArrayList<String> cells, int[] widths, int from, int to){
        StringBuilder sb = new StringBuilder();
        for(int i = from; i < to && i < cells.size(); i++){
            sb.append(String.format("%-" + (widths[i] + 2) + "s" , cells.get(i)));
        }
        return sb.toString().stripTrailing();
    }

    //Parses a number or returns the fallback for blanks "Unknown" / bad text
    private double parseDoubleOrDefault(String value, double fallback){
        String text = value.trim();

        // Some values have extra text after the number such as a +-
        // Or "21.0R", we will keep only the characters at the start that can be apart
        // of a number.
        int end = 0;
       while(end < text.length() && "0123456789.-+eE".indexOf(text.charAt(end)) != -1){
        end++;
       }
       text = text.substring(0,end);
        try{
            return Double.parseDouble(text);
        }catch (NumberFormatException e){
            return fallback;
        }
    }
    // Finds a loaded planet by name or null if not found
    private Planet findPlanet(String planetName){
        for(Planet planet : planetSet){
            if(planet.getName().equalsIgnoreCase(planetName)){
                return planet;
            }
        }
        return null;
    }
}