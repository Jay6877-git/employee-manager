package com.jaypatel.emanager.model;

import java.math.BigDecimal;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class SalaryEmployeeTest {
    private SalaryEmployee newEmp(BigDecimal annual) {
        return new SalaryEmployee("Patel", "Jay", 'M', "2000-01-01",
                "555-0000", new Address(), 1, "Developer", annual);
    }

    @Test
    void constructor_normalizesNegativeAndScale() {
        SalaryEmployee e = newEmp(new BigDecimal("-10.999"));
        assertEquals(new BigDecimal("0.00"), e.getAnnualSalary()); // clamped to 0.00
    }

    @Test
    void setter_normalizesNegativeAndScale() {
        SalaryEmployee e = newEmp(new BigDecimal("1000"));
        e.setAnnualSalary(new BigDecimal("-1.234"));
        assertEquals(new BigDecimal("0.00"), e.getAnnualSalary());
    }

    @Test
    void getEarnings_dividesBy12_withHalfUpRounding() {
        SalaryEmployee e1 = newEmp(new BigDecimal("1200.00"));
        assertEquals(new BigDecimal("100.00"), e1.getEarnings()); // exact

        SalaryEmployee e2 = newEmp(new BigDecimal("1000.00"));
        assertEquals(new BigDecimal("83.33"), e2.getEarnings()); // 1000/12=83.333..., -> 83.33

        SalaryEmployee e3 = newEmp(new BigDecimal("1000.05"));
        assertEquals(new BigDecimal("83.34"), e3.getEarnings()); // 83.3375 -> 83.34 (HALF_UP)
    }

    @Test
    void zeroAnnualSalary_givesZeroMonthly() {
        SalaryEmployee e = newEmp(new BigDecimal("0.00"));
        assertEquals(new BigDecimal("0.00"), e.getEarnings());
    }

    @Test
    void nullAnnualSalary_throwsOnConstructorAndSetter() {
        // Constructor
        assertThrows(NullPointerException.class, () -> newEmp(null));

        // Setter
        SalaryEmployee e = newEmp(new BigDecimal("1.00"));
        assertThrows(NullPointerException.class, () -> e.setAnnualSalary(null));
    }

    @Test
    void scaleIsAlwaysTwo() {
        SalaryEmployee e = newEmp(new BigDecimal("123"));
        assertEquals(2, e.getAnnualSalary().scale());
        // also true for earnings:
        assertEquals(2, e.getEarnings().scale());
    }
}
