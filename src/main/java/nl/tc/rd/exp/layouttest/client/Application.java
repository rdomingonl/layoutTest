package nl.tc.rd.exp.layouttest.client;

import nl.tc.rd.exp.layouttest.client.mvp.presenter.PageDesignerPresenter;
import nl.tc.rd.exp.layouttest.client.mvp.view.PageDesignerView;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.event.shared.SimpleEventBus;
import com.google.gwt.user.client.ui.RootPanel;
import com.sencha.gxt.widget.core.client.container.Viewport;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class Application implements EntryPoint {
	/**
	 * This is the entry point method.
	 */
	public void onModuleLoad() {
		EventBus globalEventBus = new SimpleEventBus();

		// create views
		PageDesignerView view = new PageDesignerView(globalEventBus);

		// create presenters
		PageDesignerPresenter presenter = new PageDesignerPresenter(
		        globalEventBus, view);

		// use all available space for first container
		Viewport vp = new Viewport();
//		vp.setWidget(new TextButton("Foo1234"));
		vp.setWidget(view);
		RootPanel.get().add(vp);

		// GWT.setUncaughtExceptionHandler(new GWT.UncaughtExceptionHandler() {
		// public void onUncaughtException(Throwable e) {
		// Window.alert("Error: " + e.getMessage());
		// e.printStackTrace();
		// com.google.gwt.core.shared.GWT.log(e.getMessage(), e);
		// }
		// });
	}
}
