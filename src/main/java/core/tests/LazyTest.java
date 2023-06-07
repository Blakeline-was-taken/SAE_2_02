package main.java.core.tests;

import main.java.core.Lazy;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Classe de test pour la classe Lazy.
 */
public class LazyTest {

    /**
     * Teste la méthode run() de la classe Lazy.
     *
     * @throws FileNotFoundException si le fichier du scénario n'est pas trouvé
     */
    @Test
    public void run() throws FileNotFoundException {
        // Test du scénario 0
        Lazy lzTest = new Lazy(0, 10);
        ArrayList<Integer>[] resultEff = lzTest.run(true, true);

        // Vérification des solutions générées pour un parcours efficace en recherchant les meilleures solutions
        assertEquals(Arrays.toString(new int[] {1, 2, 4, 0}), resultEff[0].toString());
        assertTrue(
                Arrays.toString(new int[] {1, 2, 3, 0}).equals(resultEff[1].toString())
                        || Arrays.toString(new int[] {1, 2, 4, 3, 0}).equals(resultEff[1].toString())
        );
        assertEquals(Arrays.toString(new int[] {1, 2, 3, 4, 0}), resultEff[3].toString());
        assertNull(resultEff[4]);

        lzTest = new Lazy(0, 5);
        ArrayList<Integer>[] resultExh = lzTest.run(false, true);

        // Vérification des solutions générées pour un parcours exhaustif en recherchant les meilleures solutions
        assertEquals(Arrays.toString(new int[] {1, 2, 4, 3, 0}), resultExh[0].toString());
        assertEquals(Arrays.toString(new int[] {1, 2, 3, 4, 0}), resultExh[1].toString());
        assertNull(resultExh[2]);

        lzTest = new Lazy(0, 10);
        resultEff = lzTest.run(true, false);

        // Vérification des solutions générées pour un parcours efficace sans recherche des meilleures solutions
        assertNull(resultEff[4]);
        assertEquals(Arrays.toString(new int[] {1, 2, 4, 0}), resultEff[3].toString());
        assertTrue(
                Arrays.toString(new int[] {1, 2, 3, 0}).equals(resultEff[1].toString())
                        || Arrays.toString(new int[] {1, 2, 4, 3, 0}).equals(resultEff[1].toString())
        );
        assertEquals(Arrays.toString(new int[] {1, 2, 3, 4, 0}), resultEff[0].toString());

        lzTest = new Lazy(0, 5);
        resultExh = lzTest.run(false, false);

        // Vérification des solutions générées pour un parcours exhaustif sans recherche des meilleures solutions
        assertNull(resultExh[2]);
        assertEquals(Arrays.toString(new int[] {1, 2, 4, 3, 0}), resultExh[1].toString());
        assertEquals(Arrays.toString(new int[] {1, 2, 3, 4, 0}), resultExh[0].toString());
    }
}
