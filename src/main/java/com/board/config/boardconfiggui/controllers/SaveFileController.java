package com.board.config.boardconfiggui.controllers;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class SaveFileController {
    @FXML
    private TextField txtBoardName;
    private Stage dialogStage;
    private boolean isContinueClicked = false;
    @FXML
    private Button btnContinue;

    public void setDialogStage(Stage dialogStage) {
        btnContinue.setDisable(true);
        txtBoardName.textProperty().addListener((observable, oldValue, newValue) -> {
            btnContinue.setDisable(newValue.trim().isEmpty() || newValue.trim().length() <= 3);
        });
        this.dialogStage = dialogStage;
    }

    public boolean isContinueClicked() {
        return isContinueClicked;
    }

    @FXML
    private void onContinueClicked() {
        isContinueClicked = true;
        dialogStage.close();
    }

    @FXML
    private void onCancelClicked() {
        dialogStage.close();
    }

    public String getBoardName() {
        return txtBoardName.getText();
    }
}
