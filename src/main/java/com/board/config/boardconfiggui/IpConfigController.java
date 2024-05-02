package com.board.config.boardconfiggui;


import com.board.config.boardconfiggui.data.inputmodels.pinconfig.Pin;
import com.board.config.boardconfiggui.data.inputmodels.pinconfig.Port;
import com.board.config.boardconfiggui.data.repo.InputConfigRepo;
import com.board.config.boardconfiggui.ui.models.IpConfigModel;
import com.board.config.boardconfiggui.ui.models.SlaveDeviceConfigModel;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;

import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.net.URL;
import java.util.*;

public class IpConfigController implements Initializable {

  private String ipName;
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

  @FXML
  private VBox ipConfigVBox;

  private IpConfigModel ipConfigModel;

  private List<String> slkPinsList = new ArrayList<>();
  private List<String> sdaPinsList = new ArrayList<>();

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
  private void onNoOfSlavesUpdate() throws IOException {
    String noOfSlaves = noOfSlavesField.getText();
    ipConfigModel.setNoOfSlaves(noOfSlaves);
    if (isNotEmpty(noOfSlaves)) {
      ipConfigVBox.getChildren().clear();
      int count = Integer.parseInt(noOfSlaves);
      for (int i = 0; i < count; i++) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("slave-widget.fxml"));
        ipConfigVBox.getChildren().add(loader.load());
      }
    } else {
      ipConfigVBox.getChildren().clear();
    }
  }

    public IpConfigController(String ipName) {
        this.ipName = ipName;
    }


  @Override
  public void initialize(URL url, ResourceBundle resourceBundle) {
    ipConfigModel = new IpConfigModel();
    initializeData();
  }

  private void initializeData() {
    InputConfigRepo inputConfigRepo = InputConfigRepo.getInstance();
    List<Port> ports = inputConfigRepo.getPinConfig().getPorts();

    for(Port port : ports){
      for(Pin pin : port.getPinList()) {
        if (pin.getValues().contains(ipName + "_SDA")) {
          sdaPinsList.add(port.getName() + pin.getName());
        } else if (pin.getValues().contains(ipName + "_SCL")) {
          slkPinsList.add(port.getName() + pin.getName());
        }
      }
    }
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
    for (SlaveDeviceConfigModel slaveDeviceConfigModel : ipConfigModel.getSlaveDeviceConfigModelList()) {
      if (slaveDeviceConfigModel.hasNullFields()) {
        return false;
      }
    }
    return true;
  }

  private boolean isNotEmpty(String value) {
    return value != null && !value.isEmpty() && !value.isBlank();
  }
}
