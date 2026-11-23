public class Driver{
    public static void main(String[] args){
        Test test = new Test();


        // Load data:
        test.initPlanetData("planets.csv");
        test.initSatelliteData("satellites.csv");

        // Deliverable #1
        // System.out.println("----- Test #1 -----");
        // test.initPlanetData("planets.csv");
        // test.showPlanetNames();
        // System.out.println();

        // System.out.println("----- Test #2 ------");
        // test.initSatelliteData("satellites.csv");
        // test.showSatelliteNames();

        //Deliverable #2
        System.out.println("---- Test #3: Planet Table ----");
        test.showPlanetTable();
        System.out.println();


        System.out.println("---- Test #4: Satellite Table ----");
        test.showSatellite();

    }
}