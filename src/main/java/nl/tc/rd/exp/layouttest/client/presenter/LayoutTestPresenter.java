package nl.tc.rd.exp.layouttest.client.presenter;

import nl.tc.rd.exp.layouttest.client.event.ValidationViewEvent;
import nl.tc.rd.exp.layouttest.client.event.ValidationViewEventHandler;
import nl.tc.rd.exp.layouttest.client.view.LayoutTestView;

import com.google.gwt.event.shared.EventBus;

/**
 * Display the Validation sample.
 */
public class LayoutTestPresenter implements ValidationViewEventHandler {

    private final Display view;
    private final EventBus globalEventBus;

    public interface Display {
        public void setGlobalEventBus(EventBus eventBus);
    }

    public LayoutTestPresenter(EventBus globalEventBus, final LayoutTestView view) {
        // store parameters
        this.globalEventBus = globalEventBus;
        this.view = view;
        attachToEventBus();
    }

    private void attachToEventBus() {
        globalEventBus.addHandler(ValidationViewEvent.TYPE, this);
    }
}
