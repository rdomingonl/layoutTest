package nl.tc.rd.exp.layouttest.client.mvp.event;

import nl.tc.rd.exp.layouttest.shared.buildingblock.BuildingBlock;

import com.google.gwt.event.shared.EventHandler;

/**
 * Handler of lifecycleevents
 */
public interface WidgetDesignerViewEventHandler extends EventHandler {
	void onInsertBuildingBlock(BuildingBlock bb);

	void onEditBuildingBlock(BuildingBlock bb);

	void onDeleteBuildingBlock(BuildingBlock bb);
}
