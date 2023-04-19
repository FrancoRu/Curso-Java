package org.example;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
public class AppTest {
    @Test
    public void testMyClassCreation() {
        App myObj = new App();
        assertNotNull(myObj);
    }
}
