package com.jaypatel.emanager.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class PersonTest {
    @Test
    void displayNameWithMiddle(){
        Person p = new Person("Patel", "Jay", 'M', "1998-05-12", "306-555-1234");
        assertEquals("Patel, Jay M", p.getDisplayName());
    }

    @Test
    void displayNameNoMiddle() {
        Person p = new Person("Patel", "Jay", '\0', null, null);
        assertEquals("Patel, Jay", p.getDisplayName());
    }

    @Test
    void displayNameOnlyLast() {
        Person p = new Person("Patel", null, '\0', null, null);
        assertEquals("Patel", p.getDisplayName());
    }

    @Test
    void displayNameOnlyFirst() {
        Person p = new Person(null, "Jay", 'M', null, null);
        assertEquals("Jay M", p.getDisplayName());
    }

    @Test
    void displayNameNone() {
        Person p = new Person(null, null, 'M', null, null);
        assertEquals("", p.getDisplayName());
    }
}