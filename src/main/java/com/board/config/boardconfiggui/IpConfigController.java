package com.board.config.boardconfiggui;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

import java.net.URL;
import java.util.ResourceBundle;

public class IpConfigController implements Initializable {

    private String ipName;

    @FXML
    public Label textView;

    public IpConfigController(String ipName) {
        this.ipName = ipName;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        textView.setText("IP CONFIG : " + ipName);
    }

}
