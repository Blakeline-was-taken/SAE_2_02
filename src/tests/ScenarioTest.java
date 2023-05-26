package tests;

import module.Quete;
import module.Scenario;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;
import java.util.TreeMap;

import static org.junit.jupiter.api.Assertions.*;

class ScenarioTest {
    @Test
    public void constructeur() throws FileNotFoundException {
        Scenario scnTest = new Scenario("scenario_0.txt");
        TreeMap <Integer, Quete> mapTree = new TreeMap<>();
        mapTree.put(0, new Quete(new int[] {1, 1}, new int[][] {{3, 4}, {0, 0}}, 4, 350, "vaincre Araignée lunaire"));
        mapTree.put(1, new Quete(new int[] {4, 3}, new int[][] {{0, 0}, {0, 0}}, 2, 100, "explorer pic de Bhanborim"));
        mapTree.put(2, new Quete(new int[] {3, 1}, new int[][] {{1, 0}, {0, 0}}, 1, 150, "dialoguer avec Kaela la chaman des esprits"));
        mapTree.put(3, new Quete(new int[] {0, 4}, new int[][] {{2, 0}, {0, 0}}, 3, 200, "explorer palais de Ahehona"));
        mapTree.put(4, new Quete(new int[] {3, 2}, new int[][] {{2, 0}, {0, 0}}, 6, 100, "vaincre Loup Géant"));

        TreeMap <Integer, Quete> mapTest = scnTest.getQuetes();
        for (int i = 0; i <= 4; i++){
            Quete quest1 = mapTest.get(i);
            Quete quest2 = mapTree.get(i);
            assertEquals(0, quest1.distance(quest2));
            assertEquals(quest1.getDuree(), quest2.getDuree());
            assertEquals(quest1.getExp(), quest2.getExp());
            assertEquals(quest1.getIntitule(), quest2.getIntitule());
        }
    }
}
