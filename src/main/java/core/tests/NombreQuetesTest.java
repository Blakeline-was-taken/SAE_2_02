package main.java.core.tests;

import main.java.core.NombreQuetes;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

public class NombreQuetesTest {

    /**
     * Teste la méthode run() de la classe NombreQuetes.
     *
     * @throws FileNotFoundException si le fichier du scénario n'est pas trouvé
     */
    @Test
    public void run() throws FileNotFoundException {
        // Test du scénario 0
        NombreQuetes nbTest = new NombreQuetes(0, 10);
        ArrayList<Integer>[] resultEff = nbTest.run(true, true);

        // Vérification des résultats attendus pour les solutions efficaces
        assertTrue(
                Arrays.toString(new int[]{1, 2, 3, 0}).equals(resultEff[0].toString())
                        || Arrays.toString(new int[]{1, 2, 4, 0}).equals(resultEff[0].toString())
        );
        assertTrue(
                Arrays.toString(new int[]{1, 2, 3, 4, 0}).equals(resultEff[2].toString())
                        || Arrays.toString(new int[]{1, 2, 4, 3, 0}).equals(resultEff[2].toString())
        );
        assertNull(resultEff[4]);

        // Test sans les meilleures solutions pour les solutions exhaustives
        nbTest = new NombreQuetes(0, 5);
        ArrayList<Integer>[] resultExh = nbTest.run(false, true);

        // Vérification des résultats attendus pour les solutions exhaustives
        assertTrue(
                Arrays.toString(new int[]{1, 2, 3, 4, 0}).equals(resultExh[0].toString())
                        || Arrays.toString(new int[]{1, 2, 4, 3, 0}).equals(resultExh[0].toString())
        );
        assertNull(resultExh[2]);

        // Test sans les meilleures solutions
        nbTest = new NombreQuetes(0, 10);
        resultEff = nbTest.run(true, false);

        // Vérification des résultats attendus pour les solutions efficaces sans les meilleures solutions
        assertNull(resultEff[4]);
        assertTrue(
                Arrays.toString(new int[]{1, 2, 3, 0}).equals(resultEff[2].toString())
                        || Arrays.toString(new int[]{1, 2, 4, 0}).equals(resultEff[2].toString())
        );
        assertTrue(
                Arrays.toString(new int[]{1, 2, 3, 4, 0}).equals(resultEff[0].toString())
                        || Arrays.toString(new int[]{1, 2, 4, 3, 0}).equals(resultEff[0].toString())
        );

        // Test sans les meilleures solutions pour les solutions exhaustives
        nbTest = new NombreQuetes(0, 5);
        resultExh = nbTest.run(false, false);

        // Vérification des résultats attendus pour les solutions exhaustives sans les meilleures solutions
        assertNull(resultExh[2]);
        assertTrue(
                Arrays.toString(new int[]{1, 2, 3, 4, 0}).equals(resultExh[0].toString())
                        || Arrays.toString(new int[]{1, 2, 4, 3, 0}).equals(resultExh[0].toString())
        );
    }
}
