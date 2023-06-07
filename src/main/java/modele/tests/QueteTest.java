/**
 * Classe de test pour la classe Quete.
 */
package modele.tests;

import modele.Quete;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class QueteTest {
    /**
     * Test du constructeur de la classe Quete.
     * Vérifie si les valeurs des coordonnées, des préconditions, de la durée, de l'expérience et de l'intitulé
     * sont correctement initialisées.
     */
    @Test
    void constructeur() {
        Quete queteTest = new Quete(new int[] {2, 7}, new int[][] {{2,4}, {8, 6}}, 3, 120, "Patate");
        assertEquals(queteTest.getCoord()[0], 2);
        assertEquals(queteTest.getCoord()[1], 7);

        assertEquals(queteTest.getCond()[0][0], 2);
        assertEquals(queteTest.getCond()[0][1], 4);
        assertEquals(queteTest.getCond()[1][0], 8);
        assertEquals(queteTest.getCond()[1][1], 6);

        assertEquals(queteTest.getDuree(), 3);
        assertEquals(queteTest.getExp(), 120);
        assertEquals(queteTest.getIntitule(), "Patate");
    }

    /**
     * Test de la méthode equals de la classe Quete.
     * Vérifie si deux quêtes sont considérées comme égales lorsque tous leurs attributs sont identiques,
     * et si elles sont considérées comme différentes lorsque au moins un attribut diffère.
     */
    @Test
    void equals() {
        Quete q1 = new Quete(new int[] {1, 1}, new int[][] {{3, 4}, {0, 0}}, 4, 350, "vaincre Araignée lunaire");
        Quete q2 = new Quete(new int[] {1, 1}, new int[][] {{3, 4}, {0, 0}}, 4, 350, "vaincre Araignée lunaire");
        Quete q3 = new Quete(new int[] {4, 3}, new int[][] {{0, 0}, {0, 0}}, 2, 100, "explorer pic de Bhanborim");
        assertEquals(q1, q2);
        assertNotEquals(q1, q3);
    }
}
