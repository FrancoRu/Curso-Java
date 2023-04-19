package org.example;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

public class teamTest {
    @Test
    void testCreatorTeam(){
        team t = new team("Boca jr.", "Lost");
        assertTrue(t instanceof team);
    }
    @Test
    void testGetter(){
        team t = new team("Boca jr.", "Lost");
        assertEquals("Boca jr.", t.getName());
        assertEquals("Lost", t.getDesc());
    }
    @Test
    void testIllegalArgument(){
        assertThrows(IllegalArgumentException.class, () -> new team("", "Lost"));
        assertThrows(IllegalArgumentException.class, () -> new team("Boca jr.", ""));
        assertThrows(IllegalArgumentException.class, () -> new team("", ""));
        assertThrows(IllegalArgumentException.class, () -> new team(null, null));
        assertThrows(IllegalArgumentException.class, () -> new team("Boca jr.", null));
        assertThrows(IllegalArgumentException.class, () -> new team(null, "Lost"));
    }
}
