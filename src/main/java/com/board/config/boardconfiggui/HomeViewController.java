package com.board.config.boardconfiggui;

import com.board.config.boardconfiggui.common.Utils;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.layout.Pane;
import org.apache.commons.lang3.ObjectUtils;

import java.io.File;
import java.io.IOException;
import java.util.logging.Logger;

public class HomeViewController {

    private static final Logger logger = Logger.getLogger(BoardConfigController.class.getName());

    @FXML
    public Pane contentArea;

    @FXML
    public void initialize() {
        loadDataView("");
    }

    public void onConfigureClick(String xmlFolderPath , String boardName) {

        if (validateXmlFolder(xmlFolderPath)) {
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
        } else {
            Utils.alertDialog(Alert.AlertType.ERROR, "No Files found", "Configuration files are not available in the selected Directory.");
        }
    }

    private boolean validateXmlFolder(String xmlFolderPath) {
        File hardwareConfigFile = new File(xmlFolderPath + "/hardware_configuration.xml");
        File pinMuxingConfigFile = new File(xmlFolderPath + "/pin_muxing.xml");

        return hardwareConfigFile.exists() && pinMuxingConfigFile.exists();
    }

    public void onOutputGenerateClick(String xmlFolderPath) {
        loadDataView(xmlFolderPath);
    }

    private void loadDataView(String xmlFolderPath) {
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