package org.example;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

public class gameTest {
    @Test
    void testCreateGame(){
        team t1 = new team("aasda","asdad");
        team t2 = new team("aasda","asdad");

        assertTrue(new game(t1,t2, 1,2,3,5) instanceof game);
    }
    @Test
    void testConstructorWithNullTeam1() {
        assertThrows(IllegalArgumentException.class, () -> new game(null, new team("Team 2","asd"), 0, 0, 1, 1));
    }

    @Test
    void testConstructorWithNullTeam2() {
        assertThrows(IllegalArgumentException.class, () -> new game(new team("Team 1","asd"), null, 0, 0, 1, 1));
    }

    @Test
    void testConstructorWithNegativeScoreTeam1() {
        assertThrows(IllegalArgumentException.class, () -> new game(new team("Team 1","asd"), new team("Team 2","asd"), -1, 0, 1, 1));
    }

    @Test
    void testConstructorWithNegativeScoreTeam2() {
        assertThrows(IllegalArgumentException.class, () -> new game(new team("Team 1","asd"), new team("Team 2","asd"), 0, -1, 1, 1));
    }

    @Test
    void testConstructorWithNegativeCod() {
        assertThrows(IllegalArgumentException.class, () -> new game(new team("Team 1","asd"), new team("Team 2","asd"), 0, 0, -1, 1));
    }

    @Test
    void testConstructorWithNegativeCodRound() {
        assertThrows(IllegalArgumentException.class, () -> new game(new team("Team 1","asd"), new team("Team 2","asd"), 0, 0, 1, -1));
    }

    @Test
    void testConstructorWithAllInvalidParams() {
        assertThrows(IllegalArgumentException.class, () -> new game(null, null, -1, -1, -1, -1));
    }
}
