import java.util.TreeSet;
public class Planet implements IPlanet, IPlanet2, Comparable<Planet>{
    //IPlanet
    private String name;
    private float mass;
    private int diameter;
    private int density;
    private double gravity;
    private double escapeVelocity;
    private double rotationPeriod;
    private double dayLength;
    private double distanceFromSun;
    private double perihelion;

    // IPlanet2
    private double aphelion;
    private double orbitalPeriod;
    private double orbitalVelocity;
    private double orbitalInclincation;
    private double orbitalEccentricity;
    private double obliquityToOribit;
    private double meanTemperature;
    private String surfacePressure;
    private int numberOfMoons;
    private String hasRingSystem;
    private String hasGlobalMagneticField;

    // Moons orbiting this planet that is sorted by Satellite's compareTo
    private TreeSet<Satellite> satelliteList = new TreeSet<>();
    // Constructor
    public Planet(String name, float mass, int diameter, int density,
                    double gravity, double escapeVelocity, double rotationPeriod,
                    double dayLength, double distanceFromSun, double perihelion, double aphelion,
                    double orbitalPeriod, double orbitalVelocity, double orbitalInclincation,
                    double orbitalEccentricity, double obliquityToOribit, double meanTemperature,
                    String surfacePressure, int numberOfMoons, String hasRingSystem, String hasGlobalMagneticField){
        this.name = name;
        this.mass = mass;
        this.diameter = diameter;
        this.density = density;
        this.gravity = gravity;
        this.escapeVelocity = escapeVelocity;
        this.rotationPeriod = rotationPeriod;
        this.dayLength = dayLength;
        this.distanceFromSun = distanceFromSun;
        this.perihelion = perihelion;
        
        this.aphelion = aphelion;
        this.orbitalPeriod = orbitalPeriod;
        this.orbitalVelocity = orbitalVelocity;
        this.orbitalInclincation = orbitalInclincation;
        this.orbitalEccentricity = orbitalEccentricity;
        this.obliquityToOribit = obliquityToOribit;
        this.meanTemperature = meanTemperature;
        this.surfacePressure = surfacePressure;
        this.numberOfMoons = numberOfMoons;
        this.hasRingSystem = hasRingSystem;
        this.hasGlobalMagneticField = hasGlobalMagneticField;
    }
    @Override
    public String getName(){
        return name;
    }
    @Override
    public float getMass(){
        return mass;
    }
    @Override
    public int getDiameter(){
        return diameter;
    }
    @Override
    public int getDensity(){
        return density;
    }
    @Override
    public double getGravity(){
        return gravity;
    }
    @Override
    public double getEscapeVelocity(){
        return escapeVelocity;
    }
    @Override
    public double getRotationPeriod(){
        return rotationPeriod;
    }
    @Override
    public double getDayLength(){
        return dayLength;
    }
    @Override
    public double getDistanceFromSun(){
        return distanceFromSun;
    }
    @Override
    public double getPerihelion(){
        return perihelion;
    }
    @Override
    public TreeSet getSatelliteList(){
        return satelliteList;
    }
    @Override
    public Satellite getSatellite(String satelliteName){
        for(Satellite s: satelliteList){
            if(s.getName().equalsIgnoreCase(satelliteName)){
                return s;
            }
        }
        return null;
    }
    @Override
    public void addSatellite(Satellite objSat){
        satelliteList.add(objSat);
    }

    // IPlanet2 getters
    @Override
    public double getAphelion(){
        return aphelion;
    }
    @Override
    public double getOrbitalPeriod(){
        return orbitalPeriod;
    }
    @Override
    public double getOrbitalVelocity(){
        return orbitalVelocity;
    }
    @Override
    public double getOrbitalInclination(){
        return orbitalInclincation;
    }
    @Override
    public double getOrbitalEccentricity(){
        return orbitalEccentricity;
    }
    @Override
    public double getObliquityToOrbit(){
        return obliquityToOribit;
    }
    @Override
    public double getMeanTemperature(){
        return meanTemperature;
    }
    @Override
    public String getSurfacePressure(){
        return surfacePressure;
    }
    @Override
    public double getNumberOfMoons(){
        return numberOfMoons;
    }
    @Override
    public String getHasRingSystem(){
        return hasRingSystem;
    }
    @Override
    public String getHasGlobalMagneticField(){
        return hasGlobalMagneticField;
    }

    // TreeSet uses this to sort planets in alphabetical order
    // 

    @Override
    public int compareTo(Planet other){
        if(this.distanceFromSun != other.getDistanceFromSun()){
            return Double.compare(this.distanceFromSun, other.distanceFromSun);
        }
        return this.name.compareTo(other.name);
    }

    @Override String toString(){
        IPlanet ip = this;
        IPlanet2 ip2 = this;

        return ip.getName() + "," + ip.getMass() + "," + ip.getDiameter() + ","
        + ip.getDensity() + "," + ip.getGravity() + "," + ip.getEscapeVelocity + ","
        + ip.getRotationPeriod() + "," + ip.getDayLength() + ","
        + ip.getDistanceFromSun() + "," + ip.getPerihelion() + ","
        + ip2.getAphelion() + "," + ip2.getOrbitalPeriod() + ","
        + ip2.getOrbitalVelocity() + "," + ip2.getOrbitalInclination() + ","
        + ip2.getOribitalEccentricity() + "," + ip2.getObliquityToOrbit() + ","
        + ip2.getMeanTemperature() + "," + ip2.getSurfacePressure() + ","
        + ip2.getNumberOfMoons() + "," + ip2.getHasRingSystem() + ","
        + ip2.getHasGlobalMagneticField();
    }

}