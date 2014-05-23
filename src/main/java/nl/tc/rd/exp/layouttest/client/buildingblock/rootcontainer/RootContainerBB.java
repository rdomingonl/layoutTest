package nl.tc.rd.exp.layouttest.client.buildingblock.rootcontainer;

import com.google.gwt.user.client.ui.Widget;
import com.sencha.gxt.widget.core.client.container.SimpleContainer;

import nl.tc.rd.exp.layouttest.shared.buildingblock.BuildingBlock;
import nl.tc.rd.exp.layouttest.shared.buildingblock.BuildingBlockContainerBase;

/**
 * @author raymond
 * 
 */
public class RootContainerBB extends BuildingBlockContainerBase {
	SimpleContainer sc = new SimpleContainer();

	public RootContainerBB() {
		super("/", "/", false);
	}

	@Override
	public BuildingBlock createNewInstance() {
		RootContainerBB bb = new RootContainerBB();
		bb.setBuildingBlockID("" + System.currentTimeMillis());
		return bb;
	}

	@Override
	public Widget getWidget() {
		return sc;
	}

	@Override
	public void addChild(BuildingBlock bb) {
		super.addChild(bb);
		sc.add(bb.getWidget());
	}

	@Override
	public void removeChild(BuildingBlock bb) {
		super.removeChild(bb);
		bb.getWidget().removeFromParent();
	}

	@Override
    public Widget getPropertiesWidget() {
	    return new SimpleContainer();
    }

}
