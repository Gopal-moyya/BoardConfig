package com.board.config.boardconfiggui.controllers;
import com.board.config.boardconfiggui.data.inputmodels.ipconfig.ChipInfo;
import com.board.config.boardconfiggui.data.inputmodels.ipconfig.Option;
import com.board.config.boardconfiggui.data.repo.InputConfigRepo;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.apache.commons.lang3.StringUtils;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;

public class SelectBoardNameController implements Initializable {

    @FXML
    public Label txtChipletName;
    @FXML
    public ComboBox<String> coreSpinner;
    @FXML
    private TextField txtBoardName;
    private Stage dialogStage;
    private boolean isContinueSelected = false;
    @FXML
    private Button btnContinue;
   private int MINIMUM_BOARD_LENGTH = 3;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ChipInfo chipInfo = InputConfigRepo.getInstance().getIpConfig().getChipInfo();
        if(Objects.isNull(chipInfo)){
            return;
        }
        Option chilpletOption = chipInfo.getOption();

        if(chilpletOption.getName().equalsIgnoreCase("chipletName")){
            txtChipletName.setText(chilpletOption.getValue());
        }

        List<String> coreNames = new ArrayList<>();
        for(Option core : chipInfo.getCoreList()){
            coreNames.add(core.getName());
        }

        coreSpinner.setItems(FXCollections.observableArrayList(coreNames));
    }

    public void setTxtBoardName(String txtBoardName) {
        this.txtBoardName.setText(txtBoardName);
    }

    public void setDialogStage(Stage dialogStage) {
        btnContinue.setDisable(true);
        txtBoardName.textProperty().addListener((observable, oldValue, newValue) -> {
            updateContinueButtonStatus();
        });
        this.dialogStage = dialogStage;
    }

    private void updateContinueButtonStatus() {
        String boardName = txtBoardName.getText();
        btnContinue.setDisable(StringUtils.isBlank(boardName) || boardName.trim().length() <= MINIMUM_BOARD_LENGTH
                || StringUtils.isBlank(coreSpinner.getSelectionModel().getSelectedItem()));
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

    public String getChipletName() {
        return txtChipletName.getText();
    }

    public String getCoreName() {
        return coreSpinner.getSelectionModel().getSelectedItem();
    }

    @FXML
    public void onCoreNameChange() {
        updateContinueButtonStatus();
    }

    public void setCoreName(String coreName) {
        coreSpinner.valueProperty().setValue(coreName);
        updateContinueButtonStatus();
    }
}
