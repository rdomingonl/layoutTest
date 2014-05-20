package nl.tc.rd.exp.layouttest.client.event;

import nl.tc.rd.exp.layouttest.shared.buildingblock.BuildingBlock;

import com.google.gwt.event.shared.EventHandler;


public interface  BuildingBlockLifeCycleEventHandler extends EventHandler {
	void onBuildingBlockRegistration(BuildingBlock bb);
}
