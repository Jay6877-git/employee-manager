package com.jaypatel.emanager.service;

import com.jaypatel.emanager.model.Employee;

import java.util.Collection;
import java.util.List;

public interface PayrollService {
    PayStub runFor(Employee employee, List<Deduction> deductions);

    List<PayStub> runForAll(Collection<Employee> employees, List<Deduction> deductions);
}
