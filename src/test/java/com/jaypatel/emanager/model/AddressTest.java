package com.jaypatel.emanager.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
public class AddressTest {

    @Test
    void fullAddressFormatsCorrectly() {
        // Arrange
        Address addr = new Address("123 Main St", "Saskatoon", "SK", "S7J 4M3");

        // Act & Assert
        assertEquals("123 Main St, Saskatoon, SK S7J 4M3", addr.displayAddress());
    }

    @Test
    void cityAndProvinceOnly() {
        Address addr = new Address(null, "Saskatoon", "SK", null);
        assertEquals("Saskatoon, SK", addr.displayAddress());
    }

    @Test
    void postalOnly() {
        Address addr = new Address(null, null, null, "S7J 4M3");
        assertEquals("S7J 4M3", addr.displayAddress());
    }

    @Test
    void provinceAndPostalOnly() {
        Address addr = new Address(null, null, "SK", "S7J 4M3");
        assertEquals("SK S7J 4M3", addr.displayAddress());
    }

    @Test
    void streetOnly() {
        Address addr = new Address("123 Main St", null, null, null);
        assertEquals("123 Main St", addr.displayAddress());
    }

    @Test
    void allNullReturnsEmptyString() {
        Address addr = new Address(null, null, null, null);
        assertEquals("", addr.displayAddress());
    }

    @Test
    void trimsInputsInConstructor() {
        Address addr = new Address(" 123 Main St  ", "  Saskatoon ", " SK  ", "  S7J 4M3 ");
        assertAll(
                () -> assertEquals("123 Main St, Saskatoon, SK S7J 4M3", addr.displayAddress()),
                () -> assertEquals("123 Main St", addr.getStreet()),
                () -> assertEquals("Saskatoon", addr.getCity()),
                () -> assertEquals("SK", addr.getProvince()),
                () -> assertEquals("S7J 4M3", addr.getPostalCode())
        );
    }
}
