package com.board.config.boardconfiggui;

import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.util.Duration;

import java.io.IOException;
import java.util.logging.Logger;

public class HomeViewController {

    private static final Logger logger = Logger.getLogger(HomeViewController.class.getName());

    private String xmlFolderPath = null;
    private String repositoryFolderPath;
    private String toolChainFolderPath;
    private String outputLocationFolderPath;

    @FXML
    public Pane contentArea;
    @FXML
    private ImageView loaderImg;
    @FXML
    private Label loaderInfo;

    Timeline rotateAnimation;



    @FXML
    public void initialize() {
        loadDataView();
        Image loadingImage = new Image("loader.png");
        loaderImg.setImage(loadingImage);
        loaderImg.setOpacity(0);
        rotateAnimation = new Timeline(new javafx.animation.KeyFrame(
                Duration.millis(50),
                event -> loaderImg.setRotate(loaderImg.getRotate() + 30)));
        rotateAnimation.setCycleCount(Timeline.INDEFINITE);
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

    public void savePathData(String repositoryFolderPath, String toolChainFolderPath, String outputLocationFolderPath) {
        this.repositoryFolderPath = repositoryFolderPath;
        this.outputLocationFolderPath = outputLocationFolderPath;
        this.toolChainFolderPath = toolChainFolderPath;
    }

    public void onOutputGenerateClick() {
        loadDataView();
    }

    private void loadDataView() {
        Parent fxml = null;
        FXMLLoader loader = new FXMLLoader(getClass().getResource("load-data-view.fxml"));
        LoadDataController loadDataController = new LoadDataController(this, xmlFolderPath, repositoryFolderPath, toolChainFolderPath, outputLocationFolderPath);
        loader.setController(loadDataController);
        try {
            fxml = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        contentArea.getChildren().removeAll();
        contentArea.getChildren().setAll(fxml);
    }

    public void showAnimation(){
        loaderInfo.setText("Generating the code....");
        loaderImg.setOpacity(0.8);
        rotateAnimation.play();
    }

    public void stopAnimation(){
        loaderInfo.setText("");
        loaderImg.setOpacity(0);
        rotateAnimation.stop();
    }
}