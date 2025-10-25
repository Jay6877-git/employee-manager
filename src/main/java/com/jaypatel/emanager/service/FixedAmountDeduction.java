package com.jaypatel.emanager.service;

import com.jaypatel.emanager.model.Employee;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * A deduction that subtracts a fixed monthly amount (e.g., $50 benefits).
 * <p>
 * The amount is independent of an employee's gross pay and is stored with
 * scale 2 using {@link RoundingMode#HALF_UP}. Null or negative inputs are
 * coerced to {@code 0.00}.
 */
public class FixedAmountDeduction implements Deduction{
    /** Human-readable label for this deduction (e.g., "Benefits $50"). */
    private final String name;

    /** Fixed monthly amount to deduct (currency, scale 2). */
    private final BigDecimal amountMonthly;

    /**
     * Creates a fixed-amount monthly deduction.
     *
     * @param name          display name (e.g., "Benefits $50")
     * @param amountMonthly fixed monthly amount; null or negative treated as {@code 0.00}
     * @throws IllegalArgumentException if {@code name} is null or blank
     */
    public FixedAmountDeduction(String name, BigDecimal amountMonthly) {
        if (name == null || name.isBlank()) throw new IllegalArgumentException("name");
        this.name = name;
        this.amountMonthly = (amountMonthly == null) ? BigDecimal.ZERO : amountMonthly.max(BigDecimal.ZERO).setScale(2, RoundingMode.HALF_UP);
    }

    /** @return the configured deduction name */
    @Override
    public String name() {
        return name;
    }

    /**
     * Returns the fixed monthly deduction, ignoring the employee.
     *
     * @param employee the employee (unused)
     * @return the fixed monthly amount (scale 2)
     */
    @Override
    public BigDecimal amountFor(Employee employee) {
        return amountMonthly; // independent of gross
    }
}