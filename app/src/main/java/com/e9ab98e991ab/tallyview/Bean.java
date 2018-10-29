package com.e9ab98e991ab.tallyview;

import android.databinding.Bindable;

public class Bean {
    public int number;

    public Bean(int number) {
        this.number = number;
    }

    public int getNumber() {
        return number;
    }
    public void setNumber(int number) {
        this.number = number;
    }
}
