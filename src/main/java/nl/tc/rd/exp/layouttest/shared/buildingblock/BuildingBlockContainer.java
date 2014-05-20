package nl.tc.rd.exp.layouttest.shared.buildingblock;

/**
 * @author raymond
 * 
 */
public interface  BuildingBlockContainer extends
        BuildingBlock {
	boolean isInsertSupported();
	boolean hasChildren();
	void addChild(BuildingBlock bb);
	void removeChild(BuildingBlock bb);
	
}
