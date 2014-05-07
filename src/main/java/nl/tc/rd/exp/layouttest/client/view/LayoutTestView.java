package nl.tc.rd.exp.layouttest.client.view;

import java.util.List;

import nl.tc.rd.exp.layouttest.client.images.SenchaImages;
import nl.tc.rd.exp.layouttest.client.presenter.LayoutTestPresenter;
import nl.tc.rd.exp.layouttest.shared.LayoutEntry;

import com.google.gwt.cell.client.ValueUpdater;
import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.NativeEvent;
import com.google.gwt.event.logical.shared.SelectionEvent;
import com.google.gwt.event.logical.shared.SelectionHandler;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.text.shared.SimpleSafeHtmlRenderer;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.sencha.gxt.cell.core.client.SimpleSafeHtmlCell;
import com.sencha.gxt.core.client.ValueProvider;
import com.sencha.gxt.core.client.dom.ScrollSupport.ScrollMode;
import com.sencha.gxt.data.shared.LabelProvider;
import com.sencha.gxt.data.shared.ListStore;
import com.sencha.gxt.data.shared.ModelKeyProvider;
import com.sencha.gxt.data.shared.TreeStore;
import com.sencha.gxt.widget.core.client.Window;
import com.sencha.gxt.widget.core.client.button.ButtonBar;
import com.sencha.gxt.widget.core.client.button.TextButton;
import com.sencha.gxt.widget.core.client.container.FlowLayoutContainer;
import com.sencha.gxt.widget.core.client.container.Viewport;
import com.sencha.gxt.widget.core.client.event.SelectEvent;
import com.sencha.gxt.widget.core.client.event.SelectEvent.SelectHandler;
import com.sencha.gxt.widget.core.client.form.ComboBox;
import com.sencha.gxt.widget.core.client.form.FieldLabel;
import com.sencha.gxt.widget.core.client.menu.Item;
import com.sencha.gxt.widget.core.client.menu.Menu;
import com.sencha.gxt.widget.core.client.menu.MenuItem;
import com.sencha.gxt.widget.core.client.tree.Tree;

/**
 * Display the Validation sample.
 */
