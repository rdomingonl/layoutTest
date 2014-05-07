package nl.tc.rd.exp.layouttest.client.event;

import com.google.gwt.event.shared.GwtEvent;

/**
 * Event informing you about life cycle changes off the application
 * 
 * @author raymond
 * 
 */
public class ValidationViewEvent extends GwtEvent<ValidationViewEventHandler> {
    public static final Type<ValidationViewEventHandler> TYPE = new Type<ValidationViewEventHandler>();

    /**
     * Action enum
     */
    public static enum ACTION {
        SEND, CLOSE
    }

    private ACTION action;

    public ValidationViewEvent(ACTION action) {
        this.action = action;
    }

    @Override
    protected void dispatch(ValidationViewEventHandler handler) {
        // if (action == ACTION.SEND) {
        // handler.onSend(this);
        // }
    }

    @Override
    public com.google.gwt.event.shared.GwtEvent.Type<ValidationViewEventHandler> getAssociatedType() {
        return TYPE;
    }
}
