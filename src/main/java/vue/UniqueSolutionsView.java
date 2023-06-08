package vue;

import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Separator;
import javafx.scene.layout.VBox;
import modele.Joueur;
import modele.Quete;
import modele.Scenario;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * La classe UniqueSolutionsView représente la vue affichant les solutions uniques d'un scénario.
 * Elle hérite de la classe VBox.
 */
public class UniqueSolutionsView extends VBox {

    /**
     * Constructeur de la classe UniqueSolutionsView.
     *
     * @param solutions Les solutions uniques à afficher.
     * @throws FileNotFoundException si le fichier n'est pas trouvé.
     */
    public UniqueSolutionsView(ArrayList<Integer>[] solutions) throws FileNotFoundException {
        ArrayList<Integer> solution = solutions[0];
        VBox display = new VBox(5);
        display.setPadding(new Insets(50));

        Scenario scn = new Scenario(Root.getSelectedScn());

        VBox scrollBox = new VBox();
        ScrollPane solutionScroll = new ScrollPane();
        solutionScroll.setContent(scrollBox);
        solutionScroll.setPrefWidth(800);
        solutionScroll.setFitToWidth(true);
        Label title = new Label("Voici la solution trouvée :");
        display.getChildren().addAll(title, solutionScroll);
        StringBuilder recapStr = new StringBuilder();

        int exp = 0;
        int duree = 0;
        int i = 0;
        Joueur player = new Joueur();

        for (int idQuest : solution) {
            Quete currentQuest = scn.getQuete(idQuest);
            duree = duree + player.deplacer(currentQuest);

            Label turnNbLbl = new Label("Étape " + i++);
            turnNbLbl.setStyle("-fx-font-size: 0.7em");

            Label movementLbl = new Label("// Aller en " + Arrays.toString(currentQuest.getCoord()) + " --- Expérience totale : " + exp + " --- Temps total écoulé : " + duree + " //");
            movementLbl.setStyle("-fx-text-fill: #4d4d4d");

            duree = duree + currentQuest.getDuree();
            if (idQuest != 0) {
                exp = exp + currentQuest.getExp();
            }

            Label curentQuestLbl = new Label(" * " + currentQuest.getIntitule() + " *    (Quête : " + idQuest + ")");
            curentQuestLbl.setStyle("-fx-font-weight: bold; -fx-font-size: 1.2em");

            Label expPlusLbl;
            if (idQuest != 0) {
                expPlusLbl = new Label("           • Expérience gagnée : " + currentQuest.getExp());
            } else {
                expPlusLbl = new Label("           • Expérience nécessaire : " + currentQuest.getExp());
            }
            expPlusLbl.setStyle("-fx-text-fill: #205391");

            Label dureeLbl = new Label("           • Temps total écoulé : " + duree);
            dureeLbl.setStyle("-fx-text-fill: #863e1f");
            scrollBox.getChildren().addAll(new Separator(Orientation.HORIZONTAL), turnNbLbl, movementLbl, curentQuestLbl, expPlusLbl, dureeLbl);

            if (idQuest != 0) {
                recapStr.append(idQuest).append("  ->  ");
            } else {
                recapStr.append(idQuest);
            }
        }

        VBox recapBox = new VBox(3);
        Label recapTitleLbl = new Label("Récapitulatif :   " + recapStr);
        Label recapDureeLbl = new Label("Durée totale : " + duree);
        Label recapExpLbl = new Label("Expérience acquise : " + exp);

        recapBox.getChildren().addAll(recapTitleLbl, recapDureeLbl, recapExpLbl);
        recapBox.setPadding(new Insets(30));
        recapBox.setPrefHeight(50);

        getChildren().addAll(display, new Separator(Orientation.HORIZONTAL), recapBox);
    }
}
