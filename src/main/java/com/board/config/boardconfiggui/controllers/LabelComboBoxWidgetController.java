package com.board.config.boardconfiggui.controllers;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;

public class LabelComboBoxWidgetController {
    @FXML
    private Label comboBoxLabel;

    @FXML
    private ComboBox<String> comboBox;


    private final StringProperty selectedItem = new SimpleStringProperty();

    public void setComboBoxLabel(String text, String promptText) {
        comboBoxLabel.setText(text);
        comboBox.setPromptText(promptText);

        //Handled if prompt message not updated in UI
        comboBox.setButtonCell(new ListCell<>() {
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(promptText);
                } else {
                    setText(item);
                }
            }
        });
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

    public void setSelectedValue(String selectedValue){
        comboBox.valueProperty().setValue(selectedValue);
    }
}
