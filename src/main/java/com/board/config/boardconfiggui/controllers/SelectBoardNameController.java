package com.board.config.boardconfiggui.controllers;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class SelectBoardNameController {
    @FXML
    private TextField txtBoardName;
    private Stage dialogStage;
    private boolean isContinueSelected = false;
    @FXML
    private Button btnContinue;
   private int MINIMUM_BOARD_LENGTH = 3;

    public void setDialogStage(Stage dialogStage) {
        btnContinue.setDisable(true);
        txtBoardName.textProperty().addListener((observable, oldValue, newValue) -> {
            btnContinue.setDisable(newValue.trim().length() <= MINIMUM_BOARD_LENGTH);
        });
        this.dialogStage = dialogStage;
    }

    public boolean isContinueSelected() {
        return isContinueSelected;
    }

    @FXML
    private void onContinueClicked() {
        isContinueSelected = true;
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
