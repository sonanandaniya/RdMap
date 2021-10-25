package com.devpractical.dialog;

import java.io.Serializable;

public class FilterModel implements Serializable {
    private boolean isSelected = false;
    String title =  "";


    public FilterModel( String title) {
        this.title = title;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
