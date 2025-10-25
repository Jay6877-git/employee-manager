package com.jaypatel.emanager.model;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * An {@code Employee} paid a fixed annual salary.
 * <p>
 * Monetary values are stored as {@link BigDecimal} with scale 2 (cents) and
 * {@link RoundingMode#HALF_UP}. Negative inputs are clamped to {@code 0.00}.
 * </p>
 */
public class SalaryEmployee extends Employee{
    /** Annual salary in currency units; never negative; scale = 2. */
    private BigDecimal annualSalary;

    /** No-arg constructor for frameworks/serialization. */
    public SalaryEmployee() {
    }

    /**
     * Full constructor.
     *
     * @param lastName   employee last name
     * @param firstName  employee first name
     * @param middleInit middle initial
     * @param birthDate  birthdate (format defined by {@code Employee})
     * @param phoneNumber phone number
     * @param address    mailing address
     * @param employeeId internal employee id
     * @param jobTitle   job title
     * @param annualSalary annual salary (non-null). Negatives become {@code 0.00};
     *                     value is stored with scale 2 and HALF_UP rounding.
     */
    public SalaryEmployee(String lastName, String firstName, char middleInit, String birthDate, String phoneNumber, Address address, int employeeId, String jobTitle, BigDecimal annualSalary) {
        super(lastName, firstName, middleInit, birthDate, phoneNumber, address, employeeId, jobTitle);
        // Normalize: non-null, clamp to zero, two decimals.
        this.annualSalary = normalize(annualSalary);
    }

    /** @return the annual salary (scale 2, never negative). */
    public BigDecimal getAnnualSalary() {
        return annualSalary;
    }

    /**
     * Sets the annual salary.
     * <p>Negatives become {@code 0.00}. Value stored with scale 2 and HALF_UP rounding.</p>
     *
     * @param annualSalary non-null amount
     * @throws NullPointerException if {@code annualSalary} is null
     */
    public void setAnnualSalary(BigDecimal annualSalary) {
        // Normalize: non-null, clamp to zero, two decimals.
        this.annualSalary = normalize(annualSalary);
    }

    /**
     * Monthly earnings derived from the annual salary.
     * <p>Rounded to 2 decimals using {@link RoundingMode#HALF_UP}.</p>
     *
     * @return monthly earnings as BigDecimal (scale 2)
     */
    @Override
    public BigDecimal getEarnings() {
        // Divide by 12; many annual values don't divide evenly -> specify rounding.
        return annualSalary.divide(BigDecimal.valueOf(12),2,RoundingMode.HALF_UP);
    }

    // ---- Helpers ------------------------------------------------------------

    /** Ensures non-null, clamps negative to 0, and sets scale=2 with HALF_UP. */
    private static BigDecimal normalize(BigDecimal value) {
        return value == null ? BigDecimal.ZERO : value.max(BigDecimal.ZERO).setScale(2, RoundingMode.HALF_UP);
    }
}