public class LayoutTestView extends Composite implements
        LayoutTestPresenter.Display {

	class LayoutEntryKeyProvider implements ModelKeyProvider<LayoutEntry> {
		@Override
		public String getKey(LayoutEntry item) {
			return item.getKey();
		}
	}

	class ScrollModeKeyProvider implements ModelKeyProvider<ScrollMode> {
		@Override
		public String getKey(ScrollMode sm) {
			return sm.value();
		}
	}

	// /////////////////
	// widgets
	// /////////////////
	Tree<LayoutEntry, String> layoutEntryTree;
	TreeStore<LayoutEntry> store;
	MenuItem insertFlowLayoutContainer;
	MenuItem edit;
	MenuItem remove;

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
	LayoutEntry rootEntry;

	// /////////////////
	// constructor
	// /////////////////
	public LayoutTestView() {
		super();
		createWidgets();
		layoutWidgets();
	}

	private void createWidgets() {
		// create the layoutEntryTree
		layoutEntryTree = createLayoutEntryTree();

	}

	private Tree<LayoutEntry, String> createLayoutEntryTree() {
		// tree
		store = new TreeStore<LayoutEntry>(new LayoutEntryKeyProvider());

		rootEntry = new LayoutEntry(null);
		rootEntry.setKey("" + System.currentTimeMillis());
		rootEntry.setName("/");
		store.add(rootEntry);

		// todo fill tree store
		Tree<LayoutEntry, String> tree = new Tree<LayoutEntry, String>(store,
		        new ValueProvider<LayoutEntry, String>() {

			        @Override
			        public String getValue(LayoutEntry object) {
				        return object.getName();
			        }

			        @Override
			        public void setValue(LayoutEntry object, String value) {
			        }

			        @Override
			        public String getPath() {
				        return "name";
			        }
		        });
		tree.setWidth(300);

		Menu contextMenu = new Menu();

		insertFlowLayoutContainer = new MenuItem();
		insertFlowLayoutContainer.setText("Insert FlowLayoutContainer");
		insertFlowLayoutContainer.setIcon(SenchaImages.INSTANCE.add());
		insertFlowLayoutContainer
		        .addSelectionHandler(new SelectionHandler<Item>() {
			        @Override
			        public void onSelection(SelectionEvent<Item> event) {
				        final LayoutEntry parentEntry = layoutEntryTree
				                .getSelectionModel().getSelectedItem();
				        if (parentEntry != null
				                && parentEntry.isAllowsMultipleChildren()
				                || parentEntry.getChildCount() == 0) {
					        final FlowLayoutContainerCreationDialog lcd = new FlowLayoutContainerCreationDialog();
					        lcd.getOkButton().addSelectHandler(
					                new SelectHandler() {
						                @Override
						                public void onSelect(SelectEvent event) {
							                LayoutEntry entry = new LayoutEntry(
							                        parentEntry);
							                entry.setKey(""
							                        + System.currentTimeMillis());

							                entry.setName("FlowLayoutContainer");
							                entry.setAllowsMultipleChildren(true);
							                lcd.applyValues(entry);

							                parentEntry.addChild(entry);
							                store.add(parentEntry, entry);
							                lcd.hide();
						                }
					                });
					        lcd.show();

				        }
			        }
		        });

		contextMenu.add(insertFlowLayoutContainer);

		edit = new MenuItem();
		edit.setText("Edit");
		edit.setIcon(SenchaImages.INSTANCE.form());
		edit.addSelectionHandler(new SelectionHandler<Item>() {

			@Override
			public void onSelection(SelectionEvent<Item> event) {
				final LayoutEntry layoutEntry = layoutEntryTree.getSelectionModel()
				        .getSelectedItem();
				if (layoutEntry != null && layoutEntry != rootEntry) {
					if (layoutEntry.getName().equals("FlowLayoutContainer")) {
						final FlowLayoutContainerCreationDialog lcd = new FlowLayoutContainerCreationDialog();
						lcd.setData(layoutEntry);
						lcd.getOkButton().addSelectHandler(new SelectHandler() {
							@Override
							public void onSelect(SelectEvent event) {
								lcd.applyValues(layoutEntry);
								lcd.hide();
							}
						});
						lcd.show();
					}
				}
			}

		});

		contextMenu.add(edit);

		remove = new MenuItem();
		remove.setText("Remove Selected");
		remove.setIcon(SenchaImages.INSTANCE.delete());
		remove.addSelectionHandler(new SelectionHandler<Item>() {

			@Override
			public void onSelection(SelectionEvent<Item> event) {
				LayoutEntry layoutEntry = layoutEntryTree.getSelectionModel()
				        .getSelectedItem();
				if (layoutEntry != null && layoutEntry != rootEntry) {
					layoutEntry.getParent().removeChild(layoutEntry);
					store.removeChildren(layoutEntry);
					store.remove(layoutEntry);
					removeEntry(layoutEntry);
				}
			}

		});

		contextMenu.add(remove);

		tree.setContextMenu(contextMenu);

		SimpleSafeHtmlCell<String> cell = new SimpleSafeHtmlCell<String>(
		        SimpleSafeHtmlRenderer.getInstance(), "click") {
			@Override
			public void onBrowserEvent(Context context, Element parent,
			        String value, NativeEvent event,
			        ValueUpdater<String> valueUpdater) {
				super.onBrowserEvent(context, parent, value, event,
				        valueUpdater);
				LayoutEntry layoutEntry = layoutEntryTree.getSelectionModel()
				        .getSelectedItem();
				insertFlowLayoutContainer
				        .setEnabled(layoutEntry != null
				                && (layoutEntry.isAllowsMultipleChildren() || layoutEntry
				                        .getChildCount() == 0));
				remove.setEnabled(layoutEntry != null
				        && layoutEntry != rootEntry
				        && layoutEntry.getChildCount() > 0);
			}
		};
		tree.setCell(cell);
		return tree;
	}

	private void removeEntry(LayoutEntry layoutEntry) {
		layoutEntry.setParent(null);
		List<LayoutEntry> children = layoutEntry.getChildren();
		for (LayoutEntry le : children) {
			layoutEntry.removeChild(le);
			removeEntry(le);
		}
	}

	private void layoutWidgets() {
		// create viewPanel (outer panel using max available space)
		Viewport viewPanel = new Viewport();
		viewPanel.setEnableScroll(true);

		// create flow panel (dummy layout)
		FlowPanel fp = new FlowPanel();
		fp.add(layoutEntryTree);

		// add flow panel
		viewPanel.setWidget(fp);

		// add viewPanel
		initWidget(viewPanel);
	}

	@Override
	public void setGlobalEventBus(EventBus globalEventBus) {
		this.globalEventBus = globalEventBus;
	}

	public class FlowLayoutContainerCreationDialog extends Window {
		ComboBox<ScrollMode> scrollModeCombo;
		TextButton okButton;
		TextButton cancelButton;

		public FlowLayoutContainerCreationDialog() {
			super();
			setModal(true);
			setResizable(false);
			setClosable(true);

			ListStore<ScrollMode> store = new ListStore<ScrollMode>(
			        new ScrollModeKeyProvider());
			store.add(ScrollMode.ALWAYS);
			store.add(ScrollMode.AUTO);
			store.add(ScrollMode.NONE);

			scrollModeCombo = new ComboBox<ScrollMode>(store,
			        new LabelProvider<ScrollMode>() {

				        @Override
				        public String getLabel(ScrollMode item) {
					        return item.name();
				        }
			        });
			scrollModeCombo.select(ScrollMode.ALWAYS);
			FlowLayoutContainer flc = new FlowLayoutContainer();

			ButtonBar bb = new ButtonBar();
			
			okButton = new TextButton("Save");
			cancelButton = new TextButton("Cancel");
			
			bb.add(okButton);
			bb.add(cancelButton);
			
			cancelButton.addSelectHandler(new SelectHandler() {
				
				@Override
				public void onSelect(SelectEvent event) {
					hide();
				}
			});
			
			flc.add(new FieldLabel(scrollModeCombo, "ScrollMode"));
			flc.add(bb);
			
			setWidget(flc);
		}

		public void setData(LayoutEntry entry) {
			String scrollModeValue = entry.getData().get("scrollModeValue");
			if (scrollModeValue != null) {
				if (scrollModeValue.equals(ScrollMode.AUTO.value())) {
					scrollModeCombo.setValue(ScrollMode.AUTO);					
				} else if (scrollModeValue.equals(ScrollMode.ALWAYS.value())) {
						scrollModeCombo.setValue(ScrollMode.ALWAYS);
				} else if (scrollModeValue.equals(ScrollMode.NONE.value())) {
					scrollModeCombo.setValue(ScrollMode.NONE);
				}
			}
		}

		public void applyValues(LayoutEntry entry) {
			entry.getData().put("scrollModeValue",
			        scrollModeCombo.getValue().value());
		}

		public TextButton getOkButton() {
			return okButton;
		}

	}

}
