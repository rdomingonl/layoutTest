package nl.tc.rd.exp.layouttest.client.mvp.presenter;

import nl.tc.rd.exp.layouttest.client.event.ApplicationViewEvent;
import nl.tc.rd.exp.layouttest.client.event.ApplicationViewEventHandler;
import nl.tc.rd.exp.layouttest.client.mvp.view.PageDesignerView;

import com.google.gwt.event.shared.EventBus;

/**
 * Display the Validation sample.
 */
public class PageDesignerPresenter implements ApplicationViewEventHandler {

    private final Display view;
    private final EventBus globalEventBus;

    public interface Display {
    }

    public PageDesignerPresenter(EventBus globalEventBus, final PageDesignerView view) {
        // store parameters
        this.globalEventBus = globalEventBus;
        this.view = view;
        attachToEventBus();
    }

    private void attachToEventBus() {
        globalEventBus.addHandler(ApplicationViewEvent.TYPE, this);
    }
}
