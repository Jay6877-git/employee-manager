package com.jaypatel.emanager.service;

import com.jaypatel.emanager.model.Employee;

import java.math.BigDecimal;
import java.math.RoundingMode;
/**
 * A deduction that takes a fixed percentage of an employee's gross earnings
 * (e.g., 0.05 = 5%). Result is rounded to 2 decimals using HALF_UP.
 */
public class FixedPercentDeduction implements Deduction{

    private final String name;
    private final BigDecimal percent; // decimal fraction (0.05 = 5%)

    /**
     * Creates a fixed-percent deduction.
     *
     * @param name    human-readable name (e.g., "Tax 5%")
     * @param percent non-negative decimal fraction; null treated as 0
     * @throws IllegalArgumentException if {@code name} is null or blank
     */
    public FixedPercentDeduction(String name, BigDecimal percent) {
        if (name == null || name.isBlank()) throw new IllegalArgumentException("name");
        this.name = name;
        this.percent = (percent == null) ? BigDecimal.ZERO : percent.max(BigDecimal.ZERO);
    }

    /** @return the configured deduction name */
    @Override
    public String name() {
        return name;
    }

    /**
     * Computes: employee. gross × percent, rounded to cents.
     * If employee or earnings are null/negative, treats base as zero.
     */
    @Override
    public BigDecimal amountFor(Employee employee) {
        return employee.getEarnings().multiply(percent).setScale(2, RoundingMode.HALF_UP);
    }
}
