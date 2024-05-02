package com.board.config.boardconfiggui.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.paint.Paint;

public class OnOffButtonWidgetController {
    @FXML
    private Label buttonLabel;

    @FXML
    private Button button;



    public void setButtonLabel(String text) {
        buttonLabel.setText(text);
    }

    public Button getButton() {
        return button;
    }

    public void setButton(String buttonText) {
        button.setText(buttonText);
    }

    @FXML
    private void buttonSelectionChanged(ActionEvent e){
        if("ON".equals(e.toString())) {
            button.setStyle("-fx-background-color: #ff0000");
        } else {
            button.setStyle("-fx-background-color: #0000ff");
        }
    }

    public void setButtonTextColor(Paint paint) {
        button.setTextFill(paint);
    }
}
