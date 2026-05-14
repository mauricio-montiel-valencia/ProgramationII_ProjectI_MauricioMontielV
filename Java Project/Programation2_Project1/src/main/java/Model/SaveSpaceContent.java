package Model;

public class SaveSpaceContent extends SaveContent{
    
    public String capacity, location, spaceType, restrictions;
    
    public SaveSpaceContent(String resourceName, String date, String capacity, String location, String spaceType, String restrictions){
    
        super(resourceName, "Space", date);
        this.capacity = capacity;
        this.location = location;
        this.spaceType = spaceType;
        this.restrictions = restrictions;
    }
}
