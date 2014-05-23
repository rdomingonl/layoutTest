package nl.tc.rd.exp.layouttest.shared.buildingblock;

import com.google.gwt.user.client.ui.Widget;


/**
 * @author raymond
 * 
 */
public interface  BuildingBlock {
    public String getBuildingBlockID() ;
    public String getBuildingBlockName() ;
    public BuildingBlock getBuildingBlockParent();
    public void setBuildingBlockParent(BuildingBlock bb);
    public BuildingBlock createNewInstance();
    public Widget getWidget();
}
