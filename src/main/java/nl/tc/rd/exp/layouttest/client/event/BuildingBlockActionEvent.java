package nl.tc.rd.exp.layouttest.client.event;

import nl.tc.rd.exp.layouttest.shared.buildingblock.BuildingBlock;

import com.google.gwt.event.shared.GwtEvent;

public class BuildingBlockActionEvent extends
        GwtEvent<BuildingBlockActionEventHandler> {

	public static final Type<BuildingBlockActionEventHandler> TYPE = new Type<BuildingBlockActionEventHandler>();
	
	BuildingBlock bb;

	public enum Action {
//		INSERT, EDIT, DELETE
	}

	Action action;

	public BuildingBlockActionEvent(BuildingBlock bb, Action action) {
		super();
		this.action = action;
	}

	public BuildingBlock getBuildingBlock() {
		return bb;
	}

	@Override
	public com.google.gwt.event.shared.GwtEvent.Type<BuildingBlockActionEventHandler> getAssociatedType() {
		return TYPE;
	}

	@Override
	protected void dispatch(BuildingBlockActionEventHandler handler) {
		switch (action) {
//		case INSERT:
//			handler.onInsertBuildingBlock(bb);
//			break;
//		case EDIT:
//			handler.onEditBuildingBlock(bb);
//			break;
//		case DELETE:
//			handler.onDeleteBuildingBlock(bb);
//			break;
		default :
		}
	}
}
