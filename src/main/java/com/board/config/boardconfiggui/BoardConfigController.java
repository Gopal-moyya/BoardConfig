package com.board.config.boardconfiggui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class BoardConfigController {
    @FXML
    private Label welcomeText;

    @FXML
    protected void onHelloButtonClick() {
        welcomeText.setText("Welcome to JavaFX Application!");
    }
}