package com.gmail.patrykheciak.lab1.bmi;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.gmail.patrykheciak.lab1.bmi.BMIutils.BMIcalculationResult;
import com.gmail.patrykheciak.lab1.bmi.BMIutils.measurment.Height;
import com.gmail.patrykheciak.lab1.bmi.BMIutils.measurment.Mass;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements OnCalculateBMIListener {

    private final String BMI_LABEL_VISIBILITY = "bmi_label_visibility";
    private final String BMI_VALUE = "bmi_value";
    private final String BMI_VALUE_COLOR = "bmi_value_color";
    private final String BMI_DESCRIPTION = "bmi_description";
    private final String HEIGHT = "et_height";
    private final String MASS = "et_mass";
    private final String HEIGHT_UNIT = "height_unit";
    private final String MASS_UNIT = "mass_unit";
    private final String SHARED_PREFS_CONTAINS_VALID_BMI = "shared_prefs_contains_valid_bmi";
    @BindView(R.id.et_height)
    EditText etHeight;
    @BindView(R.id.et_mass)
    EditText etMass;
    @BindView(R.id.btn_count)
    Button btnCalculate;
    @BindView(R.id.tv_bmi_value)
    TextView tvResult;
    @BindView(R.id.tv_bmi_description)
    TextView tvBMIdescription;
    @BindView(R.id.tv_bmi_label)
    TextView tvBMIlabel;
    @BindView(R.id.sp_height_unit)
    Spinner spnHeight;
    @BindView(R.id.sp_mass_unit)
    Spinner spnMass;
    private BMICalculationHelper bmiCalculationHelper;
    private BMIcalculationResult result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        bmiCalculationHelper = new BMICalculationHelper();
        bmiCalculationHelper.setOnBMICalculationHelper(this);
        bmiCalculationHelper.setContext(getApplicationContext());
        bmiCalculationHelper.setEditTextMassAndHeight(etMass, etHeight);
        bmiCalculationHelper.setSpinnerMassAndHeight(spnMass, spnHeight);

        spnMass.setSelection(0, false); // prevent calling initial onItemSelected
        spnMass.setOnItemSelectedListener(bmiCalculationHelper);
        spnHeight.setSelection(0, false); // prevent calling initial onItemSelected
        spnHeight.setOnItemSelectedListener(bmiCalculationHelper);

        btnCalculate.setOnClickListener(bmiCalculationHelper);
        result = new BMIcalculationResult();

        restoreInstanceState(savedInstanceState);
    }

    private void restoreInstanceState(Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            if (savedInstanceState.getInt(BMI_LABEL_VISIBILITY) == View.VISIBLE)
                tvBMIlabel.setVisibility(View.VISIBLE);
            else
                tvBMIlabel.setVisibility(View.INVISIBLE);
            tvResult.setText(savedInstanceState.getString(BMI_VALUE));
            tvResult.setTextColor(savedInstanceState.getInt(BMI_VALUE_COLOR));
            tvBMIdescription.setText(savedInstanceState.getString(BMI_DESCRIPTION));

            String sMass = savedInstanceState.getString(MASS, "");
            String sHeight = savedInstanceState.getString(HEIGHT, "");

            if (!sMass.isEmpty()) {
                Mass mass = new Mass();
                mass.setMassKG(Float.valueOf(savedInstanceState.getString(MASS))); //
                mass.setUnit(savedInstanceState.getString(MASS_UNIT, "").equalsIgnoreCase("KG")
                        ? Mass.Unit.KG : Mass.Unit.LB);

                bmiCalculationHelper.setMass(mass);
                result.mass = mass;
            }
            if (!sHeight.isEmpty()) {
                Height height = new Height();
                height.setHeight(Float.valueOf(savedInstanceState.getString(HEIGHT)));
                height.setUnit(savedInstanceState.getString(HEIGHT_UNIT, "").equalsIgnoreCase("m")
                        ? Height.Unit.M : Height.Unit.CM);

                bmiCalculationHelper.setHeight(height);
                result.height = height;
            }
        } else {
            readFromPrefs();
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putInt(BMI_LABEL_VISIBILITY, tvBMIlabel.getVisibility());
        outState.putString(BMI_VALUE, tvResult.getText().toString());
        outState.putInt(BMI_VALUE_COLOR, tvResult.getCurrentTextColor());
        outState.putString(BMI_DESCRIPTION, tvBMIdescription.getText().toString());
        outState.putString(HEIGHT, etHeight.getText().toString());
        outState.putString(MASS, etMass.getText().toString());
        if (result.success) {
            outState.putString(HEIGHT_UNIT, result.height.getUnit().name());
            outState.putString(MASS_UNIT, result.mass.getUnit().name());
        }
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onNewBMIvalueCalculated(BMIcalculationResult result) {
        this.result = result;
        updateUiFromResult(true);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_activiti_main, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_save:
                saveToSharedPrefs();
                return true;
            case R.id.menu_share:
                shareBMI();
                return true;
            case R.id.menu_about:
                startActivity(new Intent(this, AboutActivity.class));
                return true;
            case R.id.menu_delete_prefs:
                getPreferences(MODE_PRIVATE).edit().clear().apply();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void shareBMI() {
        if (result.success) {
            bmiCalculationHelper.calculateAndDisplayBMI();

            Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
            sharingIntent.setType("text/plain");
            String shareBodyText = getResources().getString(R.string.my_bmi_is, result.BMI);
            sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT,
                    getString(R.string.i_ve_found_a_way_to_lose_weight));
            sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBodyText);
            startActivity(Intent.createChooser(sharingIntent, null));
        } else {
            Toast.makeText(this, R.string.no_bmi_to_share, Toast.LENGTH_SHORT).show();
        }
    }

    private void saveToSharedPrefs() {
        if (result.success) {
            SharedPreferences.Editor editor = getPreferences(MODE_PRIVATE).edit();

            editor.putBoolean(SHARED_PREFS_CONTAINS_VALID_BMI, result.success);
            editor.putFloat(HEIGHT, result.height.getHeightM());
            editor.putString(HEIGHT_UNIT, result.height.getUnit().name());
            editor.putFloat(MASS, result.mass.getMassKG());
            editor.putString(MASS_UNIT, result.mass.getUnit().name());
            editor.putString(BMI_VALUE, result.BMI);
            editor.putInt(BMI_VALUE_COLOR, result.color);
            editor.putString(BMI_DESCRIPTION, result.description);
            editor.apply();

            Toast.makeText(this, R.string.saved, Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, R.string.no_bmi_to_save, Toast.LENGTH_SHORT).show();
        }
    }

    private void readFromPrefs() {
        SharedPreferences prefs = getPreferences(MODE_PRIVATE);

        if (prefs.getBoolean(SHARED_PREFS_CONTAINS_VALID_BMI, false)) {

            if (result == null)
                result = new BMIcalculationResult();
            result.success = true;

            result.height = new Height();
            result.height.setHeightM(prefs.getFloat(HEIGHT, 0f));
            boolean isM = prefs.getString(HEIGHT_UNIT, "").equals("M");
            result.height.setUnit(isM ? Height.Unit.M : Height.Unit.CM);

            result.mass = new Mass();
            result.mass.setMassKG(prefs.getFloat(MASS, 0f));
            boolean isKg = prefs.getString(MASS_UNIT, "").equals("KG");
            result.mass.setUnit(isKg ? Mass.Unit.KG : Mass.Unit.LB);

            result.BMI = prefs.getString(BMI_VALUE, "");
            result.color = prefs.getInt(BMI_VALUE_COLOR, 0);
            result.description = prefs.getString(BMI_DESCRIPTION, "");

            bmiCalculationHelper.setMass(result.mass);
            bmiCalculationHelper.setHeight(result.height);

            updateUiFromResult(false);
        }


    }

    private void updateUiFromResult(boolean allowUnsuccessful) {

        if (result.success) {
            tvResult.setText(result.BMI);
            tvResult.setTextColor(result.color);
            tvBMIdescription.setText(result.description);
            tvBMIlabel.setVisibility(View.VISIBLE);

            spnMass.setSelection(result.mass.getUnit().name().equals("KG") ? 0 : 1);
            etMass.setText(String.valueOf(result.mass.getMass()));
            spnHeight.setSelection(result.height.getUnit().name().equals("CM") ? 0 : 1);
            etHeight.setText(String.valueOf(result.height.getHeight()));
        } else {
            if (allowUnsuccessful) {
                tvResult.setText(result.BMI);
                tvResult.setTextColor(result.color);
                tvBMIdescription.setText(result.description);
                tvBMIlabel.setVisibility(View.VISIBLE);

            }
        }
    }
}
