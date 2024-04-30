package com.board.config.boardconfiggui;

import com.board.config.boardconfiggui.controllers.LabelComboBoxWidgetController;
import com.board.config.boardconfiggui.controllers.LabelRadioButtonWidgetController;
import com.board.config.boardconfiggui.controllers.LabelTextFieldWidgetController;
import com.board.config.boardconfiggui.controllers.SlaveWidgetController;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.HashMap;
import java.util.Map;

public class MainApplicationClass extends Application {
    private final Map<String, FXMLLoader> loaderDataMap = new HashMap<>();

    @Override
    public void start(Stage stage) throws Exception {
    //TODO: this needs to be updated
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/main_layout.fxml"));
        VBox root = loader.load();

        FXMLLoader labelTextFieldLoader = new FXMLLoader(getClass().getResource("/text_field_widget.fxml"));
        root.getChildren().add(labelTextFieldLoader.load());
        LabelTextFieldWidgetController labelTextFieldController = labelTextFieldLoader.getController();
        labelTextFieldController.setTxtFieldLabel("Label for Text Field:");
        loaderDataMap.put("text", labelTextFieldLoader);

        FXMLLoader labelRadioButtonLoader = new FXMLLoader(getClass().getResource("/radio_button_view_widget.fxml"));
        root.getChildren().add(labelRadioButtonLoader.load());
        LabelRadioButtonWidgetController labelRadioButtonWidgetController = labelRadioButtonLoader.getController();
        labelRadioButtonWidgetController.setRadioButtonLabel("Label for radio button:");
        loaderDataMap.put("radio", labelRadioButtonLoader);

        FXMLLoader labelRadioButtonLoader1 = new FXMLLoader(getClass().getResource("/radio_button_view_widget.fxml"));
        root.getChildren().add(labelRadioButtonLoader1.load());
        LabelRadioButtonWidgetController labelRadioButtonWidgetController1 = labelRadioButtonLoader1.getController();
        labelRadioButtonWidgetController1.setRadioButtonLabel("Label for radio button1:");
        loaderDataMap.put("radio1", labelRadioButtonLoader1);


        FXMLLoader labelComboBoxLoader = new FXMLLoader(getClass().getResource("/dropdown_widget.fxml"));
        root.getChildren().add(labelComboBoxLoader.load());
        LabelComboBoxWidgetController labelComboBoxController = labelComboBoxLoader.getController();
        labelComboBoxController.setComboBoxLabel("Label for Combo Box:","");
        labelComboBoxController.setCmbInfo(FXCollections.observableArrayList("Option 1", "Option 2", "Option 3"));
        loaderDataMap.put("combo", labelComboBoxLoader);

        stage.setScene(new Scene(root, 1800, 900));
        stage.setTitle("Example");
        stage.show();

    }

    public static void main(String[] args) {
        launch();
    }
}