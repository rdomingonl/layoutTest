package nl.tc.rd.exp.layouttest.client;

import nl.tc.rd.exp.layouttest.client.buildingblock.flowlayoutcontainer.FlowLayoutContainerBB;
import nl.tc.rd.exp.layouttest.client.event.BuildingBlockLifeCycleEvent;
import nl.tc.rd.exp.layouttest.client.mvp.presenter.WidgetDesignerPresenter;
import nl.tc.rd.exp.layouttest.client.mvp.view.WidgetDesignerView;
import nl.tc.rd.exp.layouttest.shared.buildingblock.BuildingBlock;
import nl.tc.rd.exp.layouttest.shared.buildingblock.BuildingBlockBase;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.event.shared.SimpleEventBus;
import com.google.gwt.user.client.ui.RootPanel;
import com.sencha.gxt.widget.core.client.container.Viewport;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class Application implements EntryPoint {
	EventBus globalEventBus = new SimpleEventBus();
	/**
	 * This is the entry point method.
	 */
	public void onModuleLoad() {

		// create views
		WidgetDesignerView view = new WidgetDesignerView(globalEventBus);

		// create presenters
		WidgetDesignerPresenter presenter = new WidgetDesignerPresenter(
		        globalEventBus, view);
		
		// add root view to RootPanel
		Viewport vp = new Viewport();
		vp.setWidget(view);
		RootPanel.get().add(vp);
		
		// register buildingblocks
		registerBuildingBlock(new FlowLayoutContainerBB());
		
	}

	private void registerBuildingBlock(
            BuildingBlock bb) {
		globalEventBus.fireEvent(new BuildingBlockLifeCycleEvent(bb, BuildingBlockLifeCycleEvent.Action.REGISTER));
    }
}
