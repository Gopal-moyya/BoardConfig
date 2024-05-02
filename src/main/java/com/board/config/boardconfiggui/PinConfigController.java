package com.board.config.boardconfiggui;

import com.board.config.boardconfiggui.data.inputmodels.pinconfig.Pin;
import com.board.config.boardconfiggui.interfaces.BoardPageDataSaverInterface;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

import java.net.URL;
import java.util.ResourceBundle;

public class PinConfigController implements Initializable, BoardPageDataSaverInterface {

    private final String portName;
    private final Pin currentPin;

    @FXML
    public Label textView;

    public PinConfigController(String portName, Pin pin) {
        this.portName = portName;
        this.currentPin = pin;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        textView.setText("PIN CONFIG : " + portName + " : " + currentPin.getName());
    }

    public String getPortName(){
        return portName;
    }

    public Pin getPinData(){
        return currentPin;
    }

    @Override
    public void saveData() {
        //Todo:- Need to implement save data
    }
}
