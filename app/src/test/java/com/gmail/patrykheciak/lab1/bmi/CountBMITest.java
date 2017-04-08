package com.gmail.patrykheciak.lab1.bmi;

import com.gmail.patrykheciak.lab1.bmi.BMIutils.CountBMI;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;


public class CountBMITest {



    @Test
    public void massLessThanMinIsInvalid() throws Exception {
        // GIVEN
        float testMass = 5;
        // WHEN
        CountBMI countBMI = new CountBMI();
        // THEN
        boolean actual = countBMI.isMassValid(testMass);
        assertFalse(actual);
    }

    @Test
    public void heightLessThanMinIsInvalid() throws Exception {
        // GIVEN
        float testHeight = 0.4f;
        // WHEN
        CountBMI countBMI = new CountBMI();
        // THEN
        boolean actual = countBMI.isHeightValid(testHeight);
        assertFalse(actual);
    }

    @Test
    public void massGreaterThanMaxIsInvalid() throws Exception {
        // GIVEN
        float testMass = 270;
        // WHEN
        CountBMI countBMI = new CountBMI();
        // THEN
        boolean actual = countBMI.isMassValid(testMass);
        assertFalse(actual);
    }

    @Test
    public void heightGreaterThanMaxIsInvalid() throws Exception {
        // GIVEN
        float testHeight = 260;
        // WHEN
        CountBMI countBMI = new CountBMI();
        // THEN
        boolean actual = countBMI.isHeightValid(testHeight);
        assertFalse(actual);
    }

    // sprawdzic czy rzuca illegalArgExc


    @Test
    public void massEqualZeroIsInvalid() throws Exception {
        // GIVEN
        float testMass = 0;
        // WHEN
        CountBMI countBMI = new CountBMI();
        // THEN
        boolean actual = countBMI.isMassValid(testMass);
        assertFalse(actual);
    }

    @Test
    public void heightEqualZeroIsInvalid() throws Exception {
        // GIVEN
        float testHeight = 0.4f;
        // WHEN
        CountBMI countBMI = new CountBMI();
        // THEN
        boolean actual = countBMI.isHeightValid(testHeight);
        assertFalse(actual);
    }

    @Test
    public void massLessThanZeroIsInvalid() throws Exception {
        // GIVEN
        float testMass = -20;
        // WHEN
        CountBMI countBMI = new CountBMI();
        // THEN
        boolean actual = countBMI.isMassValid(testMass);
        assertFalse(actual);
    }

    @Test
    public void heightLessThanZeroIsInvalid() throws Exception {
        // GIVEN
        float testHeight = -0.35f;
        // WHEN
        CountBMI countBMI = new CountBMI();
        // THEN
        boolean actual = countBMI.isHeightValid(testHeight);
        assertFalse(actual);
    }


    @Test
    public void massInBoundsIsValid() throws Exception {
        // GIVEN
        float testMass = 62.4f;
        // WHEN
        CountBMI countBMI = new CountBMI();
        // THEN
        boolean actual = countBMI.isMassValid(testMass);
        assertTrue(actual);
    }

    @Test
    public void heightInBoundsIsValid() throws Exception {
        // GIVEN
        float testHeight = 1.77f;
        // WHEN
        CountBMI countBMI = new CountBMI();
        // THEN
        boolean actual = countBMI.isHeightValid(testHeight);
        assertTrue(actual);
    }

    @Rule
    public final ExpectedException exception = ExpectedException.none();

    @Test
    public void invalidHeightInCountBMIThrowsException() throws Exception {
        // GIVEN
        float testMass = 62f;
        float testHeight = 177f; // valid format is 1.77
        // WHEN
        CountBMI countBMI = new CountBMI();
        // THEN
        exception.expect(IllegalArgumentException.class);
        countBMI.countBMI(testMass, testHeight);
    }

    @Test
    public void invalidMassInCountBMIThrowsException() throws Exception {
        // GIVEN
        float testMass = 253.4f;
        float testHeight = 1.77f; // valid format is 1.77
        // WHEN
        CountBMI countBMI = new CountBMI();
        // THEN
        exception.expect(IllegalArgumentException.class);
        countBMI.countBMI(testMass, testHeight);
    }

    @Test
    public void invalidMassAndHeightInCountBMIThrowsException() throws Exception {
        // GIVEN
        float testMass = -1f;
        float testHeight = 177f; // valid format is 1.77
        // WHEN
        CountBMI countBMI = new CountBMI();
        // THEN
        exception.expect(IllegalArgumentException.class);
        countBMI.countBMI(testMass, testHeight);
    }

    @Test
    public void validMassAndHeightInCountBMIDoesNotThrowException() throws Exception {
        // GIVEN
        float testMass = -1f;
        float testHeight = 177f; // valid format is 1.77
        // WHEN
        CountBMI countBMI = new CountBMI();
        // THEN
        //exception.expect(null);
        exception.expect(IllegalArgumentException.class);
        countBMI.countBMI(testMass, testHeight);
    }

}
