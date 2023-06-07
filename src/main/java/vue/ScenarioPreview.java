package vue;

import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import module.Quete;
import module.Scenario;

public class ScenarioPreview extends VBox {
    public ScenarioPreview(Scenario scn){
        setPadding(new Insets(15));

        TableView <QuestView> previewTab = new TableView<>();

        TableColumn <QuestView, Integer> idQuest = new TableColumn<>("N° Quete");
        idQuest.setCellValueFactory(new PropertyValueFactory<>("id"));
        idQuest.setPrefWidth(100);

        TableColumn <QuestView, String> nomQuest = new TableColumn<>("Nom");
        nomQuest.setCellValueFactory(new PropertyValueFactory<>("intitule"));
        nomQuest.setPrefWidth(400);

        TableColumn <QuestView, String> posQuest = new TableColumn<>("Position");
        posQuest.setCellValueFactory(new PropertyValueFactory<>("posView"));
        posQuest.setPrefWidth(100);

        TableColumn <QuestView, Integer> dureeQuest = new TableColumn<>("Durée");
        dureeQuest.setCellValueFactory(new PropertyValueFactory<>("duree"));
        dureeQuest.setPrefWidth(100);

        TableColumn <QuestView, Integer> expQuest = new TableColumn<>("Expérience");
        expQuest.setCellValueFactory(new PropertyValueFactory<>("exp"));
        expQuest.setPrefWidth(100);

        TableColumn <QuestView, String> condQuest = new TableColumn<>("Conditions");
        condQuest.setCellValueFactory(new PropertyValueFactory<>("precondView"));
        condQuest.setPrefWidth(200);

        previewTab.getColumns().addAll(idQuest, nomQuest, posQuest, dureeQuest, expQuest, condQuest);
        previewTab.setPrefHeight(625);
        previewTab.setPrefWidth(1002);

        for (TableColumn <QuestView, ?> col : previewTab.getColumns()){
            col.setResizable(false);
            col.setSortable(false);
            col.setReorderable(false);
        }

        for (int questID : scn.getQuetes().keySet()){
            Quete quest = scn.getQuete(questID);
            QuestView currentQuest = new QuestView(questID, quest.getCoord(), quest.getCond(), quest.getDuree(), quest.getExp(), quest.getIntitule());
            previewTab.getItems().add(currentQuest);
        }

        Button btn = new Button("Valider le choix du scénario");
        btn.setOnAction(Root.getScnControleur());

        getChildren().addAll(previewTab, btn);
    }

    public static class QuestView extends Quete{
        private final int id;
        QuestView(int parId, int[] parCoord, int[][] parCond, int parDuree, int parExp, String parIntitule){
            super(parCoord, parCond, parDuree, parExp, parIntitule);
            id = parId;
        }
        public int getId() {
            return id;
        }

        public String getPosView(){
            return "(" + getCoord()[0] + " ; " + getCoord()[1] + ")";
        }

        public String getPrecondView(){
            String result = "";
            if (getCond()[0][0] == 0){
                result = "Aucune condition";
            } else {
                result = result + getCond()[0][0] + " ";
                if (getCond()[0][1] != 0){
                    result = result + "ou " + getCond()[0][1] + " ";
                }
                result = result + "finie\n";
                if (getCond()[1][0] != 0){
                    result = result + "et " + getCond()[1][0] + " ";
                    if (getCond()[1][1] != 0){
                        result = result + "ou " + getCond()[1][1] + " ";
                    }
                    result = result + "finie";
                }
            }
            return result;
        }
    }
}
