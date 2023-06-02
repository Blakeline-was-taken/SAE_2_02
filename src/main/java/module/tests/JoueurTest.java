package module.tests;

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

    @Test
    void addExp(){
        Joueur jTest = new Joueur();
        jTest.addExp(15);
        assertEquals(jTest.getExp(), 15);
        jTest.addExp(-5);
        assertEquals(jTest.getExp(), 10);
    }

    @Test
    void distance(){
        Joueur j1 = new Joueur();
        Joueur j2 = new Joueur();

        // Test P0
        j1.deplacer(new int[] {0, 0});
        j2.deplacer(new int[] {0, 0});
        assertEquals(j1.distance(j2), 0);

        // Test P1
        j1.deplacer(new int[] {0, 2});
        j2.deplacer(new int[] {0, -2});
        assertEquals(j1.distance(j2), 4);

        // Test P2
        j1.deplacer(new int[] {0, -2});
        j2.deplacer(new int[] {0, 2});
        assertEquals(j1.distance(j2), 4);

        // Test P3
        j1.deplacer(new int[] {2, 0});
        j2.deplacer(new int[] {-2, 0});
        assertEquals(j1.distance(j2), 4);

        // Test P4
        j1.deplacer(new int[] {-2, 0});
        j2.deplacer(new int[] {2, 0});
        assertEquals(j1.distance(j2), 4);

        // Test P5
        j1.deplacer(new int[] {2, 2});
        j2.deplacer(new int[] {-2, -2});
        assertEquals(j1.distance(j2), 8);

        // Test P6
        j1.deplacer(new int[] {2, -2});
        j2.deplacer(new int[] {-2, 2});
        assertEquals(j1.distance(j2), 8);

        // Test P7
        j1.deplacer(new int[] {-2, 2});
        j2.deplacer(new int[] {2, -2});
        assertEquals(j1.distance(j2), 8);

        // Test P8
        j1.deplacer(new int[] {-2, -2});
        j2.deplacer(new int[] {2, 2});
        assertEquals(j1.distance(j2), 8);
    }
}
