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
import com.sencha.gxt.widget.core.client.TabPanel;
import com.sencha.gxt.widget.core.client.container.BorderLayoutContainer;
import com.sencha.gxt.widget.core.client.container.BorderLayoutContainer.BorderLayoutData;
import com.sencha.gxt.widget.core.client.container.SimpleContainer;
import com.sencha.gxt.widget.core.client.info.Info;
import com.sencha.gxt.widget.core.client.menu.Item;
import com.sencha.gxt.widget.core.client.menu.Menu;
import com.sencha.gxt.widget.core.client.menu.MenuItem;
import com.sencha.gxt.widget.core.client.selection.SelectionChangedEvent;
import com.sencha.gxt.widget.core.client.selection.SelectionChangedEvent.SelectionChangedHandler;
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

	ContentPanel previewPanel = new ContentPanel();
	TabPanel widgetPropertiesTabPanel = new TabPanel();

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
				        return "buildingBlockName";
			        }
		        });
		SimpleSafeHtmlCell<String> cell = new SimpleSafeHtmlCell<String>(
		        SimpleSafeHtmlRenderer.getInstance(), "click") {

			@Override
			public void onBrowserEvent(Context context,
			        com.google.gwt.dom.client.Element parent, String value,
			        NativeEvent event, ValueUpdater<String> valueUpdater) {
				super.onBrowserEvent(context, parent, value, event,
				        valueUpdater);
			}
		};
		buildingBlockTree.setCell(cell);

		buildingBlockTree.getSelectionModel().addSelectionChangedHandler(
		        new SelectionChangedHandler<BuildingBlock>() {
			        @Override
			        public void onSelectionChanged(
			                SelectionChangedEvent<BuildingBlock> event) {
				        updateContextMenu();

				        while (widgetPropertiesTabPanel.getWidgetCount() > 0) {
					        widgetPropertiesTabPanel.remove(0);
				        }

				        BuildingBlock selectedBuildingBlock = getSelectedBuildingBlock();
				        if (selectedBuildingBlock instanceof BuildingBlockContainer) {
					        BuildingBlockContainer bbc = (BuildingBlockContainer) selectedBuildingBlock;
					        ContentPanel layoutContainerProperties = new ContentPanel();
					        widgetPropertiesTabPanel.add(
					                bbc.getPropertiesWidget(), "Properties");
				        }
			        }
		        });

		// buildingBlockTree context menu
		buildingBlockTree.setContextMenu(contextMenu);

		insertMenu.setText("Insert");
		insertMenu.setSubMenu(insertSubMenu);

		contextMenu.add(insertMenu);

		editMenu.setText("Edit");
		contextMenu.add(editMenu);
		editMenu.addSelectionHandler(new SelectionHandler<Item>() {
			@Override
			public void onSelection(SelectionEvent<Item> event) {
				globalEventBus.fireEvent(new WidgetDesignerViewEvent(
				        WidgetDesignerViewEvent.Action.EDIT,
				        getSelectedBuildingBlock()));
			}
		});

		deleteMenu.setText("Delete");
		contextMenu.add(deleteMenu);
		deleteMenu.addSelectionHandler(new SelectionHandler<Item>() {
			@Override
			public void onSelection(SelectionEvent<Item> event) {
				globalEventBus.fireEvent(new WidgetDesignerViewEvent(
				        WidgetDesignerViewEvent.Action.DELETE,
				        getSelectedBuildingBlock()));
			}
		});

		// layout
		ContentPanel leftOuterContentPanel = new ContentPanel();

		BorderLayoutContainer leftBlc = new BorderLayoutContainer();

		ContentPanel cpTree = new ContentPanel();
		cpTree.setHeaderVisible(false);
		cpTree.setWidget(buildingBlockTree);

		BorderLayoutData leftBlcData1 = new BorderLayoutData();
		leftBlcData1.setMargins(new Margins(8));
		leftBlcData1.setCollapsible(false);
		leftBlcData1.setSplit(true);
		leftBlc.setCenterWidget(cpTree, leftBlcData1);

		ContentPanel propertiesTabCP = new ContentPanel();
		propertiesTabCP.setHeaderVisible(false);
		BorderLayoutData leftBlcData2 = new BorderLayoutData();
		leftBlcData2.setMargins(new Margins(8));
		leftBlcData2.setCollapsible(true);
		leftBlcData2.setSplit(true);
		leftBlcData2.setCollapseMini(true);
		leftBlcData2.setSize(250);
		leftBlc.setSouthWidget(propertiesTabCP, leftBlcData2);

		// widgetPropertiesTabPanel.add(new HTML("Foo1"),"foo1");
		propertiesTabCP.setWidget(widgetPropertiesTabPanel);
		leftOuterContentPanel.setWidget(leftBlc);

		BorderLayoutContainer blc = new BorderLayoutContainer();
		BorderLayoutData westData = new BorderLayoutData(0.2);
		westData.setMargins(new Margins(8));
		westData.setCollapsible(true);
		westData.setMinSize(200);
		westData.setSplit(true);
		blc.setWestWidget(leftOuterContentPanel, westData);

		BorderLayoutData centerData = new BorderLayoutData();
		centerData.setMargins(new Margins(8));
		centerData.setCollapsible(false);
		centerData.setSplit(true);
		blc.setCenterWidget(previewPanel, centerData);

		previewPanel.setWidget(rootBuildingBlock.getWidget());
		// add flow panel
		setWidget(blc);
	}

	private void updateContextMenu() {
		BuildingBlock selection = buildingBlockTree.getSelectionModel()
		        .getSelectedItem();

		// insert
		if (selection instanceof BuildingBlockContainer) {
			BuildingBlockContainer bbc = (BuildingBlockContainer) selection;
			insertMenu
			        .setEnabled(bbc.isInsertSupported() || !bbc.hasChildren());
		} else {
			// no container selected, can't insert
			insertMenu.setEnabled(false);
		}

		// edit
		editMenu.setEnabled(!(selection instanceof RootContainerBB));

		// delete
		deleteMenu.setEnabled(!(selection instanceof RootContainerBB));
	}

	class BuildingBlockIdentityProvider implements
	        ModelKeyProvider<BuildingBlock> {
		@Override
		public String getKey(BuildingBlock item) {
			return item.getBuildingBlockID();
		}

	}

	@Override
	public void onBuildingBlockRegistration(final BuildingBlock bb) {
		MenuItem insertMi = new MenuItem(bb.getBuildingBlockName());
		insertMi.addSelectionHandler(new SelectionHandler<Item>() {
			@Override
			public void onSelection(SelectionEvent<Item> event) {
				BuildingBlock parent = getSelectedBuildingBlock();
				BuildingBlock newBB = bb.createNewInstance();
				newBB.setBuildingBlockParent(parent);

				globalEventBus.fireEvent(new WidgetDesignerViewEvent(
				        WidgetDesignerViewEvent.Action.INSERT, newBB));

				updateContextMenu();
			}
		});

		insertSubMenu.add(insertMi);
	}

	@Override
	public BuildingBlock getSelectedBuildingBlock() {
		return buildingBlockTree.getSelectionModel().getSelectedItem();
	}

	@Override
	public void addBuldingBlock(BuildingBlock bb) {
		Info.display("addBuldingBlock", "addBuldingBlock:" + bb);
		BuildingBlockContainer bbParentContainer = (BuildingBlockContainer) bb
		        .getBuildingBlockParent();
		bbParentContainer.addChild(bb);
		buildingBlockStore.add(bb.getBuildingBlockParent(), bb);
	}
}
