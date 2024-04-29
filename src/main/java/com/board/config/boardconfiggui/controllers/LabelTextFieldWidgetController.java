package com.board.config.boardconfiggui.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class LabelTextFieldWidgetController {
    @FXML
    private Label txtFieldLabel;

    @FXML
    private TextField txtInformation;
    private String enteredText;

    public void setTxtFieldLabel(String text) {
        txtFieldLabel.setText(text);
    }

    public String getTxtInformation() {
        return txtInformation.getText();
    }
    public void setTxtInformation(String information){
        txtInformation.setText(information);
    }

    @FXML
    private void textFieldTextChanged() {
        enteredText = txtInformation.getText();
        System.out.println("Entered text: " + enteredText);
    }
}
