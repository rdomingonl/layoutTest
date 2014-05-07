package nl.tc.rd.exp.layouttest.shared;

import java.util.ArrayList;
import java.util.List;

/**
 * @author raymond
 * 
 */
public class LayoutEntry {
    private String key;
    private String name;
    private boolean allowsMultipleChildren = false;
    private List<LayoutEntry> children = new ArrayList<LayoutEntry>();
    private LayoutEntry parent;

    public LayoutEntry(LayoutEntry parent) {
        this.parent = parent;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isAllowsMultipleChildren() {
        return allowsMultipleChildren;
    }

    public void setAllowsMultipleChildren(boolean allowsMultipleChildren) {
        this.allowsMultipleChildren = allowsMultipleChildren;
    }

    public int getChildCount() {
        return children.size();
    }

    public void addChild(LayoutEntry child) {
        children.add(child);
    }

    public void removeChild(LayoutEntry child) {
        children.remove(child);
    }

    public List<LayoutEntry> getChildren() {
        return new ArrayList<LayoutEntry>(children);
    }

    public LayoutEntry getParent() {
        return parent;
    }

    public void setParent(LayoutEntry parent) {
        this.parent = parent;
    }

}
