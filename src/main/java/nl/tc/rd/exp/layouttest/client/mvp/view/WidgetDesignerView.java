package nl.tc.rd.exp.layouttest.client.mvp.view;

import nl.tc.rd.exp.layouttest.client.buildingblock.rootcontainer.RootContainerBB;
import nl.tc.rd.exp.layouttest.client.event.BuildingBlockLifeCycleEvent;
import nl.tc.rd.exp.layouttest.client.event.BuildingBlockLifeCycleEventHandler;
import nl.tc.rd.exp.layouttest.client.mvp.event.WidgetDesignerViewEvent;
import nl.tc.rd.exp.layouttest.client.mvp.presenter.WidgetDesignerPresenter;
import nl.tc.rd.exp.layouttest.shared.buildingblock.BuildingBlock;
import nl.tc.rd.exp.layouttest.shared.buildingblock.BuildingBlockContainer;

import com.google.gwt.cell.client.ValueUpdater;
import com.google.gwt.dom.client.NativeEvent;
import com.google.gwt.event.logical.shared.SelectionEvent;
import com.google.gwt.event.logical.shared.SelectionHandler;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.text.shared.SimpleSafeHtmlRenderer;
import com.sencha.gxt.cell.core.client.SimpleSafeHtmlCell;
import com.sencha.gxt.core.client.ValueProvider;
import com.sencha.gxt.core.client.util.Margins;
import com.sencha.gxt.data.shared.ModelKeyProvider;
import com.sencha.gxt.data.shared.TreeStore;
import com.sencha.gxt.widget.core.client.ContentPanel;
import com.sencha.gxt.widget.core.client.Dialog;
import com.sencha.gxt.widget.core.client.box.MessageBox;
import com.sencha.gxt.widget.core.client.container.BorderLayoutContainer;
import com.sencha.gxt.widget.core.client.container.BorderLayoutContainer.BorderLayoutData;
import com.sencha.gxt.widget.core.client.container.SimpleContainer;
import com.sencha.gxt.widget.core.client.info.Info;
import com.sencha.gxt.widget.core.client.menu.Item;
import com.sencha.gxt.widget.core.client.menu.Menu;
import com.sencha.gxt.widget.core.client.menu.MenuItem;
import com.sencha.gxt.widget.core.client.tree.Tree;

/**
 * Display the Validation sample.
 */
public class WidgetDesignerView extends SimpleContainer implements
        WidgetDesignerPresenter.Display, BuildingBlockLifeCycleEventHandler {

	EventBus globalEventBus;

	Tree<BuildingBlock, String> buildingBlockTree;
	TreeStore<BuildingBlock> buildingBlockStore;
	BuildingBlock rootBuildingBlock = new RootContainerBB();

	Menu contextMenu = new Menu();
	MenuItem insertMenu = new MenuItem();
	Menu insertSubMenu = new Menu();
	MenuItem editMenu = new MenuItem();
	MenuItem deleteMenu = new MenuItem();

	public WidgetDesignerView(EventBus eb) {
		super();
		this.globalEventBus = eb;
		globalEventBus.addHandler(BuildingBlockLifeCycleEvent.TYPE, this);

		// create store
		buildingBlockStore = new TreeStore<BuildingBlock>(
		        new BuildingBlockIdentityProvider());
		buildingBlockStore.add(rootBuildingBlock);

		// create tree
		buildingBlockTree = new Tree<BuildingBlock, String>(buildingBlockStore,
		        new ValueProvider<BuildingBlock, String>() {

			        @Override
			        public String getValue(BuildingBlock buildingBlock) {
				        return buildingBlock.getBuildingBlockName();
			        }

			        @Override
			        public void setValue(BuildingBlock buildingBlock,
			                String value) {
			        }

			        @Override
			        public String getPath() {
				        return "name";
			        }
		        });
		// buildingBlockTree.setWidth(300);
		SimpleSafeHtmlCell<String> cell = new SimpleSafeHtmlCell<String>(
		        SimpleSafeHtmlRenderer.getInstance(), "click") {

			@Override
			public void onBrowserEvent(Context context,
			        com.google.gwt.dom.client.Element parent, String value,
			        NativeEvent event, ValueUpdater<String> valueUpdater) {
				super.onBrowserEvent(context, parent, value, event,
				        valueUpdater);

				BuildingBlock selection = buildingBlockTree.getSelectionModel()
				        .getSelectedItem();

				// insert
				if (selection instanceof BuildingBlockContainer) {
					BuildingBlockContainer bbc = (BuildingBlockContainer) selection;
					insertMenu.setEnabled(bbc.isInsertSupported()
					        || !bbc.hasChildren());
				} else {
					// no container selected, can't insert
					insertMenu.setEnabled(false);
				}

				// edit
				editMenu.setEnabled(!(selection instanceof RootContainerBB));

				// delete
				deleteMenu.setEnabled(!(selection instanceof RootContainerBB));
			}

		};
		buildingBlockTree.setCell(cell);

		// buildingBlockTree context menu
		buildingBlockTree.setContextMenu(contextMenu);

		insertMenu.setText("Insert");
		insertMenu.setSubMenu(insertSubMenu);

		contextMenu.add(insertMenu);
		insertMenu.addSelectionHandler(new SelectionHandler<Item>() {
			@Override
			public void onSelection(SelectionEvent<Item> event) {
				globalEventBus.fireEvent(new WidgetDesignerViewEvent(
				        WidgetDesignerViewEvent.Action.INSERT));
			}
		});

		editMenu.setText("Edit");
		contextMenu.add(editMenu);
		editMenu.addSelectionHandler(new SelectionHandler<Item>() {
			@Override
			public void onSelection(SelectionEvent<Item> event) {
				globalEventBus.fireEvent(new WidgetDesignerViewEvent(
				        WidgetDesignerViewEvent.Action.EDIT));
			}
		});

		deleteMenu.setText("Delete");
		contextMenu.add(deleteMenu);
		deleteMenu.addSelectionHandler(new SelectionHandler<Item>() {
			@Override
			public void onSelection(SelectionEvent<Item> event) {
				globalEventBus.fireEvent(new WidgetDesignerViewEvent(
				        WidgetDesignerViewEvent.Action.DELETE));
			}
		});

		// layout
		ContentPanel cp = new ContentPanel();
		cp.setWidget(buildingBlockTree);

		BorderLayoutContainer blc = new BorderLayoutContainer();
		BorderLayoutData westData = new BorderLayoutData(0.2);
		westData.setMargins(new Margins(8));
		westData.setCollapsible(true);
		westData.setMinSize(200);
		westData.setSplit(true);
		blc.setWestWidget(cp, westData);

		BorderLayoutData centerData = new BorderLayoutData();
		centerData.setMargins(new Margins(8));
		centerData.setCollapsible(false);
		centerData.setSplit(true);
		blc.setCenterWidget(new ContentPanel(), centerData);

		// add flow panel
		setWidget(blc);
	}

	class BuildingBlockIdentityProvider implements
	        ModelKeyProvider<BuildingBlock> {
		@Override
		public String getKey(BuildingBlock item) {
			return item.getBuildingBlockID();
		}

	}

	private void mergeContextMenu(Menu bbContextMenu) {
		int wCount = bbContextMenu.getWidgetCount();
	}

	@Override
	public void onBuildingBlockRegistration(BuildingBlock bb) {
		MenuItem insertMi = new MenuItem(bb.getBuildingBlockName());
		insertSubMenu.add(insertMi);
    }

	@Override
	public BuildingBlock getSelectedBuildingBlock() {
		return buildingBlockTree.getSelectionModel().getSelectedItem();
	}
}
