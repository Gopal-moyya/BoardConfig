package com.board.config.boardconfiggui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.io.IOException;

public class BoardConfigApplication extends Application {

    //TODO : Need to check these screen sizes
    int screenWidth = (int) Screen.getPrimary().getBounds().getWidth()-100;
    int screenHeight = (int) Screen.getPrimary().getBounds().getHeight()-100;

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(BoardConfigApplication.class.getResource("home-view.fxml"));
        //TODO : Need to check these screen sizes
//        int sceneWidth = Math.max(screenWidth, 800);
//        int sceneHeight = Math.max(screenHeight, 600);
        Scene scene = new Scene(fxmlLoader.load(), 1368, 1000);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }

}