package nl.tc.rd.exp.layouttest.shared.buildingblock.common;


/**
 * @author raymond
 * 
 */
public abstract class BuildingBlockBase implements BuildingBlock{
	
    private String identifier;
    private String name;
    
    public BuildingBlockBase() {
    	
    }
    
    public BuildingBlockBase(String identifier, String name) {
    	this.name=name;
    	this.identifier = identifier;
    }

    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
