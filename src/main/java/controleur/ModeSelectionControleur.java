package controleur;

import core.*;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Orientation;
import javafx.scene.control.Button;
import javafx.scene.control.Separator;
import javafx.scene.control.ToggleButton;
import vue.ModeSelection;
import vue.MultipleSolutionView;
import vue.Root;
import vue.UniqueSolutionsView;

import java.io.FileNotFoundException;
import java.util.ArrayList;

/**
 * Classe de controleur permettant de lier la vue au modèle lors de la sélection des paramètres de parcours
 * Implémente EventHandler
 */
public class ModeSelectionControleur implements EventHandler<ActionEvent> {

    /**
     * Gère les événements déclenchés par les boutons.
     *
     * @param event l'événement déclenché
     */
    public void handle(ActionEvent event) {
        if (event.getSource() instanceof ToggleButton btn) {
            String sourceMenu = (String) btn.getToggleGroup().getUserData();
            String buttonContent = (String) btn.getUserData();
            switch (sourceMenu) {
                case "Mode" -> {
                    if (ModeSelection.getAfterModeField().size() > 0) {
                        ModeSelection.getAfterModeField().clear();
                    }
                    ModeSelection.setSelectedMode(buttonContent);
                    if (buttonContent.equals("Glouton")) {
                        ModeSelection.addMethodePanel(ModeSelection.getAfterModeField());
                    } else if (buttonContent.equals("Optimise")) {
                        ModeSelection.addOptimisationPanel(ModeSelection.getAfterModeField());
                    }
                }
                case "Methode" -> {
                    if (ModeSelection.getAfterMethodField().size() > 0) {
                        ModeSelection.getAfterMethodField().clear();
                    }
                    ModeSelection.setSelectedMethod(buttonContent);
                    if (ModeSelection.getSelectedMode().equals("Glouton")) {
                        ModeSelection.showSolution(ModeSelection.getAfterMethodField());
                    } else if (ModeSelection.getSelectedMode().equals("Optimise")) {
                        ModeSelection.addNbSolutionPanel(ModeSelection.getAfterMethodField());
                    }
                }
                case "Opti" -> {
                    if (ModeSelection.getAfterOptiField().size() > 0) {
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
                            try {
                                assert Integer.parseInt(ModeSelection.getNbSol()) > 0;
                                ModeSelection.setSelectedNbSol(Integer.parseInt(ModeSelection.getNbSol()));
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
                            ModeSelection.setSelectedNbSol(2000000);
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
                    int scnUsed = Integer.parseInt(Root.getSelectedScn().replace("scenario_", "").replace(".txt", ""));
                    if (mode.equals("Glouton")) {
                        try {
                            Glouton parcours = new Glouton(scnUsed);
                            ArrayList<Integer>[] solution = parcours.run(methode.equals("Efficace"), true);

                            Root.initSolutionView(mode, methode);
                            Root.getRightField().clear();
                            Root.getRightField().addAll(new Separator(Orientation.VERTICAL), new UniqueSolutionsView(solution));
                        } catch (FileNotFoundException e) {
                            throw new RuntimeException(e);
                        }
                    } else if (mode.equals("Optimise")) {
                        String typeSol = ModeSelection.getSelectedTypeSol();
                        switch (ModeSelection.getSelectedOpti()) {
                            case "Duree" -> {
                                BaseMoteur bot;
                                ArrayList<Integer>[] solution;
                                if (ModeSelection.getSelectedNbSol() == 1) {
                                    try {
                                        bot = new Speedrun(scnUsed);
                                        solution = bot.run(methode.equals("Efficace"), typeSol.equals("Meilleures"));
                                        Root.initSolutionView(mode, methode, ModeSelection.getSelectedOpti(), String.valueOf(ModeSelection.getSelectedNbSol()), typeSol);
                                        Root.getRightField().clear();
                                        Root.getRightField().addAll(new Separator(Orientation.VERTICAL), new UniqueSolutionsView(solution));
                                    } catch (FileNotFoundException e) {
                                        throw new RuntimeException(e);
                                    }
                                } else {
                                    try {
                                        bot = new Speedrun(scnUsed, ModeSelection.getSelectedNbSol());
                                        solution = bot.run(methode.equals("Efficace"), typeSol.equals("Meilleures"));
                                        Root.initSolutionView(mode, methode, ModeSelection.getSelectedOpti(), String.valueOf(ModeSelection.getSelectedNbSol()), typeSol);
                                        Root.getRightField().clear();
                                        Root.getRightField().addAll(new Separator(Orientation.VERTICAL), new MultipleSolutionView(solution));
                                    } catch (FileNotFoundException e) {
                                        throw new RuntimeException(e);
                                    }
                                }
                            }
                            case "Dist" -> {
                                BaseMoteur bot;
                                ArrayList<Integer>[] solution;
                                if (ModeSelection.getSelectedNbSol() == 1) {
                                    try {
                                        bot = new Lazy(scnUsed);
                                        solution = bot.run(methode.equals("Efficace"), typeSol.equals("Meilleures"));
                                        Root.initSolutionView(mode, methode, ModeSelection.getSelectedOpti(), String.valueOf(ModeSelection.getSelectedNbSol()), typeSol);
                                        Root.getRightField().clear();
                                        Root.getRightField().addAll(new Separator(Orientation.VERTICAL), new UniqueSolutionsView(solution));
                                    } catch (FileNotFoundException e) {
                                        throw new RuntimeException(e);
                                    }
                                } else {
                                    try {
                                        bot = new Lazy(scnUsed, ModeSelection.getSelectedNbSol());
                                        solution = bot.run(methode.equals("Efficace"), typeSol.equals("Meilleures"));
                                        Root.initSolutionView(mode, methode, ModeSelection.getSelectedOpti(), String.valueOf(ModeSelection.getSelectedNbSol()), typeSol);
                                        Root.getRightField().clear();
                                        Root.getRightField().addAll(new Separator(Orientation.VERTICAL), new MultipleSolutionView(solution));
                                    } catch (FileNotFoundException e) {
                                        throw new RuntimeException(e);
                                    }
                                }
                            }
                            case "NbQuest" -> {
                                BaseMoteur bot;
                                ArrayList<Integer>[] solution;
                                if (ModeSelection.getSelectedNbSol() == 1) {
                                    try {
                                        bot = new NombreQuetes(scnUsed);
                                        solution = bot.run(methode.equals("Efficace"), typeSol.equals("Meilleures"));
                                        Root.initSolutionView(mode, methode, ModeSelection.getSelectedOpti(), String.valueOf(ModeSelection.getSelectedNbSol()), typeSol);
                                        Root.getRightField().clear();
                                        Root.getRightField().addAll(new Separator(Orientation.VERTICAL), new UniqueSolutionsView(solution));
                                    } catch (FileNotFoundException e) {
                                        throw new RuntimeException(e);
                                    }
                                } else {
                                    try {
                                        bot = new NombreQuetes(scnUsed, ModeSelection.getSelectedNbSol());
                                        solution = bot.run(methode.equals("Efficace"), typeSol.equals("Meilleures"));
                                        Root.initSolutionView(mode, methode, ModeSelection.getSelectedOpti(), String.valueOf(ModeSelection.getSelectedNbSol()), typeSol);
                                        Root.getRightField().clear();
                                        Root.getRightField().addAll(new Separator(Orientation.VERTICAL), new MultipleSolutionView(solution));
                                    } catch (FileNotFoundException e) {
                                        throw new RuntimeException(e);
                                    }
                                }
                            }
                        }
                    }
                }
                case "New" -> Root.initSelectionView();
            }
        }
    }
}
