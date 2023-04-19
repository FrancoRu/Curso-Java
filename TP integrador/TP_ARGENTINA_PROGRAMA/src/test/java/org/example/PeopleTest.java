package org.example;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
public class PeopleTest {
    @Test
    void testCreatePeople() {
        assertThrows(IllegalArgumentException.class, () -> new people("Franco", 123123, 10,5,10);
    }
    @Test
    public void testConstructorNullName() {
        assertThrows(IllegalArgumentException.class, () -> new people(null, 12345678, 10, 5, 2));
    }

    @Test
    public void testConstructorNegativeDNI() {
        assertThrows(IllegalArgumentException.class, () -> new people("John Doe", -12345678, 10, 5, 2));
    }

    @Test
    public void testConstructorNegativePoints() {
        assertThrows(IllegalArgumentException.class, () -> new people("John Doe", 12345678, -10, 5, 2));
    }

    @Test
    public void testConstructorNegativePointsRound() {
        assertThrows(IllegalArgumentException.class, () -> new people("John Doe", 12345678, 10, -5, 2));
    }

    @Test
    public void testConstructorNegativePointsPhase() {
        assertThrows(IllegalArgumentException.class, () -> new people("John Doe", 12345678, 10, 5, -2));
    }
}
