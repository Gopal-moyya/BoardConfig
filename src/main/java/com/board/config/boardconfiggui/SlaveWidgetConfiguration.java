package com.board.config.boardconfiggui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class SlaveWidgetConfiguration extends Application {
    public static void main(String[] args) {
        launch();
    }

    @Override
    public void start(Stage stage) throws Exception {

        FXMLLoader fxmlLoader = new FXMLLoader(SlaveWidgetConfiguration.class.getResource("slave-widget.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1000, 500);
        stage.setTitle("Slave Widget");
        stage.setScene(scene);
        stage.show();
    }
}
