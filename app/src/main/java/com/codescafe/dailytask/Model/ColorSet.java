package com.codescafe.dailytask.Model;

import java.io.Serializable;

public class ColorSet implements Serializable {

    private int colorId;
    private int colorResId;

    public ColorSet() {
    }

    public ColorSet(int colorId, int colorResId) {
        this.colorId = colorId;
        this.colorResId = colorResId;
    }

    public int getColorId() {
        return colorId;
    }

    public void setColorId(int colorId) {
        this.colorId = colorId;
    }

    public int getColorResId() {
        return colorResId;
    }

    public void setColorResId(int colorResId) {
        this.colorResId = colorResId;
    }
}
