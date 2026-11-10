interface ITest {

public void initPlanetData(String fileName);  // opens the planet file and creates a list of planet objects and places it in a TreeSet

public void initSatelliteData(String fileName);   // opens the satellite file and creates a list of satellite objects and places it in a TreeSet

public void showPlanetNames();  // List all of the planet names from top to bottom (e.g. each name listed as:  e.g. "- Earth")

public void showSatelliteNames();  // List all of the satellite names from top to bottom (e.g. each name list as: e.g. "- Moon")

}