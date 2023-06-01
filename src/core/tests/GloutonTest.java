package core.tests;

import static org.junit.jupiter.api.Assertions.*;
import core.Glouton;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;

public class GloutonTest {

    @Test
    public void run() throws FileNotFoundException {
        // test scenario 0
        Glouton glTest = new Glouton(0);
        ArrayList<Integer> resultEff = glTest.run(true);

        glTest = new Glouton(0);
        ArrayList<Integer> resultExh = glTest.run(false);

        assertEquals(Arrays.toString(new int[] {1, 2, 4, 0}), resultEff.toString());
        assertEquals(Arrays.toString(new int[] {1, 2, 4, 3, 0}), resultExh.toString());

        //test scenario 10
        glTest = new Glouton(10);
        resultEff = glTest.run(true);

        glTest = new Glouton(10);
        resultExh = glTest.run(false);

        assertEquals(Arrays.toString(new int[] {2, 18, 8, 3, 0}), resultEff.toString());
        assertEquals(Arrays.toString(new int[] {2, 18, 8, 3, 16, 12, 4, 15, 1, 10, 17, 9, 14, 11, 6, 13, 7, 5, 0}), resultExh.toString());
    }
}
