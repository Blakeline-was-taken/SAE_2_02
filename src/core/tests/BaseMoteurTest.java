package core.tests;

import static org.junit.jupiter.api.Assertions.*;
import core.Glouton;
import modele.Joueur;
import modele.Scenario;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;

class BaseMoteurTest {

    @Test
    public void constructeur() throws FileNotFoundException {
        Glouton glTest = new Glouton(0);

        // Vérification de la création du Joueur
        Joueur jTest = glTest.getJoueur();
        assertEquals(Arrays.toString((new int[]{0, 0})), Arrays.toString(jTest.getCoord()));
        assertEquals(0, jTest.getExp());

        //Vérification de la création du Scenario
        Scenario scnTest = glTest.getScenario();
        Scenario scnCheck = new Scenario("scenario_0.txt");
        for (int idQuete : scnTest.getQuetes().keySet()){
            assertEquals(scnCheck.getQuete(idQuete), scnTest.getQuete(idQuete));
        }

        //Vérification du nombre de solutions
        assertEquals(1, glTest.getNbSolutions());
        assertEquals(10, new Glouton(0, 10).getNbSolutions());
    }

    @Test
    public void queteLaPlusProche() throws FileNotFoundException {
        Glouton glTest = new Glouton(0);
        Scenario scTest = glTest.getScenario();
        Joueur jTest = glTest.getJoueur();

        assertEquals(1, glTest.queteLaPlusProche());
        jTest.deplacer(scTest.getQuete(glTest.queteLaPlusProche()));
        scTest.validerQuete(1);

        assertEquals(2, glTest.queteLaPlusProche());
        jTest.deplacer(scTest.getQuete(glTest.queteLaPlusProche()));
        scTest.validerQuete(2);

        assertEquals(4, glTest.queteLaPlusProche());
        jTest.deplacer(scTest.getQuete(glTest.queteLaPlusProche()));
        scTest.validerQuete(4);

        assertEquals(0, glTest.queteLaPlusProche());
    }

    @Test
    public void calculDuree() throws FileNotFoundException {
        Glouton glTest = new Glouton(0);
        ArrayList<Integer> solution = new ArrayList<>();
        solution.add(1);
        solution.add(2);
        solution.add(4);
        solution.add(0);
        assertEquals(27, glTest.calculDuree(solution));
    }
}
