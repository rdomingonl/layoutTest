package nl.tc.rd.exp.layouttest.shared.buildingblock;


/**
 * @author raymond
 * 
 */
public abstract class BuildingBlockBase implements BuildingBlock {
	
    private String identifier;
    private String name;
    
    public BuildingBlockBase() {
    	super();
    }
    
    public BuildingBlockBase(String identifier, String name) {
    	this();
    	this.name=name;
    	this.identifier = identifier;
    }

    public String getBuildingBlockID() {
        return identifier;
    }

    public void setBuildingBlockID(String identifier) {
        this.identifier = identifier;
    }

    public String getBuildingBlockName() {
        return name;
    }

    public void setBuildingBlockName(String name) {
        this.name = name;
    }

}
