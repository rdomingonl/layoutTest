package nl.tc.rd.exp.layouttest.client;

import nl.tc.rd.exp.layouttest.client.presenter.LayoutTestPresenter;
import nl.tc.rd.exp.layouttest.client.view.LayoutTestView;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.event.shared.SimpleEventBus;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.RootPanel;
import com.sencha.gxt.widget.core.client.container.Viewport;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class LayoutTest implements EntryPoint {
    /**
     * This is the entry point method.
     */
    public void onModuleLoad() {
        EventBus globalEventBus = new SimpleEventBus();

        // create views
        LayoutTestView view = new LayoutTestView();

        // create presenters
        LayoutTestPresenter presenter = new LayoutTestPresenter(globalEventBus, view);

        // use all available space for first container
        Viewport vp = new Viewport();
        vp.setWidget(view);
        RootPanel.get().add(vp);

        GWT.setUncaughtExceptionHandler(new GWT.UncaughtExceptionHandler() {
            public void onUncaughtException(Throwable e) {
                Window.alert("Error: " + e.getMessage());
                e.printStackTrace();
                com.google.gwt.core.shared.GWT.log(e.getMessage(), e);
            }
        });
    }
}
