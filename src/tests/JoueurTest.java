package tests;

import static org.junit.jupiter.api.Assertions.*;
import module.Joueur;
import org.junit.jupiter.api.Test;

class JoueurTest {
    @Test
    void constructeur(){
        Joueur joueurTest = new Joueur();
        assertEquals(joueurTest.getExp(), 0);
        assertEquals(joueurTest.getCoord()[0], 0);
        assertEquals(joueurTest.getCoord()[1], 0);
    }

    @Test
    void deplacer(){
        Joueur jTest = new Joueur();
        jTest.deplacer(new int[] {5, 4});
        assertEquals(jTest.getCoord()[0], 5);
        assertEquals(jTest.getCoord()[1], 4);
    }

    /*@Test
    void distance(){
        Joueur j1 = new Joueur();
        Joueur j2 = new Joueur();
    }*/
}
