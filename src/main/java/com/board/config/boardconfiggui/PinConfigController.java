package com.board.config.boardconfiggui;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

import java.net.URL;
import java.util.ResourceBundle;

public class PinConfigController implements Initializable {

    @FXML
    public Label textView;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        textView.setText("PIN CONFIG");
    }
}
