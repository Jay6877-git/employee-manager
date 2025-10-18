package com.jaypatel.emanager.model;


/**
 * Represents a person with basic identity and contact details.
 * <p>
 * This is a simple mutable POJO intended for use as a model/entity in the eManager app.
 * </p>
 *
 * <h3>Example</h3>
 * <pre>{@code
 * Person p = new Person("Patel", "Jay", 'M', "1998-05-12", "306-555-1234");
 * System.out.println(p);  // Patel, Jay M
 * }</pre>
 *
 * <p><b>Note:</b> {@code birthDate} and {@code phoneNumber} are stored as strings to keep the class minimal;
 * consider using {@link java.time.LocalDate} and a validated phone type if you need stricter typing.</p>
 */
public class Person {
    /** Surname / family name. */
    private String lastName;

    /** Given / first name. */
    private String firstName;

    /** Middle initial (use '\0' if unknown). */
    private char middleInit;

    /** Birthdate as a string (e.g., ISO-8601 "YYYY-MM-DD"). No validation is performed here. */
    private String birthDate;

    /** Primary contact number (format not enforced). */
    private String phoneNumber;

    private Address address;

    /** No-args constructor for frameworks and serialization. */
    public Person() {
    }

    /**
     * Full constructor.
     *
     * @param lastName    the person's last name (surname)
     * @param firstName   the person's first name (given name)
     * @param middleInit  the person's middle initial; use {@code '\0'} if none/unknown
     * @param birthDate   the person's birthdate string (e.g., {@code "2000-01-31"})
     * @param phoneNumber the person's phone number string
     */
    public Person(String lastName, String firstName, char middleInit, String birthDate, String phoneNumber, Address address) {
        this.lastName = (lastName == null || lastName.isBlank()) ? null : lastName.trim();
        this.firstName = (firstName == null || firstName.isBlank()) ? null : firstName.trim();
        this.middleInit = middleInit;
        this.birthDate = (birthDate == null || birthDate.isBlank()) ? null : birthDate.trim();
        this.phoneNumber = (phoneNumber == null || phoneNumber.isBlank()) ? null : phoneNumber.trim();

        this.address = address;
    }

    /**
     * Sets the last name (surname).
     *
     * @param lastName the last name to set
     */
    public void setLastName(String lastName) {
        this.lastName = (lastName == null || lastName.isBlank()) ? null : lastName.trim();
    }

    /**
     * Sets the first name (given name).
     *
     * @param firstName the first name to set
     */
    public void setFirstName(String firstName) {
        this.firstName = (firstName == null || firstName.isBlank()) ? null : firstName.trim();
    }

    /**
     * Sets the middle initial.
     *
     * @param middleInit the middle initial character; use {@code '\0'} if none/unknown
     */
    public void setMiddleInit(char middleInit) {
        this.middleInit = middleInit;
    }

    /**
     * Sets the birthdate string.
     *
     * @param birthDate birth date string (e.g., {@code "YYYY-MM-DD"})
     */
    public void setBirthDate(String birthDate) {
        this.birthDate = (birthDate == null || birthDate.isBlank()) ? null : birthDate.trim();
    }

    /**
     * Sets the phone number string.
     *
     * @param phoneNumber phone number string to set
     */
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = (phoneNumber == null || phoneNumber.isBlank()) ? null : phoneNumber.trim();
    }

    /**
     * Sets the Address.
     *
     * @param address to set address
     */
    public void setAddress(Address address) {
        this.address = address;
    }

    /**
     * Returns the last name (surname).
     *
     * @return the last name
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * Returns the first name (given name).
     *
     * @return the first name
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * Returns the middle initial.
     *
     * @return the middle initial character, or {@code '\0'} if none/unknown
     */
    public char getMiddleInit() {
        return middleInit;
    }

    /**
     * Returns the birthdate string.
     *
     * @return the birthdate, as provided (no parsing/validation)
     */
    public String getBirthDate() {
        return birthDate;
    }

    /**
     * Returns the phone number string.
     *
     * @return the phone number, as provided (no formatting/validation)
     */
    public String getPhoneNumber() {
        return phoneNumber;
    }

    /**
     * Returns the address
     * @return the address class
     */
    public Address getAddress() {
        return address;
    }

    /**
     * Returns a concise display string of the form {@code "LastName, FirstName M"}.
     * <p>
     * If {@code middleInit} is the null character ({@code '\0'}), the trailing space and
     * initial may look odd; adjust formatting if needed for your UI.
     * </p>
     *
     * @return a human-readable representation
     */
    @Override
    public String toString() {
        return getDisplayName();
    }

    /**
     * Builds a human-friendly name for display.
     * <p>
     * Rules:
     * <ul>
     *   <li>Format is {@code "LastName, FirstName"} when both are present.</li>
     *   <li>If {@code middleInit != '\0'} <em>and</em> first name exists, append a space and the middle initial:
     *       {@code "LastName, FirstName M"}.</li>
     *   <li>If only one of first/last is present, return just that part (no dangling commas/spaces).</li>
     *   <li>If both names are missing (null/blank), return an empty string.</li>
     * </ul>
     * This method is null/blank-safe to match the class's permissive setters which may collapse blanks to {@code null}.
     *
     * @return a display-ready name string, never {@code null}; may be {@code ""} if no name parts are present
     * @implNote Avoids printing the {@code '\0'} middle-initial placeholder and never includes the literal
     *           string {@code "null"} in the output.
     */
    public String getDisplayName() {
        final String ln = lastName;
        final String fn = firstName;

        final boolean hasLn = ln != null && !ln.isBlank();
        final boolean hasFn = fn != null && !fn.isBlank();

        if (!hasLn && !hasFn) return "";

        StringBuilder sb = new StringBuilder();
        if (hasLn) sb.append(ln);
        if (hasFn) {
            if (hasLn) sb.append(", ");
            sb.append(fn);
            if (middleInit != '\0') sb.append(' ').append(middleInit);
        }
        return sb.toString();
    }
}
