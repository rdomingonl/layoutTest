package nl.tc.rd.exp.layouttest.client.event;

import nl.tc.rd.exp.layouttest.shared.buildingblock.BuildingBlock;

import com.google.gwt.event.shared.GwtEvent;

public class BuildingBlockLifeCycleEvent extends
        GwtEvent<BuildingBlockLifeCycleEventHandler> {

	public static final Type<BuildingBlockLifeCycleEventHandler> TYPE = new Type<BuildingBlockLifeCycleEventHandler>();
	BuildingBlock bb;

	public enum Action {
		REGISTER
	}

	Action action;

	public BuildingBlockLifeCycleEvent(BuildingBlock bb, Action action) {
		super();
		this.bb=bb;
		this.action = action;
	}

	public BuildingBlock getBuildingBlock() {
		return bb;
	}

	@Override
	public com.google.gwt.event.shared.GwtEvent.Type<BuildingBlockLifeCycleEventHandler> getAssociatedType() {
		return TYPE;
	}

	@Override
	protected void dispatch(BuildingBlockLifeCycleEventHandler handler) {
		switch (action) {
		case REGISTER:
			handler.onBuildingBlockRegistration(bb);
			break;
		default:
		}
	}

}
