package core.tests;

import core.Speedrun;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

/**
 * Classe de test pour la classe Speedrun.
 */
public class SpeedrunTest {

    /**
     * Teste la méthode run() de la classe Speedrun.
     *
     * @throws FileNotFoundException si le fichier du scénario n'est pas trouvé
     */
    @Test
    public void run() throws FileNotFoundException {
        // Test du scénario 0
        Speedrun spTest = new Speedrun(0, 10);
        ArrayList<Integer>[] resultEff = spTest.run(true, true);

        // Vérifie les résultats pour le mode efficace avec les meilleures solutions
        assertEquals(Arrays.toString(new int[] {1, 2, 4, 0}), resultEff[0].toString());
        assertEquals(Arrays.toString(new int[] {1, 2, 3, 0}), resultEff[1].toString());
        assertEquals(Arrays.toString(new int[] {1, 2, 4, 3, 0}), resultEff[2].toString());
        assertEquals(Arrays.toString(new int[] {1, 2, 3, 4, 0}), resultEff[3].toString());

        // Vérifie qu'il n'y a pas de cinquième solution
        assertNull(resultEff[4]);

        spTest = new Speedrun(0, 5);
        ArrayList<Integer>[] resultExh = spTest.run(false, true);

        // Vérifie les résultats pour le mode exhaustif avec les meilleures solutions
        assertEquals(Arrays.toString(new int[] {1, 2, 4, 3, 0}), resultExh[0].toString());
        assertEquals(Arrays.toString(new int[] {1, 2, 3, 4, 0}), resultExh[1].toString());

        // Vérifie qu'il n'y a pas de troisième solution
        assertNull(resultExh[2]);

        spTest = new Speedrun(0, 10);
        resultEff = spTest.run(true, false);

        // Vérifie les résultats pour le mode efficace sans les meilleures solutions
        assertNull(resultEff[4]);
        assertEquals(Arrays.toString(new int[] {1, 2, 4, 0}), resultEff[3].toString());
        assertEquals(Arrays.toString(new int[] {1, 2, 3, 0}), resultEff[2].toString());
        assertEquals(Arrays.toString(new int[] {1, 2, 4, 3, 0}), resultEff[1].toString());
        assertEquals(Arrays.toString(new int[] {1, 2, 3, 4, 0}), resultEff[0].toString());

        spTest = new Speedrun(0, 5);
        resultExh = spTest.run(false, false);

        // Vérifie les résultats pour le mode exhaustif sans les meilleures solutions
        assertNull(resultExh[2]);
        assertEquals(Arrays.toString(new int[] {1, 2, 4, 3, 0}), resultExh[1].toString());
        assertEquals(Arrays.toString(new int[] {1, 2, 3, 4, 0}), resultExh[0].toString());
    }
}
