package nl.tc.rd.exp.layouttest.shared.buildingblock.common;

import java.util.ArrayList;
import java.util.List;

/**
 * @author raymond
 * 
 */
public class BuildingBlockRegister {
	private static BuildingBlockRegister instance;
	List<BuildingBlock> buildingBlocks = new ArrayList<BuildingBlock>();
	
	private BuildingBlockRegister() {
		
	}
	
	public static BuildingBlockRegister getInstance() {
		if (instance==null) {
			instance = new BuildingBlockRegister();
		}
		return instance;
	}
	
	public void register(BuildingBlock bb) {
		buildingBlocks.add(bb);
	}
	
	public List <BuildingBlock> getBuildingBlocks() {
		return new ArrayList<BuildingBlock>(buildingBlocks);
	}
}
