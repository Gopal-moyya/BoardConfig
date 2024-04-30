package com.board.config.boardconfiggui.controllers;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;

public class LabelComboBoxWidgetController {
    @FXML
    private Label comboBoxLabel;

    @FXML
    private ComboBox<String> comboBox;

    private final StringProperty selectedItem = new SimpleStringProperty();

    public void setComboBoxLabel(String text, String promptText) {
        comboBoxLabel.setText(text);
        comboBox.setPromptText(promptText);
    }

    public void setItems(ObservableList<String> items) {
        comboBox.setItems(items);
    }

    public StringProperty getCmbInfoItem() {
        return selectedItem;
    }
    @FXML
    private void onSelectionChanged() {
        selectedItem.setValue(comboBox.getSelectionModel().getSelectedItem());
    }
}
