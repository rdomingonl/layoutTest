package nl.tc.rd.exp.layouttest.client.mvp.event;

import com.google.gwt.event.shared.GwtEvent;

/**
 * Event informing you about life cycle changes off the application
 * 
 * @author raymond
 * 
 */
public class WidgetDesignerViewEvent extends GwtEvent<WidgetDesignerViewEventHandler> {
    public static final Type<WidgetDesignerViewEventHandler> TYPE = new Type<WidgetDesignerViewEventHandler>();

    /**
     * Action enum
     */
    public static enum Action {
    	INSERT, EDIT, DELETE
    }

    private Action action;

    public WidgetDesignerViewEvent(Action action) {
        this.action = action;
    }

    @Override
    protected void dispatch(WidgetDesignerViewEventHandler handler) {
        // if (action == ACTION.SEND) {
        // handler.onSend(this);
        // }
    }

    @Override
    public com.google.gwt.event.shared.GwtEvent.Type<WidgetDesignerViewEventHandler> getAssociatedType() {
        return TYPE;
    }
}
