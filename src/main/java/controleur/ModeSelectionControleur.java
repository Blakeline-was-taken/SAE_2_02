package controleur;

import core.Glouton;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Orientation;
import javafx.scene.control.Button;
import javafx.scene.control.Separator;
import javafx.scene.control.ToggleButton;
import vue.ModeSelection;
import vue.Root;
import vue.UniqueSolutionsView;

import java.io.FileNotFoundException;
import java.util.ArrayList;
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
                case "NbSol" -> {
                    switch (buttonContent) {
                        case "Texte" -> {
                            if (ModeSelection.getAfterNbSolField().size() > 0) {
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
                            if (ModeSelection.getAfterNbSolField().size() > 0) {
                                ModeSelection.getAfterNbSolField().clear();
                            }
                            ModeSelection.setSelectedNbSol(Integer.MAX_VALUE);
                            ModeSelection.setSelectedTypeSol(ModeSelection.getTypeSol());
                            ModeSelection.showSolution(ModeSelection.getAfterNbSolField());
                        }
                    }
                }
            }
        } else if (event.getSource() instanceof Button btn) {
            String buttonContent = (String) btn.getUserData();
            switch (buttonContent) {
                case "Valider" -> {
                    String mode = ModeSelection.getSelectedMode();
                    String methode = ModeSelection.getSelectedMethod();
                    if (mode.equals("Glouton")){
                        try {
                            String scn = Root.getSelectedScn();
                            scn = scn.replace("scenario_", "").replace(".txt", "");
                            Glouton parcours = new Glouton(Integer.parseInt(scn));
                            ArrayList<Integer> solution = parcours.run(methode.equals("Efficace"));

                            Root.initSolutionView(mode, methode);
                            Root.getRightField().clear();
                            Root.getRightField().addAll(new Separator(Orientation.VERTICAL), new UniqueSolutionsView(solution));
                        } catch (FileNotFoundException e) {
                            throw new RuntimeException(e);
                        }
                    } else if (mode.equals("Optimise")) {
                        return; // TODO : Check si = 1 (afficher en 1 solution comme glouton) sinon multiple sol avec tableau
                    }
                }
                case "New" -> {
                    Root.initSelectionView();
                }
            }
        }
    }
}
