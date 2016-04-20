package com.jiayusoft.mobile.kenli.utils.eventbus.event;

/**
 * Created by Hi on 2016-1-17.
 */
public class SingleChooseEvent {
    int viewID;
    String selectedID;
    String selectedItem;

    public SingleChooseEvent(int viewID, String selectedID, String selectedItem) {
        this.viewID = viewID;
        this.selectedID = selectedID;
        this.selectedItem = selectedItem;
    }

    public int getViewID() {
        return viewID;
    }

    public String getSelectedID() {
        return selectedID;
    }

    public String getSelectedItem() {
        return selectedItem;
    }
}
