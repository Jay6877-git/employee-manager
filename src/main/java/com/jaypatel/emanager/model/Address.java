package com.jaypatel.emanager.model;

/**
 * Simple mailing address (street, city, province, postal code).
 * <p>Fields are trimmed; blanks are stored as {@code null}.</p>
 */
public class Address {
    private String street;

    private String city;

    private String province;

    private String postalCode;

    /**
     * No-args constructor (useful for frameworks, JSON binding, etc.).
     */
    public Address() {
    }

    /**
     * Full constructor.
     *
     * @param street     street line; trimmed, blank → {@code null}
     * @param city       city; trimmed, blank → {@code null}
     * @param province   province/state; trimmed, blank → {@code null}
     * @param postalCode postal code/ZIP; trimmed, blank → {@code null}
     */
    public Address(String street, String city, String province, String postalCode) {
        this.street = (street == null || street.isBlank()) ? null : street.trim();
        this.city = (city == null || city.isBlank()) ? null : city.trim();
        this.province = (province == null || province.isBlank()) ? null : province.trim();
        this.postalCode = (postalCode == null || postalCode.isBlank()) ? null : postalCode.trim();
    }

    /**
     * @return the street, or {@code null} if not set
     */
    public String getStreet() {
        return street;
    }

    /**
     * Sets the street. Trimmed; blanks become {@code null}.
     * @param street street line
     */
    public void setStreet(String street) {
        this.street = (street == null || street.isBlank()) ? null : street.trim();
    }

    /**
     * @return the city, or {@code null} if not set
     */
    public String getCity() {
        return city;
    }

    /**
     * Sets the city. Trimmed; blanks become {@code null}.
     * @param city city name
     */
    public void setCity(String city) {
        this.city = (city == null || city.isBlank()) ? null : city.trim();
    }

    /**
     * @return the province/state, or {@code null} if not set
     */
    public String getProvince() {
        return province;
    }

    /**
     * Sets the province/state. Trimmed; blanks become {@code null}.
     * @param province province or state code/name
     */
    public void setProvince(String province) {
        this.province = (province == null || province.isBlank()) ? null : province.trim();
    }

    /**
     * @return the postal/ZIP code, or {@code null} if not set
     */
    public String getPostalCode() {
        return postalCode;
    }

    /**
     * Sets the postal/ZIP code. Trimmed; blanks become {@code null}.
     * @param postalCode postal or ZIP code
     */
    public void setPostalCode(String postalCode) {
        this.postalCode = (postalCode == null || postalCode.isBlank()) ? null : postalCode.trim();
    }

    /**
     * Builds a single-line, human-friendly address.
     * <p>Format: {@code "Street, City, Province PostalCode"}.
     * Missing parts are skipped (no extra commas/spaces). Returns {@code ""} if all parts are absent.</p>
     *
     * @return display-ready address string (never {@code null})
     */
    public String displayAddress(){
        final String st = street;
        final String c  = city;
        final String pr = province;
        final String pc = postalCode;

        final boolean hasSt = st != null && !st.isBlank();
        final boolean hasC  = c  != null && !c.isBlank();
        final boolean hasPr = pr != null && !pr.isBlank();
        final boolean hasPc = pc != null && !pc.isBlank();

        StringBuilder base = new StringBuilder();

        // Join Street, City, Province with ", " between present parts
        if (hasSt) base.append(st);
        if (hasC) {
            if (!base.isEmpty()) base.append(", ");
            base.append(c);
        }
        if (hasPr) {
            if (!base.isEmpty()) base.append(", ");
            base.append(pr);
        }

        // Append Postal Code with a leading space if anything precedes it
        if (hasPc) {
            if (!base.isEmpty()) {
                base.append(' ').append(pc);
            } else {
                base.append(pc);
            }
        }

        return base.toString();
    }

    @Override
    public String toString() {
        return displayAddress();
    }


}
