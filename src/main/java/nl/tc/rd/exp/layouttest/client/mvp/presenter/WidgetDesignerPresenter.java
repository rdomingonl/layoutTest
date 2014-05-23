package nl.tc.rd.exp.layouttest.client.mvp.presenter;

import nl.tc.rd.exp.layouttest.client.event.BuildingBlockActionEvent;
import nl.tc.rd.exp.layouttest.client.event.BuildingBlockActionEventHandler;
import nl.tc.rd.exp.layouttest.client.mvp.event.WidgetDesignerViewEvent;
import nl.tc.rd.exp.layouttest.client.mvp.event.WidgetDesignerViewEventHandler;
import nl.tc.rd.exp.layouttest.client.mvp.view.WidgetDesignerView;
import nl.tc.rd.exp.layouttest.shared.buildingblock.BuildingBlock;

import com.google.gwt.event.shared.EventBus;

/**
 * Display the Validation sample.
 */
public class WidgetDesignerPresenter implements WidgetDesignerViewEventHandler, BuildingBlockActionEventHandler {

    private final Display view;
    private final EventBus globalEventBus;

    public interface Display {
    	public BuildingBlock getSelectedBuildingBlock();
    	public void addBuldingBlock(BuildingBlock bb);
    }

    public WidgetDesignerPresenter(EventBus globalEventBus, final WidgetDesignerView view) {
        // store parameters
        this.globalEventBus = globalEventBus;
        this.view = view;
        globalEventBus.addHandler(BuildingBlockActionEvent.TYPE,this);
        globalEventBus.addHandler(WidgetDesignerViewEvent.TYPE, this);
    }

	@Override
    public void onInsertBuildingBlock(BuildingBlock bb) {
		view.addBuldingBlock(bb);
    }

	@Override
    public void onEditBuildingBlock(BuildingBlock bb) {
    }

	@Override
    public void onDeleteBuildingBlock(BuildingBlock bb) {
    }
}
