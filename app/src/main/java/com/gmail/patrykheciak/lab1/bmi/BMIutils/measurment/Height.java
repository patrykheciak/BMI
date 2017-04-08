package com.gmail.patrykheciak.lab1.bmi.BMIutils.measurment;

public class Height {


    public enum Unit {
        M, CM
    }

    private Unit unit;

    private float height; // in metres

    public Height() {
        unit = Unit.CM;
        height = 0f;
    }

    public void setUnit(Unit newUnit) {
        unit = newUnit;
    }

    public Unit getUnit() {
        return unit;
    }

    public void setHeight(float newHeight) {
        if (unit == Unit.M)
            height = newHeight;
        else
            height = newHeight / 100f;
    }

    public float getHeight() {
        if (unit == Unit.M)
            return height;
        else
            return height * 100f;
    }

    public float getHeightM() {
        return height;
    }

    public void setHeightM(float height) {this.height = height;}
}
