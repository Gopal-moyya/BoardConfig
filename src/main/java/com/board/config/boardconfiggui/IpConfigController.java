package com.board.config.boardconfiggui;

import com.board.config.boardconfiggui.interfaces.BoardPageDataSaverInterface;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

import java.net.URL;
import java.util.ResourceBundle;

public class IpConfigController implements Initializable, BoardPageDataSaverInterface {

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

    @Override
    public void saveData() {
        //Todo:- Need to implement save data
    }
}
