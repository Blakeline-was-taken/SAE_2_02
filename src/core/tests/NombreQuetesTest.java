package core.tests;

import core.NombreQuetes;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

public class NombreQuetesTest {

    @Test
    public void run() throws FileNotFoundException {
        // test scenario 0
        NombreQuetes nbTest = new NombreQuetes(0, 10);
        ArrayList<Integer>[] resultEff = nbTest.run(true, true);
        assertTrue(
                Arrays.toString(new int[] {1, 2, 3, 0}).equals(resultEff[0].toString())
                || Arrays.toString(new int[] {1, 2, 4, 0}).equals(resultEff[0].toString())
        );
        assertTrue(
                Arrays.toString(new int[] {1, 2, 3, 4, 0}).equals(resultEff[2].toString())
                        || Arrays.toString(new int[] {1, 2, 4, 3, 0}).equals(resultEff[2].toString())
        );
        assertNull(resultEff[4]);

        nbTest = new NombreQuetes(0, 5);
        ArrayList<Integer>[] resultExh = nbTest.run(false, true);
        assertTrue(
                Arrays.toString(new int[] {1, 2, 3, 4, 0}).equals(resultExh[0].toString())
                        || Arrays.toString(new int[] {1, 2, 4, 3, 0}).equals(resultExh[0].toString())
        );
        assertNull(resultExh[2]);

        nbTest = new NombreQuetes(0, 10);
        resultEff = nbTest.run(true, false);
        assertNull(resultEff[4]);
        assertTrue(
                Arrays.toString(new int[] {1, 2, 3, 0}).equals(resultEff[2].toString())
                        || Arrays.toString(new int[] {1, 2, 4, 0}).equals(resultEff[2].toString())
        );
        assertTrue(
                Arrays.toString(new int[] {1, 2, 3, 4, 0}).equals(resultEff[0].toString())
                        || Arrays.toString(new int[] {1, 2, 4, 3, 0}).equals(resultEff[0].toString())
        );

        nbTest = new NombreQuetes(0, 5);
        resultExh = nbTest.run(false, false);
        assertNull(resultExh[2]);
        assertTrue(
                Arrays.toString(new int[] {1, 2, 3, 4, 0}).equals(resultExh[0].toString())
                        || Arrays.toString(new int[] {1, 2, 4, 3, 0}).equals(resultExh[0].toString())
        );
    }
}
