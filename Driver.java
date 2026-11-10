public class Driver{
    public static void main(String[] args){
        Test test = new Test();


        System.out.println("----- Test #1 -----");
        test.initPlanetData("planets.csv");
        test.showPlanetNames();
        System.out.println();

        System.out.println("----- Test #2 ------");
        test.initSatelliteData("satellites.csv");
        test.showSatelliteNames();
    }
}