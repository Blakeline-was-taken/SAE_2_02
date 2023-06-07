package controleur;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.ToggleButton;
import vue.ModeSelection;

import java.util.Scanner;

public class ModeSelectionControleur implements EventHandler<ActionEvent> {
    public void handle(ActionEvent event){
        if (event.getSource() instanceof ToggleButton btn){
            String sourceMenu = (String) btn.getToggleGroup().getUserData();
            String buttonContent = (String) btn.getUserData();
            switch (sourceMenu){
                case "Mode" -> {
                    if (ModeSelection.getAfterModeField().size() > 0){
                        ModeSelection.getAfterModeField().clear();
                    }
                    ModeSelection.setSelectedMode(buttonContent);
                    if (buttonContent.equals("Glouton")){
                        ModeSelection.addMethodePanel(ModeSelection.getAfterModeField());
                    } else if (buttonContent.equals("Optimise")) {
                        ModeSelection.addOptimisationPanel(ModeSelection.getAfterModeField());
                    }
                }
                case "Methode" -> {
                    if (ModeSelection.getAfterMethodField().size() > 0){
                        ModeSelection.getAfterMethodField().clear();
                    }
                    ModeSelection.setSelectedMethod(buttonContent);
                    if (ModeSelection.getSelectedMode().equals("Glouton")){
                        ModeSelection.showSolution(ModeSelection.getAfterMethodField());
                    } else if (ModeSelection.getSelectedMode().equals("Optimise")) {
                        ModeSelection.addNbSolutionPanel(ModeSelection.getAfterMethodField());
                    }
                }
                case "Opti" -> {
                    if (ModeSelection.getAfterOptiField().size() > 0){
                        ModeSelection.getAfterOptiField().clear();
                    }
                    ModeSelection.setSelectedOpti(buttonContent);
                    ModeSelection.addMethodePanel(ModeSelection.getAfterOptiField());
                }
            }
        } else if (event.getSource() instanceof Button btn) {
            String buttonContent = (String) btn.getUserData();
            switch (buttonContent) {
                case "Texte" -> {
                    if (ModeSelection.getAfterNbSolField().size() > 0){
                        ModeSelection.getAfterNbSolField().clear();
                    }
                    Scanner scanTxtField = new Scanner(ModeSelection.getNbSol());
                    try {
                        ModeSelection.setSelectedNbSol(scanTxtField.nextInt());
                        ModeSelection.setSelectedTypeSol(ModeSelection.getTypeSol());
                        ModeSelection.showSolution(ModeSelection.getAfterNbSolField());
                    } catch (Exception e) {
                        System.out.println("Valeur invalide, rien ne se passe");
                    }
                }
                case "Toutes" -> {
                    if (ModeSelection.getAfterNbSolField().size() > 0){
                        ModeSelection.getAfterNbSolField().clear();
                    }
                    ModeSelection.setSelectedNbSol(Integer.MAX_VALUE);
                    ModeSelection.setSelectedTypeSol(ModeSelection.getTypeSol());
                    ModeSelection.showSolution(ModeSelection.getAfterNbSolField());
                }
                case "Valider" -> {
                    // TODO : Appeler l'affichage des solutions (en fonction du nb de sol.)
                }
            }
        }
    }
}
