package david.controller;

import david.dao.SmartWordDao;
import david.model.SmartWord;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;

import java.util.ArrayList;


public class Controller {

    @FXML
    private TableView<SmartWord> smartWordsTable;
    @FXML
    private TableColumn<SmartWord, String> wordColumn;
    @FXML
    private TableColumn<SmartWord, String> definitionColumn;
    @FXML
    private TextField searchTF;
    @FXML
    private TextArea descriptionTF;
    @FXML
    private TextField nameTF;


    SmartWordDao smartWordDAO = new SmartWordDao();

    @FXML
    public void initialize() {
        onShowList();
        onInitializeApp();
    }

    @FXML
    public void onInitializeApp(){
        nameTF.setPromptText("Add new word");
        descriptionTF.setPromptText("Add definition");
        searchTF.setPromptText("Search");

    }
    @FXML
    public void onShowList(){
        ObservableList<SmartWord> smartWordObservableList = FXCollections.observableArrayList();

        if(smartWordsTable != null){
            smartWordsTable.setEditable(true);


            ArrayList<SmartWord> smartWords = smartWordDAO.getAllSmartWords();
            smartWordObservableList.addAll(smartWords);
            //smartWordObservableList.addAll(smartWordDAO.getAllSmartWords());
            wordColumn.setCellValueFactory(new PropertyValueFactory<SmartWord, String>("name"));
            definitionColumn.setCellValueFactory(new PropertyValueFactory<SmartWord, String>("definition"));


            wordColumn.setCellFactory(TextFieldTableCell.forTableColumn());
            wordColumn.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<SmartWord, String>>() {
                @Override
                public void handle(TableColumn.CellEditEvent<SmartWord, String> event) {
                    ((SmartWord) event.getTableView().getItems().get(
                            event.getTablePosition().getRow())
                    ).setName(event.getNewValue());

                    SmartWord smartWord = ((SmartWord)event.getTableView().getItems().get(event.getTablePosition().getRow()));
                    SmartWordDao smartWordDao = new SmartWordDao();

                    smartWordDao.updateSmartWord(smartWord);
                    //smartWordDao.deleteWord()
                    //System.out.println(smartWord.getName()+" "+smartWord.getId());
                    //System.out.println("jestem word "+event.getNewValue());
                }
            });

            definitionColumn.setCellFactory(TextFieldTableCell.forTableColumn());
            definitionColumn.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<SmartWord, String>>() {
                @Override
                public void handle(TableColumn.CellEditEvent<SmartWord, String> event) {
                    ((SmartWord) event.getTableView().getItems().get(
                            event.getTablePosition().getRow())
                    ).setDefinition(event.getNewValue());
                    SmartWord smartWord = ((SmartWord)event.getTableView().getItems().get(event.getTablePosition().getRow()));
                    SmartWordDao smartWordDao = new SmartWordDao();
                    smartWordDao.updateSmartWord(smartWord);

                }
            });


            //wordColumn.getStyleClass().add("Times New Roman,40");
            smartWordsTable.setItems(smartWordObservableList);
        }



        }

    @FXML
    public void onAddWord(){

        if(!nameTF.getText().isEmpty() && !descriptionTF.getText().isEmpty()){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Information Dialog");
            alert.setHeaderText("Add Operation");

            SmartWord smartWord = new SmartWord(nameTF.getText(), descriptionTF.getText());
            if(smartWordDAO.insertSmartWord(smartWord)){
                alert.setContentText("Your word was just added!");
                alert.showAndWait();
                onShowList();
                nameTF.clear();
                descriptionTF.clear();
            }else {
                alert.setContentText("Something gone wrong :(!");
                alert.showAndWait();
            }
            //onShowList();
        }


    }

    public void onDeleteElement(){

        if(smartWordsTable.getSelectionModel().getSelectedItem() != null){
            SmartWord smartWord = smartWordsTable.getSelectionModel().getSelectedItem();
            SmartWordDao smartWordDao = new SmartWordDao();

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Information Dialog");
            alert.setHeaderText("Delete Operation");

            if(smartWordDao.deleteWord(smartWord.getId())){
                alert.setContentText("Your word was just deleted!");
                alert.showAndWait();
                onShowList();
            }else {
                alert.setContentText("Something gone wrong :(");
                alert.showAndWait();
            }
            //System.out.println(smartWord.getName()+" "+smartWord.getId());
        }
    }

    public void onSearchWord(){
//        searchTF.setOnAction(new EventHandler<ActionEvent>() {
//            @Override
//            public void handle(ActionEvent event) {
//
//            }
//
//
//        });
//
        searchTF.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> ov, String t, String t1) {
                if(searchTF.getText().equals(""))
                System.out.println("Label Text Changed");
                onShowList();
            }
        });


        if(!searchTF.getText().isEmpty()){
            //System.out.println("Search me!");
            SmartWordDao smartWordDao = new SmartWordDao();
            //searchTF.clear();
            ObservableList<SmartWord> smartWordsObservableList = FXCollections.observableArrayList();
            if(smartWordsTable != null){
                smartWordsObservableList.addAll(smartWordDao.searchSmartWords(searchTF.getText()));
                wordColumn.setCellValueFactory(new PropertyValueFactory<SmartWord, String>("name"));
                definitionColumn.setCellValueFactory(new PropertyValueFactory<SmartWord, String>("definition"));
                //wordColumn.getStyleClass().add("Times New Roman,40");
                smartWordsTable.setItems(smartWordsObservableList);
            }

        }

    }
}
