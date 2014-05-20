package nl.tc.rd.exp.layouttest.client.buildingblock.flowlayoutcontainer;

import java.util.HashMap;

import nl.tc.rd.exp.layouttest.shared.buildingblock.BuildingBlockContainerBase;

/**
 * @author raymond
 * 
 */
public class FlowLayoutContainerBB extends BuildingBlockContainerBase {
    private HashMap<String, String> data = new HashMap<String, String>();

    public FlowLayoutContainerBB() {
    	super("FlowLayoutContainer","FlowLayoutContainer",true);
    }
}
