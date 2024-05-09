package com.board.config.boardconfiggui;

import com.board.config.boardconfiggui.data.Constants;
import com.board.config.boardconfiggui.data.inputmodels.pinconfig.Pin;
import com.board.config.boardconfiggui.ui.models.PinType;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.net.URL;
import java.util.*;

public class PortConfigController implements Initializable {

  private final String portName;
  private final Map<String, Pin> currentPins;
  private final Map<String, List<PinType>> pinTypes;

  @FXML
  private Pane portConfigPane;

  public PortConfigController(String portName, Map<String, Pin> currentPins, Map<String, List<PinType>> pinTypes) {
    this.portName = portName;
    this.currentPins = currentPins;
    this.pinTypes = pinTypes;
  }

  @Override
  public void initialize(URL url, ResourceBundle resourceBundle) {

    GridPane gridPane = new GridPane();
    gridPane.setAlignment(Pos.CENTER);
    gridPane.setPadding(new Insets(40));
    gridPane.setHgap(10);
    gridPane.setVgap(10);

    ColumnConstraints columnConstraints = new ColumnConstraints();
    columnConstraints.setHgrow(Priority.ALWAYS);
    gridPane.getColumnConstraints().add(columnConstraints);

    RowConstraints rowConstraints = new RowConstraints();
    rowConstraints.setVgrow(Priority.ALWAYS);
    gridPane.getRowConstraints().add(rowConstraints);

    int row = 0;
    int col = 0;
    List<String> pinNames = new ArrayList<>(currentPins.keySet().stream().toList());
    Collections.sort(pinNames);
    for(String pinName : pinNames){

      Button button = new Button(pinName);
      Font myFont = Font.font("", FontWeight.BOLD, 10);
      button.setFont(myFont);
      button.setMinWidth(75);
      button.setMinHeight(45);

      gridPane.add(button, col, row);

      button.onMouseClickedProperty().setValue(new EventHandler<MouseEvent>() {
        @Override
        public void handle(MouseEvent mouseEvent) {
          // Create a custom dialog
          Dialog<ButtonType> dialog = new Dialog<>();
          dialog.initStyle(StageStyle.UNDECORATED);

          // Create a DialogPane and set its content
          DialogPane dialogPane = new DialogPane();

          // Add title to the header
          VBox titleBox = new VBox();
          titleBox.setPrefHeight(30);
          titleBox.setAlignment(Pos.CENTER);
          Label title = new Label("Pin Configuration for " + portName + " Pin " + pinName);
          title.setFont(myFont);
          titleBox.getChildren().add(title);

          dialogPane.setHeader(titleBox);
          Border border = new Border(new BorderStroke(null, BorderStrokeStyle.SOLID, null, null));
          dialogPane.setBorder(border);

          FXMLLoader loader = new FXMLLoader(getClass().getResource("pin-config.fxml"));
          Pin pin = currentPins.get(pinName);
          PinConfigController pinConfigController = new PinConfigController(portName, pin, pinTypes.get(portName+"_"+pinName));
          loader.setController(pinConfigController);

          try {
            dialogPane.setContent(loader.load());
          } catch (IOException e) {
            e.printStackTrace();
          }

          dialogPane.getButtonTypes().add(new ButtonType(Constants.OK, ButtonBar.ButtonData.LEFT));
          dialog.setDialogPane(dialogPane);

          // Show the dialog and wait for user response
          dialog.showAndWait().ifPresent(response -> {
            pinConfigController.saveData();
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
