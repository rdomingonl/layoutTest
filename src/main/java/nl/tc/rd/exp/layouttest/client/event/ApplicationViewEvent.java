package nl.tc.rd.exp.layouttest.client.event;

import com.google.gwt.event.shared.GwtEvent;

/**
 * Event informing you about life cycle changes off the application
 * 
 * @author raymond
 * 
 */
public class ApplicationViewEvent extends GwtEvent<ApplicationViewEventHandler> {
    public static final Type<ApplicationViewEventHandler> TYPE = new Type<ApplicationViewEventHandler>();

    /**
     * Action enum
     */
    public static enum ACTION {
        SEND, CLOSE
    }

    private ACTION action;

    public ApplicationViewEvent(ACTION action) {
        this.action = action;
    }

    @Override
    protected void dispatch(ApplicationViewEventHandler handler) {
        // if (action == ACTION.SEND) {
        // handler.onSend(this);
        // }
    }

    @Override
    public com.google.gwt.event.shared.GwtEvent.Type<ApplicationViewEventHandler> getAssociatedType() {
        return TYPE;
    }
}
