package com.gmail.patrykheciak.lab1.bmi.BMIutils;

public interface ICountBMI {
    boolean isMassValid(float mass);
    boolean isHeightValid(float height);
    float countBMI(float mass, float height);
}
