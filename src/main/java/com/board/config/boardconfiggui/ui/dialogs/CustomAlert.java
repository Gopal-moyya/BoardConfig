package com.board.config.boardconfiggui.ui.dialogs;

import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class CustomAlert extends Alert {

    public CustomAlert(Alert.AlertType alertType, String title, String headerText, String message) {
        super(alertType);
        setTitle(title);
        setHeaderText(headerText);

        // Custom content for the dialog
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);

        // Split message into parts
        String[] messageParts = message.split("\n");

        for (String part : messageParts) {
            // Split each part into key-value pair
            String[] keyValue = part.split(":");
            if (keyValue.length == 2) {
                // Create labels for key and value, apply font style
                Label keyLabel = new Label(keyValue[0] + ":");
                keyLabel.setFont(Font.font("Arial", FontWeight.BOLD, 12));
                Label valueLabel = new Label(keyValue[1]);
                grid.addRow(grid.getRowCount(), keyLabel, valueLabel);
            } else {
                // If not in the format key:value, add as a normal label
                Label label = new Label(part);
                grid.addRow(grid.getRowCount(), label);
            }
        }

        // Set the content of the dialog to the custom grid
        getDialogPane().setContent(grid);
    }
}
