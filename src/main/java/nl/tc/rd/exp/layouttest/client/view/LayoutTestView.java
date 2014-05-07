package nl.tc.rd.exp.layouttest.client.view;

import java.util.Date;
import java.util.List;

import nl.tc.rd.exp.layouttest.client.images.SenchaImages;
import nl.tc.rd.exp.layouttest.client.presenter.LayoutTestPresenter;
import nl.tc.rd.exp.layouttest.shared.LayoutEntry;

import com.google.gwt.cell.client.ValueUpdater;
import com.google.gwt.core.shared.GWT;
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
import com.sencha.gxt.data.shared.ModelKeyProvider;
import com.sencha.gxt.data.shared.TreeStore;
import com.sencha.gxt.widget.core.client.container.Viewport;
import com.sencha.gxt.widget.core.client.menu.Item;
import com.sencha.gxt.widget.core.client.menu.Menu;
import com.sencha.gxt.widget.core.client.menu.MenuItem;
import com.sencha.gxt.widget.core.client.tree.Tree;

/**
 * Display the Validation sample.
 */
public class LayoutTestView extends Composite implements LayoutTestPresenter.Display {

    class KeyProvider implements ModelKeyProvider<LayoutEntry> {
        @Override
        public String getKey(LayoutEntry item) {
            return item.getKey();
        }
    }

    // /////////////////
    // widgets
    // /////////////////
    Tree<LayoutEntry, String> layoutEntryTree;
    TreeStore<LayoutEntry> store;
    MenuItem insert;
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
        store = new TreeStore<LayoutEntry>(new KeyProvider());

        rootEntry = new LayoutEntry(null);
        rootEntry.setKey("" + System.currentTimeMillis());
        rootEntry.setName("/");
        store.add(rootEntry);

        // todo fill tree store
        Tree<LayoutEntry, String> tree = new Tree<LayoutEntry, String>(store, new ValueProvider<LayoutEntry, String>() {

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

        insert = new MenuItem();
        insert.setText("Insert Item");
        insert.setIcon(SenchaImages.INSTANCE.add());
        insert.addSelectionHandler(new SelectionHandler<Item>() {
            @Override
            public void onSelection(SelectionEvent<Item> event) {
                LayoutEntry layoutEntry = layoutEntryTree.getSelectionModel().getSelectedItem();
                if (layoutEntry != null && layoutEntry.isAllowsMultipleChildren() || layoutEntry.getChildCount() == 0) {
                    LayoutEntry entry = new LayoutEntry(layoutEntry);
                    entry.setAllowsMultipleChildren(true);
                    entry.setKey("" + System.currentTimeMillis());
                    entry.setName("" + new Date());
                    layoutEntry.addChild(entry);
                    store.add(layoutEntry, entry);
                }
            }
        });

        contextMenu.add(insert);

        remove = new MenuItem();
        remove.setText("Remove Selected");
        remove.setIcon(SenchaImages.INSTANCE.delete());
        remove.addSelectionHandler(new SelectionHandler<Item>() {

            @Override
            public void onSelection(SelectionEvent<Item> event) {
                GWT.log("remove item");
                LayoutEntry layoutEntry = layoutEntryTree.getSelectionModel().getSelectedItem();
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

        SimpleSafeHtmlCell<String> cell = new SimpleSafeHtmlCell<String>(SimpleSafeHtmlRenderer.getInstance(), "click") {
            @Override
            public void onBrowserEvent(Context context, Element parent, String value, NativeEvent event,
                    ValueUpdater<String> valueUpdater) {
                super.onBrowserEvent(context, parent, value, event, valueUpdater);
                LayoutEntry layoutEntry = layoutEntryTree.getSelectionModel().getSelectedItem();
                insert.setEnabled(layoutEntry != null
                        && (layoutEntry.isAllowsMultipleChildren() || layoutEntry.getChildCount() == 0));
                remove.setEnabled(layoutEntry != null && layoutEntry != rootEntry && layoutEntry.getChildCount() > 0);
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

}
