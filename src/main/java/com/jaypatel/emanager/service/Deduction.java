package com.jaypatel.emanager.service;

import com.jaypatel.emanager.model.Employee;
import java.math.BigDecimal;

/**
 * A single payroll deduction (e.g., Tax 5%, Benefits $50).
 * Implementations decide how to compute the amount from the monthly gross.
 */
public interface Deduction {
    String name();

    BigDecimal amountFor(Employee employee);
}
