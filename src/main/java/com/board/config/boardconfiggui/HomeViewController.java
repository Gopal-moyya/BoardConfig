package com.board.config.boardconfiggui;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.Pane;

import java.io.IOException;

public class HomeViewController {

    @FXML
    public Pane contentArea;

    @FXML
    public void initialize(){
        loadDataView();
    }

    public void onConfigureClick(String xmlFolderPath, String boardName) {
        if(validateXmlFolder()){
            Parent fxml = null;
            FXMLLoader loader = new FXMLLoader(getClass().getResource("board-config.fxml"));
            BoardConfigController boardConfigController = new BoardConfigController(xmlFolderPath, boardName, this);
            loader.setController(boardConfigController);
            try {
                fxml = loader.load();
            } catch (IOException e) {
                e.printStackTrace();
            }
            contentArea.getChildren().removeAll();
            contentArea.getChildren().setAll(fxml);
        }
    }

    private boolean validateXmlFolder() {
        //TODO :
        return true;
    }

    public void onOutputGenerateClick() {
        loadDataView();
    }

    private void loadDataView(){
        Parent fxml = null;
        FXMLLoader loader = new FXMLLoader(getClass().getResource("load-data-view.fxml"));
        LoadDataController loadDataController = new LoadDataController(this);
        loader.setController(loadDataController);
        try {
            fxml = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        contentArea.getChildren().removeAll();
        contentArea.getChildren().setAll(fxml);
    }
}