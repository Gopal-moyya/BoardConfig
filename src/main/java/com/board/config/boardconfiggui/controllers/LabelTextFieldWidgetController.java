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


    public void setLabel(String text) {
        txtFieldLabel.setText(text);
    }

    public StringProperty getText() {
        return stringProperty;
    }

    public void setText(String information){
        txtInformation.setText(information);
        stringProperty.setValue(information);
    }

    @FXML
    private void onTextChanged() {
        enteredText = txtInformation.getText();
        stringProperty.setValue(enteredText);
    }
}
