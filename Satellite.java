
public class Satellite implements ISatellite, Comparable<Satellite>{
    //planet,name,gm,radius,density,magnitude,albedo
    private String planetName;
    private String name;
    private double GM;
    private double radius;
    private double density;
    private double magnitude;
    private double albedo;

    public Satellite(String planetName, String name, double GM, double radius,
                    double density, double magnitude, double albedo){
        this.planetName = planetName;
        this.name = name;
        this.GM = GM;
        this.radius = radius;
        this.density = density;
        this.magnitude = magnitude;
        this.albedo = albedo; 
    }

    @Override
    public String getPlanetName(){// Associated planet of the moon
        return planetName;
    }

    @Override
    public String getName(){ // Name of the moon
        return name;
    }

    @Override
    public double getGM(){ // this is product of gravity times magnitude
    return GM;
    }

    @Override
    public double getRadius(){
        return radius;
    }

    @Override
    public double getDensity(){
        return density;
    }

    @Override
    public double getMagnitude(){
        return magnitude;
    }

    @Override
    public double getAlbedo(){
        return albedo;
    }

    @Override
    public int compareTo(Satellite other){
        if(!this.name.equals(other.name)){
            return this.name.compareTo(other.name);
        }
        return this.planetName.compareTo(other.planetName);
    }
    
    @Override
    public String toString(){
        ISatellite is = this;

        return is.getPlanetName() + "," + is.getName() + "," + is.getGM() + "," 
        + is.getRadius() + "," + is.getDensity() + ","
        + is.getMagnitude() + "," + is.getAlbedo(); 
    }

}