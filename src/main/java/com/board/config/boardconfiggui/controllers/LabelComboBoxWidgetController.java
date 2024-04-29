package com.board.config.boardconfiggui.controllers;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;

public class LabelComboBoxWidgetController {
    @FXML
    private Label comboBoxLabel;

    @FXML
    private ComboBox<String> cmbInfo;
    private String selectedItem;
    public void setComboBoxLabel(String text) {
        comboBoxLabel.setText(text);
    }

    public void setCmbInfo(ObservableList<String> items) {
        cmbInfo.setItems(items);
    }

    public String getCmbInfoItem() {
        return cmbInfo.getSelectionModel().getSelectedItem();
    }
    @FXML
    private void comboBoxSelectionChanged() {
        selectedItem = cmbInfo.getSelectionModel().getSelectedItem();
        System.out.println("Selected item: " + selectedItem);
    }
}
