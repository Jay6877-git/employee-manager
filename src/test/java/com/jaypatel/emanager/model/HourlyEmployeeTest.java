package com.jaypatel.emanager.model;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.math.RoundingMode;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class HourlyEmployeeTest {
    @Test
    void happyPath_monthlyPayIsRateTimesHours_scaled2() {
        HourlyEmployee he = new HourlyEmployee();
        he.setHourlyRate(new BigDecimal("20.00"));
        he.setMonthlyHours(new BigDecimal("160"));

        BigDecimal earnings = he.getEarnings();

        assertEquals(new BigDecimal("3200.00"), earnings);
    }

    @Test
    void zeroHours_resultsInZeroPay() {
        HourlyEmployee he = new HourlyEmployee();
        he.setHourlyRate(new BigDecimal("15.75"));
        he.setMonthlyHours(new BigDecimal("0"));

        assertEquals(new BigDecimal("0.00"), he.getEarnings());
    }

    @Test
    void rounding_halfUpAfterMultiply() {
        HourlyEmployee he = new HourlyEmployee();
        he.setHourlyRate(new BigDecimal("19.99"));
        he.setMonthlyHours(new BigDecimal("173.33"));

        // Multiply first, then HALF_UP to 2dp
        BigDecimal expected = new BigDecimal("19.99")
                .multiply(new BigDecimal("173.33"))
                .setScale(2, RoundingMode.HALF_UP); // 3464.87

        assertEquals(expected, he.getEarnings());
    }

    @Test
    void negativeValues_areClampedToZero() {
        HourlyEmployee he = new HourlyEmployee();
        he.setHourlyRate(new BigDecimal("-10.00"));   // clamp → 0
        he.setMonthlyHours(new BigDecimal("-5.00"));  // clamp → 0

        // getters may have different scale for zero; compare numerically
        assertEquals(0, he.getHourlyRate().compareTo(BigDecimal.ZERO));
        assertEquals(0, he.getMonthlyHours().compareTo(BigDecimal.ZERO));

        // earnings must be exactly 0.00 (scale=2 from getEarnings)
        assertEquals(new BigDecimal("0.00"), he.getEarnings());
    }

    @Test
    void nullValues_treatedAsZero_noExceptions() {
        HourlyEmployee he = new HourlyEmployee();
        he.setHourlyRate(null);               // becomes 0
        he.setMonthlyHours(new BigDecimal("160"));

        // 0 × 160 = 0.00
        assertEquals(new BigDecimal("0.00"), he.getEarnings());

        // also verify null -> 0 clamping via getter compare
        assertEquals(0, he.getHourlyRate().compareTo(BigDecimal.ZERO));
    }
}
