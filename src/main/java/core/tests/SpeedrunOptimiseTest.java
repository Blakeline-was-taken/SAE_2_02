package main.java.core.tests;

import main.java.core.SpeedrunOptimise;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Classe de test pour la classe SpeedrunOptimise.
 */
public class SpeedrunOptimiseTest {

    /**
     * Teste la méthode run() de la classe SpeedrunOptimise.
     *
     * @throws FileNotFoundException si le fichier du scénario n'est pas trouvé
     */
    @Test
    public void run() throws FileNotFoundException {
        // Test du scénario 0
        SpeedrunOptimise spTest = new SpeedrunOptimise(0);
        ArrayList<Integer> resultEff = spTest.run(true, true)[0];

        spTest = new SpeedrunOptimise(0);
        ArrayList<Integer> resultExh = spTest.run(false, true)[0];

        // Vérification des résultats pour le mode efficace
        assertEquals(Arrays.toString(new int[] {1, 2, 4, 0}), resultEff.toString());

        // Vérification des résultats pour le mode exhaustif
        assertEquals(Arrays.toString(new int[] {1, 2, 4, 3, 0}), resultExh.toString());

        // Test du scénario 10
        spTest = new SpeedrunOptimise(10);
        resultEff = spTest.run(true, true)[0];

        spTest = new SpeedrunOptimise(10);
        resultExh = spTest.run(false, true)[0];

        // Vérification des résultats pour le mode efficace
        assertEquals(Arrays.toString(new int[] {2, 18, 3, 0}), resultEff.toString());

        // Vérification des résultats pour le mode exhaustif
        assertEquals(Arrays.toString(new int[] {2, 18, 8, 3, 16, 12, 4, 15, 1, 10, 17, 9, 14, 11, 6, 7, 13, 5, 0}), resultExh.toString());
    }
}
