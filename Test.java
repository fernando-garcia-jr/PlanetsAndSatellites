import java.util.TreeMap;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Map;
import java.util.TreeSet;

public class Test implements ITest, IFile{ 

    // Key = file name, Value = bufferedReader for that file
    private TreeMap<String, BufferedReader> openFiles = new TreeMap<>();

    // The loaded data for each class:
    private TreeSet<Planet> planetSet = new TreeSet<>();
    private TreeSet<Satellite> satelliteSet = new TreeSet<>();

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
            br.readLine(); // for header

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
                        parseDoubleOrDefault(p[4], -1),     // gravity
                        parseDoubleOrDefault(p[5], -1),     // escapeVelocity
                        parseDoubleOrDefault(p[6], -1),     // rotationPeriod
                        parseDoubleOrDefault(p[7], -1),     // lengthOfDay
                        parseDoubleOrDefault(p[8], -1),     // distanceFromSun
                        parseDoubleOrDefault(p[9], -1),     // perihelion
                        parseDoubleOrDefault(p[10], -1),    // aphelion
                        parseDoubleOrDefault(p[11], -1),    // orbitalPeriod
                        parseDoubleOrDefault(p[12], -1),    // orbitalVelocity
                        parseDoubleOrDefault(p[13], -1),    // orbitalInclination
                        parseDoubleOrDefault(p[14], -1),    // orbitalEccentricity
                        parseDoubleOrDefault(p[15], -1),    // obliquityToOrbit
                        parseDoubleOrDefault(p[16], -1),    // meanTemperature
                        parseDoubleOrDefault(p[17], -1),    // surfacePressure
                        Integer.parseInt(p[18].trim()),     // numberOfMoons
                        parseYesNo(p[19]),                  // hasRingSystem                    
                        parseYesNo(p[20])                   // hasGlobalMagneticField
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
            br.readLine();

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
                        parseDoubleOrDefault(p[2], -1),     // gm
                        parseDoubleOrDefault(p[3], -1),     // radius
                        parseDoubleOrDefault(p[4], -1),     // density
                        parseDoubleOrDefault(p[5], -1),     // magnitude
                        parseDoubleOrDefault(p[6], -1)     // albedo
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
        for(Planet planet : planetSet){
            System.out.println("- " + planet.getName());
        }
    }
    public void showSatelliteNames(){  
    // List all of the satellite names from top to bottom (e.g. each name list as: e.g. "- Moon")
        System.out.println("Satellites: ");
        for(Satellite sat : satelliteSet){
            System.out.println("- " + sat.getName());
        }
    }


    private double parseDoubleOrDefault(String value, double fallback){
        try{
            return Double.parseDouble(value.trim());
        }catch (NumberFormatException e){
            return fallback;
        }
    }

    private Boolean parseYesNo(String value){
        String v = value.trim();
        if(v.equalsIgnoreCase("yes")) return true;
        if(v.equalsIgnoreCase("no")) return false;
        return null;
    }

    private Planet findPlanet(String planetName){
        for(Planet planet : planetSet){
            if(planet.getName().equalsIgnoreCase(planetName)){
                return planet;
            }
        }
        return null;
    }
}