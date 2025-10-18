package com.jaypatel.emanager.model;
/**
 * Base abstract type for all employees in the system.
 * <p>
 * Extends {@link Person} with an employee-specific identifier and job title,
 * and defines a contract for computing earnings via {@link #getEarnings()}.
 * </p>
 *
 * @see Person
 * @see Address
 */
public abstract class Employee extends Person{
    /**
     * Unique identifier for the employee within the organization.
     */
    private int employeeId;
    /**
     * Human-readable title for the employee's role (e.g., "Software Engineer").
     */
    private String jobTitle;

    /**
     * No-arg constructor for frameworks or serializers that require it.
     */
    public Employee() {
    }

    /**
     * Creates a new {@code Employee}.
     *
     * @param lastName     the person's last name
     * @param firstName    the person's first name
     * @param middleInit   middle initial of the person
     * @param birthDate    birthdate as a string (format determined by {@link Person})
     * @param phoneNumber  contact number
     * @param address      mailing address
     * @param employeeId   unique employee identifier
     * @param jobTitle     employee's job title
     */
    public Employee(String lastName, String firstName, char middleInit, String birthDate, String phoneNumber, Address address, int employeeId, String jobTitle) {
        super(lastName, firstName, middleInit, birthDate, phoneNumber, address);
        this.employeeId = employeeId;
        this.jobTitle = jobTitle;
    }

    /**
     * Returns the unique employee identifier.
     *
     * @return the employee ID
     */
    public int getEmployeeId() {
        return employeeId;
    }

    /**
     * Sets the unique employee identifier.
     *
     * @param employeeId the employee ID to set
     */
    public void setEmployeeId(int employeeId) {
        this.employeeId = employeeId;
    }

    /**
     * Returns the employee's job title.
     *
     * @return the job title
     */
    public String getJobTitle() {
        return jobTitle;
    }

    /**
     * Sets the employee's job title.
     *
     * @param jobTitle the job title to set
     */
    public void setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
    }

    /**
     * Returns a concise textual representation of the employee.
     * <p>
     * Includes the employee ID and the {@code Person} details from {@link Person#toString()}.
     * </p>
     *
     * @return a string representation of this employee
     */
    @Override
    public String toString() {
        return getEmployeeId() + ": " + super.toString();
    }

    /**
     * Computes the employee's earnings.
     * <p>
     * Subclasses must define how earnings are calculated (e.g., hourly wage,
     * salary, commission). Implementations should document the basis of the
     * calculation and the units (typically the current pay period or monthly).
     * </p>
     *
     * @return the earnings amount
     */
    public abstract double getEarnings();
}
