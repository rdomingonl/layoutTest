package nl.tc.rd.exp.layouttest.shared.buildingblock;


/**
 * @author raymond
 * 
 */
public interface  BuildingBlock {
    public String getBuildingBlockID() ;
    public String getBuildingBlockName() ;
    public BuildingBlock getBuildingBlockParent();
}
