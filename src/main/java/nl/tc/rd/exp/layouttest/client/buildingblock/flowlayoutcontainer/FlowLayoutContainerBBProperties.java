/**
 * Sencha GXT 3.1.0-beta - Sencha for GWT
 * Copyright(c) 2007-2014, Sencha, Inc.
 * licensing@sencha.com
 *
 * http://www.sencha.com/products/gxt/license/
 */
package nl.tc.rd.exp.layouttest.client.buildingblock.flowlayoutcontainer;

import com.google.gwt.editor.client.Editor.Path;
import com.sencha.gxt.core.client.ValueProvider;
import com.sencha.gxt.data.shared.ModelKeyProvider;
import com.sencha.gxt.data.shared.PropertyAccess;


public interface FlowLayoutContainerBBProperties extends PropertyAccess<FlowLayoutContainerBB> {
    @Path("key")
    ModelKeyProvider<FlowLayoutContainerBB> key();

    ValueProvider<FlowLayoutContainerBB, String> name();
}