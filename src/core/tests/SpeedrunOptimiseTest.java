package core.tests;

import core.SpeedrunOptimise;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SpeedrunOptimiseTest {

    @Test
    public void run() throws FileNotFoundException {
        // test scenario 0
        SpeedrunOptimise spTest = new SpeedrunOptimise(0);
        ArrayList<Integer> resultEff = spTest.run(true)[0];

        spTest = new SpeedrunOptimise(0);
        ArrayList<Integer> resultExh = spTest.run(false)[0];

        assertEquals(Arrays.toString(new int[] {1, 2, 4, 0}), resultEff.toString());
        assertEquals(Arrays.toString(new int[] {1, 2, 4, 3, 0}), resultExh.toString());

        //test scenario 10
        spTest = new SpeedrunOptimise(10);
        resultEff = spTest.run(true)[0];

        spTest = new SpeedrunOptimise(10);
        resultExh = spTest.run(false)[0];

        assertEquals(Arrays.toString(new int[] {2, 18, 3, 0}), resultEff.toString());
        assertEquals(Arrays.toString(new int[] {2, 18, 8, 3, 16, 12, 4, 15, 1, 10, 17, 9, 14, 11, 6, 7, 13, 5, 0}), resultExh.toString());
    }
}
