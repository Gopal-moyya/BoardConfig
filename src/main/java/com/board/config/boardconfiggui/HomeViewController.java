package com.board.config.boardconfiggui;

import com.board.config.boardconfiggui.ui.models.ConfigPathsModel;
import com.invecas.interfaces.IUpdateListener;
import javafx.animation.Timeline;
import javafx.application.Platform;
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

public class HomeViewController implements IUpdateListener {

    private static final Logger logger = Logger.getLogger(HomeViewController.class.getName());

    ConfigPathsModel configPathsModel;

    @FXML
    public Pane contentArea;
    @FXML
    private ImageView loaderImg;
    @FXML
    private Label loaderInfo;

    Timeline rotateAnimation;




    @FXML
    public void initialize() {

        configPathsModel = new ConfigPathsModel();
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

        configPathsModel.setXmlPath(xmlFolderPath);

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

    public void savePathData(ConfigPathsModel configPathsModel) {
        this.configPathsModel = configPathsModel;
    }

    public void onOutputGenerateClick() {
        loadDataView();
    }

    private void loadDataView() {
        Parent fxml = null;
        FXMLLoader loader = new FXMLLoader(getClass().getResource("load-data-view.fxml"));
        LoadDataController loadDataController = new LoadDataController(this,configPathsModel);
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

    @Override
    public void onUpdate(String s) {
        try {
            updateLoaderText(s);
            Thread.sleep(100);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

    }

    public void updateLoaderText(String str){
        Platform.runLater(()->{
            loaderInfo.setText(str);
        });
    }
}
