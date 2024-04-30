package com.board.config.boardconfiggui;

import com.board.config.boardconfiggui.ui.models.IpConfigModel;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class IpConfigController implements Initializable {

  @FXML
  private ChoiceBox<String> sclChoiceBox;
  @FXML
  private ChoiceBox<String> sdaChoiceBox;
  @FXML
  private TextField sysClockField;
  @FXML
  private TextField i2cFreqField;
  @FXML
  private TextField sdrFreqField;
  @FXML
  private TextField noOfSlavesField;

  private IpConfigModel ipConfigModel;

  @FXML
  private void onSCLPinUpdate() {
    String sclPin = sclChoiceBox.getValue();
    ipConfigModel.setSclPin(sclPin);
  }

  @FXML
  private void onSDAPinUpdate() {
    String sdaPin = sdaChoiceBox.getValue();
    ipConfigModel.setSdaPin(sdaPin);
  }

  @FXML
  private void onI2CFreqUpdate() {
    String i2cFreq = i2cFreqField.getText();
    ipConfigModel.setI2cFreq(i2cFreq);
  }

  @FXML
  private void onSDSRFreqUpdate() {
    String sdrFreq = sdrFreqField.getText();
    ipConfigModel.setSdrFreq(sdrFreq);
  }

  @FXML
  private void onSysClockUpdate() {
    String sysClock = sysClockField.getText();
    ipConfigModel.setSysClock(sysClock);
  }

  @FXML
  private void onNoOfSlavesUpdate() {
    String noOfSlaves = noOfSlavesField.getText();
    ipConfigModel.setNoOfSlaves(noOfSlaves);
  }

  @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
      ipConfigModel = new IpConfigModel();
    }


  private boolean validate() {
    return isNotEmpty(ipConfigModel.getSclPin()) &&
      isNotEmpty(ipConfigModel.getSdaPin()) &&
      isNotEmpty(ipConfigModel.getI2cFreq()) &&
      isNotEmpty(ipConfigModel.getSysClock()) &&
      isNotEmpty(ipConfigModel.getSdrFreq()) &&
      validateSlaves();
  }

  private boolean validateSlaves() {
//    for (SlaveDeviceConfigModel slaveDeviceConfigModel : ipConfigModel.getSlaveDeviceConfigModelList()) {
//      if (!slaveDeviceConfigModel.validate()) {
//        return false;
//      }
//    }
    return true;
  }

  private boolean isNotEmpty(String value) {
    return value != null && !value.isEmpty() && !value.isBlank();
  }
}
