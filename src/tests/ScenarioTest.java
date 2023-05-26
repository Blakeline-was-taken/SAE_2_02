package tests;

import module.Quete;
import module.Scenario;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;
import java.util.Collections;
import java.util.TreeMap;
import java.util.TreeSet;

import static org.junit.jupiter.api.Assertions.*;

class ScenarioTest {
    @Test
    public void constructeur() throws FileNotFoundException {
        //Tests du champ quetes :
        Scenario scnTest = new Scenario("scenario_0.txt");
        TreeMap <Integer, Quete> mapTree = new TreeMap<>();
        mapTree.put(0, new Quete(new int[] {1, 1}, new int[][] {{3, 4}, {0, 0}}, 4, 350, "vaincre Araignée lunaire"));
        mapTree.put(1, new Quete(new int[] {4, 3}, new int[][] {{0, 0}, {0, 0}}, 2, 100, "explorer pic de Bhanborim"));
        mapTree.put(2, new Quete(new int[] {3, 1}, new int[][] {{1, 0}, {0, 0}}, 1, 150, "dialoguer avec Kaela la chaman des esprits"));
        mapTree.put(3, new Quete(new int[] {0, 4}, new int[][] {{2, 0}, {0, 0}}, 3, 200, "explorer palais de Ahehona"));
        mapTree.put(4, new Quete(new int[] {3, 2}, new int[][] {{2, 0}, {0, 0}}, 6, 100, "vaincre Loup Géant"));

        TreeMap <Integer, Quete> mapTest = scnTest.getQuetes();
        for (int i = 0; i <= 4; i++){
            assertEquals(mapTest.get(i), mapTree.get(i));
        }

        //Tests du champ successeurs :
        TreeMap <Integer, TreeSet <Integer>> voisinsTestTree = new TreeMap<>();
        voisinsTestTree.put(0,new TreeSet<>());

        TreeSet <Integer> ts1 = new TreeSet<>();
        ts1.add(2);
        voisinsTestTree.put(1,ts1);

        TreeSet <Integer> ts2 = new TreeSet<>();
        Collections.addAll(ts2, 3, 4);
        voisinsTestTree.put(2,ts2);

        TreeSet <Integer> ts3 = new TreeSet<>();
        ts3.add(0);
        voisinsTestTree.put(3,ts3);

        TreeSet <Integer> ts4 = new TreeSet<>();
        ts4.add(0);
        voisinsTestTree.put(4,ts4);

        for (int key : voisinsTestTree.keySet()){
            assertEquals(voisinsTestTree.get(key), scnTest.getSuccesseurs().get(key));
        }
    }

    @Test
    public void getQuete() throws FileNotFoundException {
        Scenario scnTest = new Scenario("scenario_0.txt");
        TreeMap <Integer, Quete> mapTree = new TreeMap<>();
        mapTree.put(0, new Quete(new int[] {1, 1}, new int[][] {{3, 4}, {0, 0}}, 4, 350, "vaincre Araignée lunaire"));
        mapTree.put(1, new Quete(new int[] {4, 3}, new int[][] {{0, 0}, {0, 0}}, 2, 100, "explorer pic de Bhanborim"));
        mapTree.put(2, new Quete(new int[] {3, 1}, new int[][] {{1, 0}, {0, 0}}, 1, 150, "dialoguer avec Kaela la chaman des esprits"));
        mapTree.put(3, new Quete(new int[] {0, 4}, new int[][] {{2, 0}, {0, 0}}, 3, 200, "explorer palais de Ahehona"));
        mapTree.put(4, new Quete(new int[] {3, 2}, new int[][] {{2, 0}, {0, 0}}, 6, 100, "vaincre Loup Géant"));
        for (int queteId : mapTree.keySet()){
            assertEquals(mapTree.get(queteId),scnTest.getQuete(queteId));
        }
    }
}
