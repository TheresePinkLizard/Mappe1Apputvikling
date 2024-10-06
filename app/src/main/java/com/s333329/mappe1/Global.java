package com.s333329.mappe1;

import android.app.Application;

public class Global extends Application {
    private String antallSpill = "";
    private int antallDyr = 0;

    public String getAntallSpill() {
        return antallSpill;
    }

    public void setAntallSpill(String someVariable) {
        this.antallSpill = someVariable;
    }

    public int getAntallDyr() {
        return antallDyr;
    }

    public void setAntallDyr(int someVariable) {
        this.antallDyr = someVariable;
    }
}
