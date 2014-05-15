package nl.tc.rd.exp.layouttest.client.mvp.view;

import nl.tc.rd.exp.layouttest.client.buildingblock.flowlayoutcontainer.FlowLayoutContainerBB;
import nl.tc.rd.exp.layouttest.client.images.SenchaImages;
import nl.tc.rd.exp.layouttest.client.mvp.presenter.PageDesignerPresenter;
import nl.tc.rd.exp.layouttest.shared.buildingblock.common.BuildingBlockBase;

import com.google.gwt.cell.client.ValueUpdater;
import com.google.gwt.dom.client.NativeEvent;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.text.shared.SimpleSafeHtmlRenderer;
import com.sencha.gxt.cell.core.client.SimpleSafeHtmlCell;
import com.sencha.gxt.core.client.ValueProvider;
import com.sencha.gxt.core.client.util.Margins;
import com.sencha.gxt.data.shared.ModelKeyProvider;
import com.sencha.gxt.data.shared.TreeStore;
import com.sencha.gxt.widget.core.client.ContentPanel;
import com.sencha.gxt.widget.core.client.container.BorderLayoutContainer;
import com.sencha.gxt.widget.core.client.container.BorderLayoutContainer.BorderLayoutData;
import com.sencha.gxt.widget.core.client.container.SimpleContainer;
import com.sencha.gxt.widget.core.client.menu.Menu;
import com.sencha.gxt.widget.core.client.menu.MenuItem;
import com.sencha.gxt.widget.core.client.tree.Tree;

/**
 * Display the Validation sample.
 */
public class PageDesignerView extends SimpleContainer implements
        PageDesignerPresenter.Display {

	class BuildingBlockIdentityProvider implements
	        ModelKeyProvider<BuildingBlockBase> {
		@Override
		public String getKey(BuildingBlockBase item) {
			return item.getIdentifier();
		}

	}

	// /////////////////
	// widgets
	// /////////////////
	Tree<BuildingBlockBase, String> buildingBlockTree;
	TreeStore<BuildingBlockBase> buildingBlockStore;
	MenuItem insertMenu = new MenuItem();
	MenuItem edit = new MenuItem();
	MenuItem remove = new MenuItem();

	// /////////////////
	// events
	// /////////////////
	private EventBus globalEventBus;

	// /////////////////
	// layout
	// /////////////////

	// /////////////////
	// data
	// /////////////////
	BuildingBlockBase rootBuildingBlock = new FlowLayoutContainerBB(""
	        + System.currentTimeMillis(), "/");

	// /////////////////
	// constructor
	// /////////////////
	public PageDesignerView(EventBus eb) {
		super();
		this.globalEventBus = eb;

		// create root node
		rootBuildingBlock.setIdentifier("" + System.currentTimeMillis());
		rootBuildingBlock.setName("/");

		// create store
		buildingBlockStore = new TreeStore<BuildingBlockBase>(
		        new BuildingBlockIdentityProvider());
		buildingBlockStore.add(rootBuildingBlock);

		// create tree
		buildingBlockTree = new Tree<BuildingBlockBase, String>(
		        buildingBlockStore,
		        new ValueProvider<BuildingBlockBase, String>() {

			        @Override
			        public String getValue(BuildingBlockBase buildingBlock) {
				        return buildingBlock.getName();
			        }

			        @Override
			        public void setValue(BuildingBlockBase buildingBlock,
			                String value) {
				        buildingBlock.setName(value);
			        }

			        @Override
			        public String getPath() {
				        return "name";
			        }
		        });
		buildingBlockTree.setWidth(300);

		// create the contextMenu
		Menu contextMenu = new Menu();

		insertMenu.setText("Insert FlowLayoutContainer");
		insertMenu.setIcon(SenchaImages.INSTANCE.add());
		contextMenu.add(insertMenu);

		edit.setText("Edit");
		edit.setIcon(SenchaImages.INSTANCE.form());
		contextMenu.add(edit);

		remove.setText("Remove Selected");
		remove.setIcon(SenchaImages.INSTANCE.delete());
		contextMenu.add(remove);

		buildingBlockTree.setContextMenu(contextMenu);

		SimpleSafeHtmlCell<String> cell = new SimpleSafeHtmlCell<String>(
		        SimpleSafeHtmlRenderer.getInstance(), "click") {

			@Override
			public void onBrowserEvent(Context context,
			        com.google.gwt.dom.client.Element parent, String value,
			        NativeEvent event, ValueUpdater<String> valueUpdater) {
				super.onBrowserEvent(context, parent, value, event,
				        valueUpdater);

				BuildingBlockBase selectedBbuildingBlock = buildingBlockTree
				        .getSelectionModel().getSelectedItem();
			}

		};
		buildingBlockTree.setCell(cell);

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

	// private void removeEntry(FlowLayoutContainerBB layoutEntry) {
	// layoutEntry.setParent(null);
	// List<FlowLayoutContainerBB> children = layoutEntry.getChildren();
	// for (FlowLayoutContainerBB le : children) {
	// layoutEntry.removeChild(le);
	// removeEntry(le);
	// }
	// }

	// public class FlowLayoutContainerCreationDialog extends Window {
	// ComboBox<ScrollMode> scrollModeCombo;
	// TextButton okButton;
	// TextButton cancelButton;
	//
	// public FlowLayoutContainerCreationDialog() {
	// super();
	// setModal(true);
	// setResizable(false);
	// setClosable(true);
	//
	// // ListStore<ScrollMode> store = new ListStore<ScrollMode>(
	// // new ScrollModeKeyProvider());
	// // store.add(ScrollMode.ALWAYS);
	// // store.add(ScrollMode.AUTO);
	// // store.add(ScrollMode.NONE);
	// //
	// // scrollModeCombo = new ComboBox<ScrollMode>(store,
	// // new LabelProvider<ScrollMode>() {
	// //
	// // @Override
	// // public String getLabel(ScrollMode item) {
	// // return item.name();
	// // }
	// // });
	// // scrollModeCombo.select(ScrollMode.ALWAYS);
	// FlowLayoutContainer flc = new FlowLayoutContainer();
	//
	// ButtonBar bb = new ButtonBar();
	//
	// okButton = new TextButton("Save");
	// cancelButton = new TextButton("Cancel");
	//
	// bb.add(okButton);
	// bb.add(cancelButton);
	//
	// cancelButton.addSelectHandler(new SelectHandler() {
	//
	// @Override
	// public void onSelect(SelectEvent event) {
	// hide();
	// }
	// });
	//
	// flc.add(new FieldLabel(scrollModeCombo, "ScrollMode"));
	// flc.add(bb);
	//
	// setWidget(flc);
	// }
	//
	// public void setData(FlowLayoutContainerBB entry) {
	// String scrollModeValue = entry.getData().get("scrollModeValue");
	// if (scrollModeValue != null) {
	// if (scrollModeValue.equals(ScrollMode.AUTO.value())) {
	// scrollModeCombo.setValue(ScrollMode.AUTO);
	// } else if (scrollModeValue.equals(ScrollMode.ALWAYS.value())) {
	// scrollModeCombo.setValue(ScrollMode.ALWAYS);
	// } else if (scrollModeValue.equals(ScrollMode.NONE.value())) {
	// scrollModeCombo.setValue(ScrollMode.NONE);
	// }
	// }
	// }
	//
	// public void applyValues(FlowLayoutContainerBB entry) {
	// entry.getData().put("scrollModeValue",
	// scrollModeCombo.getValue().value());
	// }
	//
	// public TextButton getOkButton() {
	// return okButton;
	// }
	//
	// }

}
