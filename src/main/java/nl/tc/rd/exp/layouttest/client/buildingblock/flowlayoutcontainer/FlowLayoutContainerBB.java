package nl.tc.rd.exp.layouttest.client.buildingblock.flowlayoutcontainer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import nl.tc.rd.exp.layouttest.shared.buildingblock.BuildingBlock;
import nl.tc.rd.exp.layouttest.shared.buildingblock.BuildingBlockContainerBase;

import com.google.gwt.core.client.GWT;
import com.google.gwt.editor.client.Editor.Path;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.widget.client.TextButton;
import com.sencha.gxt.core.client.ValueProvider;
import com.sencha.gxt.data.shared.ListStore;
import com.sencha.gxt.data.shared.ModelKeyProvider;
import com.sencha.gxt.data.shared.PropertyAccess;
import com.sencha.gxt.widget.core.client.container.FlowLayoutContainer;
import com.sencha.gxt.widget.core.client.container.MarginData;
import com.sencha.gxt.widget.core.client.grid.ColumnConfig;
import com.sencha.gxt.widget.core.client.grid.ColumnModel;
import com.sencha.gxt.widget.core.client.grid.Grid;

/**
 * @author raymond
 * 
 */
public class FlowLayoutContainerBB extends BuildingBlockContainerBase {
	private HashMap<String, String> data = new HashMap<String, String>();
	FlowLayoutContainer flc = new FlowLayoutContainer();
	private static final PropDevProperties properties = GWT
	        .create(PropDevProperties.class);

	public FlowLayoutContainerBB() {
		super("FlowLayoutContainer", "FlowLayoutContainer", true);
		flc.setBorders(true);
		flc.add(new TextButton("foo"));
	}

	@Override
	public BuildingBlock createNewInstance() {
		FlowLayoutContainerBB flcbb = new FlowLayoutContainerBB();
		flcbb.setBuildingBlockID("" + System.currentTimeMillis());
		return flcbb;
	}

	public Widget getWidget() {
		return flc;
	}

	@Override
	public void addChild(BuildingBlock bb) {
		super.addChild(bb);
		flc.add(bb.getWidget(), new MarginData(5));
	}

	@Override
	public void removeChild(BuildingBlock bb) {
		super.removeChild(bb);
		flc.remove(bb.getWidget());
	}

	@Override
	public Widget getPropertiesWidget() {
		ListStore<PropDev> store = new ListStore<PropDev>(
		        new ModelKeyProvider<PropDev>() {
			        @Override
			        public String getKey(PropDev pd) {
				        return pd.getKey();
			        }
		        });

		ColumnConfig<PropDev, String> keyColumn = new ColumnConfig<PropDev, String>(
		        properties.key(), 100, "Key");
		ColumnConfig<PropDev, String> valueColumn = new ColumnConfig<PropDev, String>(
		        properties.value(), 100, "Value");

		List<ColumnConfig<PropDev, ?>> l = new ArrayList<ColumnConfig<PropDev, ?>>();
		l.add(keyColumn);
		l.add(valueColumn);
		ColumnModel<PropDev> cm = new ColumnModel<PropDev>(l);
		
		Grid<PropDev> g = new Grid<PropDev>(store,cm);
		g.getView().setAutoExpandColumn(valueColumn);
		return g;
	}

	class PropDev {
		String key;
		String value;

		public String getKey() {
			return key;
		}

		public void setKey(String key) {
			this.key = key;
		}

		public String getValue() {
			return value;
		}

		public void setValue(String value) {
			this.value = value;
		}

	}

	interface PropDevProperties extends PropertyAccess<PropDev> {
		@Path("key")
		ModelKeyProvider<PropDev> id();

		ValueProvider<PropDev, String> key();

		ValueProvider<PropDev, String> value();
	}

}
