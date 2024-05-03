package com.board.config.boardconfiggui.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;

public class LabelRadioButtonWidgetController {
    @FXML
    private Label radioButtonLabel;

    @FXML
    private RadioButton rbTrue;

    @FXML
    private RadioButton rbFalse;

    public void setRadioButtonLabel(String text) {
        radioButtonLabel.setText(text);
    }

    public boolean isRbTrueSelected() {
        return rbTrue.isSelected();
    }

    public boolean isRbFalseSelected() {
        return rbFalse.isSelected();
    }

    @FXML
    private void onSelection(ActionEvent e){
        if(rbTrue.isSelected()){
            //TODO : Need to handle

        }
        if(rbFalse.isSelected()){
            //TODO : Need to handle
        }
    }
}
