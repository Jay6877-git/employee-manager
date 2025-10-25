package com.jaypatel.emanager.service;

import com.jaypatel.emanager.model.Employee;

import java.math.BigDecimal;
import java.util.*;

/**
 * Default implementation of {@link PayrollService}.
 *
 * <p>Behavior:
 * <ul>
 *   <li>Requires a non-null {@link Employee} in {@link #runFor(Employee, List)}.</li>
 *   <li>Ignores {@code null} deduction entries; uses deduction {@code name()} as the line key.</li>
 *   <li>If multiple deductions share the same name, the last one wins (insertion ordered map).</li>
 *   <li>{@link PayStub} is responsible for final rounding and net computation.</li>
 * </ul>
 * </p>
 */
public class PayrollServiceImpl implements PayrollService{

    /**
     * Runs payroll for a single employee.
     *
     * @see PayrollService#runFor(Employee, List)
     */
    @Override
    public PayStub runFor(Employee employee, List<Deduction> deductions) {
        Objects.requireNonNull(employee, "employee");

        // Preserve the order in which deductions are provided.
        Map<String, BigDecimal> lines = new LinkedHashMap<>();

        if (deductions != null) {
            for (Deduction d : deductions){
                if (d == null) continue;  // skip null entries

                BigDecimal amt = d.amountFor(employee);

                // If two deductions share the same name, the later one overwrites the earlier.
                lines.put(d.name(),amt);
            }
        }
        // PayStub defensively copies and computes net internally.
        return new PayStub(employee,lines);
    }

    /**
     * Runs payroll for all provided employees.
     *
     * @see PayrollService#runForAll(Collection, List)
     */
    @Override
    public List<PayStub> runForAll(Collection<Employee> employees, List<Deduction> deductions) {
        if (employees == null || employees.isEmpty()) return List.of();
        List<PayStub> out = new ArrayList<>();

        for (Employee e : employees){
            out.add(runFor(e,deductions));
        }

        return out;
    }
}
