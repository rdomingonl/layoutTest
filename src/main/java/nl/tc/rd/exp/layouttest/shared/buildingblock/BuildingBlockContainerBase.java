package nl.tc.rd.exp.layouttest.shared.buildingblock;

import java.util.ArrayList;
import java.util.List;

/**
 * @author raymond
 * 
 */
public class BuildingBlockContainerBase extends BuildingBlockBase implements
        BuildingBlockContainer {

	private List<BuildingBlock> children = new ArrayList<BuildingBlock>();
	private BuildingBlock parent;
	private boolean insertSupport;
	
	public BuildingBlockContainerBase(String id, String name, boolean insertSupport) {
		super(id,name);
		this.insertSupport=insertSupport;
	}

	@Override
	public BuildingBlock getBuildingBlockParent() {
		return parent;
	}

	@Override
	public boolean isInsertSupported() {
		return insertSupport;
	}

	@Override
	public boolean hasChildren() {
		return children.size() > 0;
	}

	@Override
	public void addChild(BuildingBlock bb) {
		children.add(bb);
	}

	@Override
	public void removeChild(BuildingBlock bb) {
		children.remove(bb);
	}

}
