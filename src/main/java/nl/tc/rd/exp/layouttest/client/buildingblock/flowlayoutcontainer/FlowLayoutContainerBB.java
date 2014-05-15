package nl.tc.rd.exp.layouttest.client.buildingblock.flowlayoutcontainer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import nl.tc.rd.exp.layouttest.shared.buildingblock.common.BuildingBlockBase;

/**
 * @author raymond
 * 
 */
public class FlowLayoutContainerBB extends BuildingBlockBase {
	
    private boolean allowsMultipleChildren = false;
    private List<FlowLayoutContainerBB> children = new ArrayList<FlowLayoutContainerBB>();
    private FlowLayoutContainerBB parent;
    private HashMap<String, String> data = new HashMap<String, String>();

    public FlowLayoutContainerBB(String identifier, String name, FlowLayoutContainerBB parent) {
    	super(identifier, name);
        this.parent = parent;
    }
    
    public FlowLayoutContainerBB(String identifier, String name) {
    	this(identifier, name, null);
    }
    
    public boolean isAllowsMultipleChildren() {
        return allowsMultipleChildren;
    }

    public void setAllowsMultipleChildren(boolean allowsMultipleChildren) {
        this.allowsMultipleChildren = allowsMultipleChildren;
    }

    public int getChildCount() {
        return children.size();
    }

    public void addChild(FlowLayoutContainerBB child) {
        children.add(child);
    }

    public void removeChild(FlowLayoutContainerBB child) {
        children.remove(child);
    }

    public List<FlowLayoutContainerBB> getChildren() {
        return new ArrayList<FlowLayoutContainerBB>(children);
    }

    public FlowLayoutContainerBB getParent() {
        return parent;
    }

    public void setParent(FlowLayoutContainerBB parent) {
        this.parent = parent;
    }

	public HashMap<String, String> getData() {
		return data;
	}

	public void setData(HashMap<String, String> data) {
		this.data = data;
	}
    
    

}
