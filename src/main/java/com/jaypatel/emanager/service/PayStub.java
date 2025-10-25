package com.jaypatel.emanager.service;

import com.jaypatel.emanager.model.Employee;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Immutable view of a single pay stub for an employee.
 * <p>
 * Captures:
 * <ul>
 *   <li>{@code gross}: the employee's gross earnings for the period</li>
 *   <li>{@code deductions}: a label → amount map (e.g., "Tax 5%" → 125.00)</li>
 *   <li>{@code net}: {@code gross - sum(deductions)}, rounded to 2 decimals (HALF_UP)</li>
 * </ul>
 * All amounts are treated as currency and rounded to 2 decimals as results are computed.
 */
public class PayStub {
    private final Employee employee;
    private final BigDecimal gross;
    private final Map<String, BigDecimal> deductions; // unmodifiable, preserves insertion order
    private final BigDecimal net;

    /**
     * Creates a pay stub from an employee and a set of deductions.
     *
     * @param employee   the employee for this pay stub (may not be null in normal use)
     * @param deductions a map of deduction name → amount; null values are ignored in totals.
     *                   The map is defensively copied and wrapped unmodifiable.
     */
    public PayStub(Employee employee, Map<String, BigDecimal> deductions) {
        this.employee = employee;
        // If earnings is null, treat as 0.00 to avoid NPEs.
        this.gross = (employee != null && employee.getEarnings() != null)
                ? employee.getEarnings()
                : BigDecimal.ZERO.setScale(2, RoundingMode.HALF_UP);

        // Defensive copy to prevent external mutation; preserve caller's insertion order.
        Map<String, BigDecimal> copy = (deductions == null)
                ? new LinkedHashMap<>()
                : new LinkedHashMap<>(deductions);
        this.deductions = Collections.unmodifiableMap(copy);

        this.net = computeNet();
    }

    /**
     * Computes net pay as {@code gross - sum(deductions)} with currency rounding.
     */
    private BigDecimal computeNet() {
        BigDecimal total = BigDecimal.ZERO.setScale(2, RoundingMode.HALF_UP);

        for (BigDecimal v : deductions.values()){
            // Sum with currency precision.
            if (v != null) total = total.add(v).setScale(2,RoundingMode.HALF_UP);
        }

        return gross.subtract(total).setScale(2, RoundingMode.HALF_UP);
    }

    /** @return the employee this pay stub belongs to */
    public Employee getEmployee() {
        return employee;
    }

    /** @return the gross earnings for this pay period */
    public BigDecimal getGross() {
        return gross;
    }

    /**
     * @return an unmodifiable map of deduction name → amount.
     *         Null amounts (if present in the input) are ignored in totals.
     */
    public Map<String, BigDecimal> getDeductions() {
        return deductions;
    }

    /** @return the net pay (gross minus deductions), scale 2, HALF_UP */
    public BigDecimal getNet() {
        return net;
    }
}