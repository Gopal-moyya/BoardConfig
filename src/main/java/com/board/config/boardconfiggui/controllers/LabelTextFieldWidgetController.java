package com.board.config.boardconfiggui.controllers;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class LabelTextFieldWidgetController {
    @FXML
    private Label txtFieldLabel;

    @FXML
    private TextField txtInformation;
    private String enteredText;

    private final StringProperty stringProperty = new SimpleStringProperty();


    public void setTxtFieldLabel(String text) {
        txtFieldLabel.setText(text);
    }

    public StringProperty getTxtInformation() {
        return stringProperty;
    }

    public void setTxtInformation(String information){
        txtInformation.setText(information);
        stringProperty.setValue(information);
    }

    @FXML
    private void textFieldTextChanged() {
        enteredText = txtInformation.getText();
        System.out.println("Entered text: " + enteredText);
        stringProperty.setValue(enteredText);
    }
}
