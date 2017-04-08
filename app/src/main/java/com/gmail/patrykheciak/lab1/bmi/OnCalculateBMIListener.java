package com.gmail.patrykheciak.lab1.bmi;

import com.gmail.patrykheciak.lab1.bmi.BMIutils.BMIcalculationResult;

public interface OnCalculateBMIListener {

    void onNewBMIvalueCalculated(BMIcalculationResult result);

}
