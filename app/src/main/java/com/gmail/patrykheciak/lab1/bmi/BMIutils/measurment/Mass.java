package com.gmail.patrykheciak.lab1.bmi.BMIutils.measurment;

public class Mass {

    public enum Unit {
        KG, LB
    }

    private Unit unit;
    private float mass; // in kilograms

    public Mass() {
        unit = Unit.KG;
        mass = 0f;
    }

    public void setUnit(Unit newUnit) {
        unit = newUnit;
    }

    public Unit getUnit() {
        return unit;
    }

    public void setMass(float newMass) {
        if (unit == Unit.KG)
            mass = newMass;
        else
            mass = newMass * 0.45359237f;
    }

    public float getMass() {
        if (unit == Unit.KG)
            return mass;
        else
            return mass / 0.45359237f;
    }

    public float getMassKG() {
        return mass;
    }
    public void setMassKG(float mass) {this.mass = mass;}
}
