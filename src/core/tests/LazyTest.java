package core.tests;

import core.Lazy;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

public class LazyTest {

    @Test
    public void run() throws FileNotFoundException {
        // test scenario 0
        Lazy lzTest = new Lazy(0, 10);
        ArrayList<Integer>[] resultEff = lzTest.run(true, true);
        assertEquals(Arrays.toString(new int[] {1, 2, 4, 0}), resultEff[0].toString());
        assertEquals(Arrays.toString(new int[] {1, 2, 3, 0}), resultEff[1].toString());
        assertEquals(Arrays.toString(new int[] {1, 2, 4, 3, 0}), resultEff[2].toString());
        assertEquals(Arrays.toString(new int[] {1, 2, 3, 4, 0}), resultEff[3].toString());
        assertNull(resultEff[4]);

        lzTest = new Lazy(0, 5);
        ArrayList<Integer>[] resultExh = lzTest.run(false, true);
        assertEquals(Arrays.toString(new int[] {1, 2, 4, 3, 0}), resultExh[0].toString());
        assertEquals(Arrays.toString(new int[] {1, 2, 3, 4, 0}), resultExh[1].toString());
        assertNull(resultExh[2]);

        lzTest = new Lazy(0, 10);
        resultEff = lzTest.run(true, false);
        assertNull(resultEff[4]);
        assertEquals(Arrays.toString(new int[] {1, 2, 4, 0}), resultEff[3].toString());
        assertEquals(Arrays.toString(new int[] {1, 2, 3, 0}), resultEff[2].toString());
        assertEquals(Arrays.toString(new int[] {1, 2, 4, 3, 0}), resultEff[1].toString());
        assertEquals(Arrays.toString(new int[] {1, 2, 3, 4, 0}), resultEff[0].toString());


        lzTest = new Lazy(0, 5);
        resultExh = lzTest.run(false, false);
        assertNull(resultExh[2]);
        assertEquals(Arrays.toString(new int[] {1, 2, 4, 3, 0}), resultExh[1].toString());
        assertEquals(Arrays.toString(new int[] {1, 2, 3, 4, 0}), resultExh[0].toString());

    }
}
