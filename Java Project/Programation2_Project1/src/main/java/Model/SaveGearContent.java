package Model;

public class SaveGearContent extends SaveContent{
    
    public String brand, model, serial, status;
    
    public SaveGearContent(String resourceName, String date, String brand, String model, String serial, String status){
        
        super(resourceName, "Gear", date);
        this.brand = brand;
        this.model = model;
        this.serial = serial;
        this.status = status;
    }
}
