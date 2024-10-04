package com.s333329.mappe1;

import android.app.Application;

public class Global extends Application {
    private String antallSpill = "";

    public String getAntallSpill() {
        return antallSpill;
    }

    public void setAntallSpill(String someVariable) {
        this.antallSpill = someVariable;
    }
}
