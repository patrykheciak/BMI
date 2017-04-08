package com.gmail.patrykheciak.lab1.bmi;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.Spinner;

import com.gmail.patrykheciak.lab1.bmi.BMIutils.BMIcalculationResult;
import com.gmail.patrykheciak.lab1.bmi.BMIutils.CountBMI;
import com.gmail.patrykheciak.lab1.bmi.BMIutils.measurment.Height;
import com.gmail.patrykheciak.lab1.bmi.BMIutils.measurment.Mass;

import java.util.Locale;

public class BMICalculationHelper implements AdapterView.OnItemSelectedListener, View.OnClickListener {

    private OnCalculateBMIListener listener;
    private EditText editTextMass;
    private EditText editTextHeight;
    private Context context;
    private Mass mass;
    private Height height;
    private Spinner spnMass, spnHeight;

    public BMICalculationHelper() {
        mass = new Mass();
        height = new Height();
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public void setEditTextMassAndHeight(EditText editTextMass, EditText editTextHeight) {
        this.editTextMass = editTextMass;
        this.editTextHeight = editTextHeight;
    }

    public void setOnBMICalculationHelper(OnCalculateBMIListener listener) {
        this.listener = listener;
    }

    public void setSpinnerMassAndHeight(Spinner m, Spinner h) {
        spnMass = m;
        spnHeight = h;
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        switch (adapterView.getId()) {
            case R.id.sp_mass_unit:
                spinnerMassClicked(i);
                break;
            case R.id.sp_height_unit:
                spinnerHeightClicked(i);
                break;
        }

    }

    private void spinnerHeightClicked(int itemIndex) {
        updateHeightValue();
        if (itemIndex == 0) {
            updateHeightUnit(Height.Unit.CM);
        } else {
            updateHeightUnit(Height.Unit.M);
        }
        editTextHeight.requestFocus();
        editTextHeight.setSelection(editTextHeight.getText().length());
    }

    private void spinnerMassClicked(int itemIndex) {
        updateMassValue();
        if (itemIndex == 0) {
            updateMassUnit(Mass.Unit.KG);
        } else {
            updateMassUnit(Mass.Unit.LB);
        }
        editTextMass.requestFocus();
        editTextMass.setSelection(editTextMass.getText().length());
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    @Override
    public void onClick(View view) {
        calculateAndDisplayBMI();
    }

    private void updateMassValue() {
        String text = editTextMass.getText().toString();
        if (text.length() != 0) {
            float val = Float.parseFloat(text);
            mass.setMass(val);
        }
    }

    private void updateMassUnit(Mass.Unit unit) {
        mass.setUnit(unit);
        if (editTextMass.getText().length() != 0)
            editTextMass.setText(String.format(Locale.US, "%.2f", mass.getMass()));
    }

    private void updateHeightValue() {
        String text = editTextHeight.getText().toString();
        if (text.length() != 0) {
            float val = Float.parseFloat(text);
            height.setHeight(val);
        }
    }

    private void updateHeightUnit(Height.Unit unit) {
        height.setUnit(unit);
        if (editTextHeight.getText().length() != 0)
            editTextHeight.setText(String.format(Locale.US, "%.2f", height.getHeight()));
    }

    public void calculateAndDisplayBMI() {
        updateMassValue();
        updateHeightValue();

        CountBMI cbmi = new CountBMI();
        float m = mass.getMassKG();
        float h = height.getHeightM();

        BMIcalculationResult result = new BMIcalculationResult();

        if (cbmi.isMassValid(m) && cbmi.isHeightValid(h)) {
            float bmi = cbmi.countBMI(m, h);
            String description;
            int color;

            if (bmi < 18.5) {
                description = context.getResources().getString(R.string.underweight);
                color = context.getColor(R.color.colorIncorrect);
            } else if (bmi < 25) {
                description = context.getResources().getString(R.string.normal_range);
                color = context.getColor(R.color.colorCorrect);
            } else {
                description = context.getResources().getString(R.string.overweight);
                color = context.getColor(R.color.colorIncorrect);
            }
            result.height = height;
            result.mass = mass;

            result.BMI = String.format(Locale.US, "%.2f", bmi);
            result.description = description;
            result.color = color;
            result.success = true;
        } else {
            result.BMI = context.getString(R.string.illegal_input);
            result.description = context.getString(R.string.fix_it_and_try_again);
            result.color = context.getColor(R.color.colorIncorrect);
            result.success = false;
        }

        listener.onNewBMIvalueCalculated(result);
    }

    public void setMass(Mass mass) {
        this.mass = mass;
    }

    public void setHeight(Height height) {
        this.height = height;
    }
}
