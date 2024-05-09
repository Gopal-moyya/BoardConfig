package com.board.config.boardconfiggui;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.Pane;

import java.io.IOException;
import java.util.logging.Logger;

public class HomeViewController {

    private static final Logger logger = Logger.getLogger(HomeViewController.class.getName());

    private String xmlFolderPath = null;

    @FXML
    public Pane contentArea;


    @FXML
    public void initialize() {
        loadDataView();
    }


    public void onConfigureClick(String xmlFolderPath) {

        this.xmlFolderPath = xmlFolderPath;

        Parent fxml = null;
        FXMLLoader loader = new FXMLLoader(getClass().getResource("board-config.fxml"));
        BoardConfigController boardConfigController = new BoardConfigController(xmlFolderPath, this);
        loader.setController(boardConfigController);
        try {
            fxml = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        contentArea.getChildren().removeAll();
        contentArea.getChildren().setAll(fxml);
    }

    public void onOutputGenerateClick() {
        loadDataView();
    }

    private void loadDataView() {
        Parent fxml = null;
        FXMLLoader loader = new FXMLLoader(getClass().getResource("load-data-view.fxml"));
        LoadDataController loadDataController = new LoadDataController(this, xmlFolderPath);
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
