package nl.tc.rd.exp.layouttest.client.mvp.event;

import nl.tc.rd.exp.layouttest.shared.buildingblock.BuildingBlock;

import com.google.gwt.event.shared.GwtEvent;

/**
 * Event informing you about life cycle changes off the application
 * 
 * @author raymond
 * 
 */
public class WidgetDesignerViewEvent extends
        GwtEvent<WidgetDesignerViewEventHandler> {
	public static final Type<WidgetDesignerViewEventHandler> TYPE = new Type<WidgetDesignerViewEventHandler>();

	/**
	 * Action enum
	 */
	public static enum Action {
		INSERT, EDIT, DELETE
	}

	private Action action;
	private BuildingBlock bb;

	public WidgetDesignerViewEvent(Action action, BuildingBlock bb) {
		this.action = action;
		this.bb = bb;
	}

	public BuildingBlock getBuildingBlock() {
		return bb;
	}

	@Override
	protected void dispatch(WidgetDesignerViewEventHandler handler) {
		switch (action) {
			case INSERT:
				handler.onInsertBuildingBlock(bb);
				break;
			case EDIT:
				handler.onEditBuildingBlock(bb);
				break;
			case DELETE:
				handler.onDeleteBuildingBlock(bb);
				break;
			default:
		}
	}

	@Override
	public com.google.gwt.event.shared.GwtEvent.Type<WidgetDesignerViewEventHandler> getAssociatedType() {
		return TYPE;
	}
}
