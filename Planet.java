import java.util.TreeSet;
public class Planet implements IPlanet, Comparable<Planet>{
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

    private double aphelion;
    private double orbitalPeriod;
    private double orbitalVelocity;
    private double orbitalInclincation;
    private double orbitalEccentricity;
    private double obliquityToOribit;
    private double meanTemperature;
    private double surfacePressure;
    private int numberOfMoons;
    private Boolean hasRingSystem;
    private Boolean hasGlobalMagneticField;

    
    private TreeSet<Satellite> satelliteList = new TreeSet<>();

    public Planet(String name, float mass, int diameter, int density,
                    double gravity, double escapeVelocity, double rotationPeriod,
                    double dayLength, double distanceFromSun, double perihelion, double aphelion,
                    double orbitalPeriod, double orbitalVelocity, double orbitalInclincation,
                    double orbitalEccentricity, double obliquityToOribit, double meanTemperature,
                    double surfacePressure, int numberOfMoons, Boolean hasRingSystem, Boolean hasGlobalMagneticField){
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
    public String getName(){
        return name;
    }

    public float getMass(){
        return mass;
    }

    public int getDiameter(){
        return diameter;
    }

    public int getDensity(){
        return density;
    }

    public double getGravity(){
        return gravity;
    }

    public double getEscapeVelocity(){
        return escapeVelocity;
    }

    public double getRotationPeriod(){
        return rotationPeriod;
    }

    public double getDayLength(){
        return dayLength;
    }

    public double getDistanceFromSun(){
        return distanceFromSun;
    }

    public double getPerihelion(){
        return perihelion;
    }

    public TreeSet getSatelliteList(){
        return satelliteList;
    }

    public Satellite getSatellite(String satelliteName){
        for(Satellite s: satelliteList){
            if(s.getName().equalsIgnoreCase(satelliteName)){
                return s;
            }
        }
        return null;
    }

    public void addSatellite(Satellite objSat){
        satelliteList.add(objSat);
    }

    public double getAphelion(){
        return aphelion;
    }

    public double getOrbitalPeriod(){
        return orbitalPeriod;
    }

    public double getOrbitalVelocity(){
        return orbitalVelocity;
    }

    public double getOrbitalInclination(){
        return orbitalInclincation;
    }

    public double getOrbitalEccentricity(){
        return orbitalEccentricity;
    }

    public double getObliquityToOrbit(){
        return obliquityToOribit;
    }

    public double getMeanTemperature(){
        return meanTemperature;
    }

    public double getSurfacePressure(){
        return surfacePressure;
    }

    public double getNumberOfMoons(){
        return numberOfMoons;
    }

    public Boolean getHasRingSystem(){
        return hasRingSystem;
    }

    public Boolean getHasGlobalMagneticField(){
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

}