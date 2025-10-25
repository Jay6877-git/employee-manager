package com.jaypatel.emanager.model;

import java.math.BigDecimal;
import java.math.RoundingMode;


/**
 * An {@code Employee} paid by the hour.
 * <p>
 * Monetary values are stored as {@link BigDecimal} with scale 2 (cents) and
 * {@link RoundingMode#HALF_UP}. Negative or null inputs are clamped to {@code 0.00}.
 * </p>
 */
public class HourlyEmployee extends Employee{
    /** Hourly pay rate; never negative; scale = 2. */
    private BigDecimal hourlyRate;

    /** Planned/recorded hours for the current month; never negative; scale = 2. */
    private BigDecimal monthlyHours;

    /** No-arg constructor for frameworks/serialization. */
    public HourlyEmployee() {
    }

    /**
     * Full constructor.
     *
     * @param lastName      employee last name
     * @param firstName     employee first name
     * @param middleInit    middle initial
     * @param birthDate     birthdate (format defined by {@code Employee})
     * @param phoneNumber   phone number
     * @param address       mailing address
     * @param employeeId    internal employee id
     * @param jobTitle      job title
     * @param monthlyHours  hours in the current month; negatives/null → {@code 0.00}; stored with scale 2
     * @param hourlyRate    hourly rate; negatives/null → {@code 0.00}; stored with scale 2
     */
    public HourlyEmployee(String lastName, String firstName, char middleInit, String birthDate, String phoneNumber, Address address, int employeeId, String jobTitle, BigDecimal monthlyHours, BigDecimal hourlyRate) {
        super(lastName, firstName, middleInit, birthDate, phoneNumber, address, employeeId, jobTitle);
        this.monthlyHours = validatedNonNegative(monthlyHours);
        this.hourlyRate = validatedNonNegative(hourlyRate);
    }

    /** @return hours this month (scale 2, never negative). */
    public BigDecimal getMonthlyHours() {
        return monthlyHours;
    }

    /**
     * Sets hours for the current month.
     * <p>Negatives/null become {@code 0.00}. Value stored with scale 2 and HALF_UP rounding.</p>
     *
     * @param monthlyHours hours value
     */
    public void setMonthlyHours(BigDecimal monthlyHours) {
        this.monthlyHours = validatedNonNegative(monthlyHours);
    }

    /** @return hourly rate (scale 2, never negative). */
    public BigDecimal getHourlyRate() {
        return hourlyRate;
    }

    /**
     * Sets the hourly rate.
     * <p>Negatives/null become {@code 0.00}. Value stored with scale 2 and HALF_UP rounding.</p>
     *
     * @param hourlyRate currency amount
     */
    public void setHourlyRate(BigDecimal hourlyRate) {
        this.hourlyRate = validatedNonNegative(hourlyRate);
    }

    /**
     * Monthly earnings = {@code hourlyRate × monthlyHours}, rounded to 2 decimals (HALF_UP).
     * <p>We multiply first, then apply rounding.</p>
     *
     * @return monthly earnings (scale 2)
     */
    @Override
    public BigDecimal getEarnings() {
        return hourlyRate.multiply(monthlyHours).setScale(2,RoundingMode.HALF_UP);
    }

    // ---- Helpers ------------------------------------------------------------

    /** Ensures non-null, clamps negative to 0. */
    private static BigDecimal validatedNonNegative(BigDecimal value) {
        return value == null ? BigDecimal.ZERO.setScale(2,RoundingMode.HALF_UP) : value.max(BigDecimal.ZERO).setScale(2,RoundingMode.HALF_UP);
    }
}
