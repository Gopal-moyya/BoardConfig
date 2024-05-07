package com.board.config.boardconfiggui;

import com.board.config.boardconfiggui.data.inputmodels.pinconfig.Pin;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.DialogPane;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

import java.io.IOException;
import java.net.URL;
import java.util.*;

public class PortConfigController implements Initializable {

  private final String portName;
  private final Map<String, Pin> currentPins;

  @FXML
  private Pane portConfigPane;

  public PortConfigController(String portName, Map<String, Pin> currentPins) {
    this.portName = portName;
    this.currentPins = currentPins;
  }

  @Override
  public void initialize(URL url, ResourceBundle resourceBundle) {

    GridPane gridPane = new GridPane();
    gridPane.setAlignment(Pos.CENTER);
    gridPane.setPadding(new Insets(60));
    gridPane.setHgap(10);
    gridPane.setVgap(10);

    int row = 0;
    int col = 0;
    List<String> pinNames = new ArrayList<>(currentPins.keySet().stream().toList());
    Collections.sort(pinNames);
    for(String pinName : pinNames){

      Button button = new Button(pinName);
      Font myFont = Font.font("", FontWeight.BOLD, 14);
      button.setFont(myFont);
      button.setPrefWidth(110);
      button.setPrefHeight(95);

      gridPane.add(button, col, row);

      button.onMouseClickedProperty().setValue(new EventHandler<MouseEvent>() {
        @Override
        public void handle(MouseEvent mouseEvent) {
          // Create a custom dialog
          Dialog<ButtonType> dialog = new Dialog<>();
          dialog.setTitle("Pin Configuration");

          // Create a DialogPane and set its content
          DialogPane dialogPane = new DialogPane();
          FXMLLoader loader = new FXMLLoader(getClass().getResource("pin-config.fxml"));
          Pin pin = currentPins.get(pinName);
          PinConfigController pinConfigController = new PinConfigController(portName, pin);
          loader.setController(pinConfigController);

          try {
            dialogPane.setContent(loader.load());
          } catch (IOException e) {
            e.printStackTrace();
          }

          dialogPane.getButtonTypes().addAll(ButtonType.OK);
          dialog.setDialogPane(dialogPane);

          // Show the dialog and wait for user response
          dialog.showAndWait().ifPresent(response -> {
            if (Objects.equals(response, ButtonType.OK)) {
              pinConfigController.saveData();
            }
          });
        }
      });

      col++;
      if (col >= 8) {
        col = 0;
        row++;
      }
    }

    portConfigPane.getChildren().add(gridPane);
  }
}
