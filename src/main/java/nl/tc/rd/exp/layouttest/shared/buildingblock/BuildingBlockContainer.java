package nl.tc.rd.exp.layouttest.shared.buildingblock;

import com.google.gwt.user.client.ui.Widget;

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
	Widget getPropertiesWidget();
	
}
