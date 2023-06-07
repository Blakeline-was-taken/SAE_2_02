package core.tests;

import static org.junit.jupiter.api.Assertions.*;
import core.Glouton;
import modele.Joueur;
import modele.Scenario;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Classe de test pour la classe BaseMoteur.
 * Elle contient des méthodes de test pour vérifier le fonctionnement des méthodes de la classe BaseMoteur.
 */
class BaseMoteurTest {

    /**
     * Test du constructeur de la classe BaseMoteur.
     *
     * @throws FileNotFoundException Si le fichier de scénario n'est pas trouvé.
     */
    @Test
    public void constructeur() throws FileNotFoundException {
        // Création d'une instance de Glouton pour le test
        Glouton glTest = new Glouton(0);

        // Vérification de la création du Joueur
        Joueur jTest = glTest.getJoueur();
        assertEquals(Arrays.toString((new int[]{0, 0})), Arrays.toString(jTest.getCoord()));
        assertEquals(0, jTest.getExp());

        // Vérification de la création du Scenario
        Scenario scnTest = glTest.getScenario();
        Scenario scnCheck = new Scenario("scenario_0.txt");
        for (int idQuete : scnTest.getQuetes().keySet()) {
            assertEquals(scnCheck.getQuete(idQuete), scnTest.getQuete(idQuete));
        }

        // Vérification du nombre de solutions
        assertEquals(1, glTest.getNbSolutions());
        assertEquals(10, new Glouton(0, 10).getNbSolutions());
    }

    /**
     * Test de la méthode queteLaPlusProche de la classe BaseMoteur.
     *
     * @throws FileNotFoundException Si le fichier de scénario n'est pas trouvé.
     */
    @Test
    public void queteLaPlusProche() throws FileNotFoundException {
        // Création d'une instance de Glouton pour le test
        Glouton glTest = new Glouton(0);
        Scenario scTest = glTest.getScenario();
        Joueur jTest = glTest.getJoueur();

        // Vérification de la quête la plus proche initiale
        assertEquals(1, glTest.queteLaPlusProche());

        // Déplacement et validation d'une quête
        jTest.deplacer(scTest.getQuete(glTest.queteLaPlusProche()));
        scTest.validerQuete(1);

        // Vérification de la nouvelle quête la plus proche
        assertEquals(2, glTest.queteLaPlusProche());

        // Déplacement et validation d'une autre quête
        jTest.deplacer(scTest.getQuete(glTest.queteLaPlusProche()));
        scTest.validerQuete(2);

        // Vérification de la nouvelle quête la plus proche
        assertEquals(4, glTest.queteLaPlusProche());

        // Déplacement et validation d'une autre quête
        jTest.deplacer(scTest.getQuete(glTest.queteLaPlusProche()));
        scTest.validerQuete(4);

        // Vérification de la quête la plus proche finale
        assertEquals(0, glTest.queteLaPlusProche());
    }

    /**
     * Test de la méthode calculDuree de la classe BaseMoteur.
     *
     * @throws FileNotFoundException Si le fichier de scénario n'est pas trouvé.
     */
    @Test
    public void calculDuree() throws FileNotFoundException {
        // Création d'une instance de Glouton pour le test
        Glouton glTest = new Glouton(0);
        ArrayList<Integer> solution = new ArrayList<>();
        solution.add(1);
        solution.add(2);
        solution.add(4);
        solution.add(0);
        assertEquals(27, glTest.calculDuree(solution));
    }
}
