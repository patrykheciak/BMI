package com.gmail.patrykheciak.lab1.bmi.BMIutils;

public class CountBMI implements ICountBMI {

    private static final float MIN_MASS = 10f;
    private static final float MAX_MASS = 250f;
    private static final float MIN_HEIGHT = 0.5f;
    private static final float MAX_HEIGHT = 2.5f;

    @Override
    public boolean isMassValid(float mass) {
        return MIN_MASS <= mass && mass <= MAX_MASS;
    }

    @Override
    public boolean isHeightValid(float height) {
        return MIN_HEIGHT <= height && height <= MAX_HEIGHT;
    }

    @Override
    public float countBMI(float mass, float height) throws IllegalArgumentException {
        if (isMassValid(mass) && isHeightValid(height))
            return mass/height/height;
        else
            throw new IllegalArgumentException("Mass or Height is out of bounds");
    }
}
