package com.board.config.boardconfiggui;

import com.board.config.boardconfiggui.controllers.SlaveWidgetController;
import com.board.config.boardconfiggui.data.Constants;
import com.board.config.boardconfiggui.data.enums.DeviceRole;
import com.board.config.boardconfiggui.data.inputmodels.ipconfig.Instance;
import com.board.config.boardconfiggui.data.inputmodels.ipconfig.Ip;
import com.board.config.boardconfiggui.data.inputmodels.ipconfig.IpConfig;
import com.board.config.boardconfiggui.data.inputmodels.pinconfig.Pin;
import com.board.config.boardconfiggui.data.inputmodels.pinconfig.Port;
import com.board.config.boardconfiggui.data.outputmodels.BoardResult;
import com.board.config.boardconfiggui.data.outputmodels.Param;
import com.board.config.boardconfiggui.data.outputmodels.ipconfig.*;
import com.board.config.boardconfiggui.data.outputmodels.pinconfig.PinConfigParam;
import com.board.config.boardconfiggui.data.outputmodels.pinconfig.PinConfigPort;
import com.board.config.boardconfiggui.data.repo.BoardResultsRepo;
import com.board.config.boardconfiggui.data.repo.InputConfigRepo;
import com.board.config.boardconfiggui.interfaces.BoardPageDataSaverInterface;
import com.board.config.boardconfiggui.ui.models.IpConfigModel;
import com.board.config.boardconfiggui.ui.models.SlaveDeviceConfigModel;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;

import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.net.URL;
import java.util.*;

public class IpConfigController implements Initializable, BoardPageDataSaverInterface {

  private String ipName;
  @FXML
  private ComboBox<String> sclChoiceBox;
  @FXML
  private ComboBox<String> sdaChoiceBox;
  @FXML
  private TextField sysClockField;
  @FXML
  private TextField i2cFreqField;
  @FXML
  private TextField sdrFreqField;
  @FXML
  private TextField noOfSlavesField;
  @FXML
  private Label noOfSlavesLabel;

  @FXML
  private Label slavesInfoLabel;
  @FXML
  private VBox ipConfigVBox;

  @FXML
  private IpConfigModel ipConfigModel;

  private List<String> slkPinsList = new ArrayList<>();
  private List<String> sdaPinsList = new ArrayList<>();

  private List<SlaveWidgetController> slaveWidgetControllers = new ArrayList<>();

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
      slavesInfoLabel.setVisible(true);
      ipConfigVBox.getChildren().clear();
      int count = Integer.parseInt(noOfSlaves);
      for (int i = 0; i < count; i++) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("slave-widget.fxml"));
        SlaveWidgetController slaveWidgetController = loader.getController();
        slaveWidgetControllers.add(slaveWidgetController);
        ipConfigVBox.getChildren().add(loader.load());
      }
    } else {
      slavesInfoLabel.setVisible(false);
      ipConfigVBox.getChildren().clear();
      slaveWidgetControllers.clear();
    }
  }

    public IpConfigController(String ipName) {
        this.ipName = ipName;
    }


  @Override
  public void initialize(URL url, ResourceBundle resourceBundle) {
    ipConfigModel = new IpConfigModel();
    updateIpType();
    initializeData();
  }


  private void initializeData() {
    try {
      InputConfigRepo inputConfigRepo = InputConfigRepo.getInstance();
      List<Port> ports = inputConfigRepo.getPinConfig().getPorts();

      List<String> sdaAndsclPorts = new ArrayList<>();
      List<String> sdaAndsclPins = new ArrayList<>();

      for (Port port : ports) {
        for (Pin pin : port.getPinList()) {
          if (pin.getValues().contains(getSDAParam())) {
            sdaAndsclPins.add(pin.getName());
            sdaAndsclPorts.add(port.getName());
            sdaPinsList.add(port.getName() + " Pin: " + pin.getName());
            sdaChoiceBox.setItems(FXCollections.observableList(sdaPinsList));
          } else if (pin.getValues().contains(getSCLParam())) {
            sdaAndsclPins.add(pin.getName());
            sdaAndsclPorts.add(port.getName());
            slkPinsList.add(port.getName() + " Pin: " + pin.getName());
            sclChoiceBox.setItems(FXCollections.observableList(slkPinsList));
          }
        }
      }

      BoardResultsRepo boardResultsRepo = BoardResultsRepo.getInstance();
      prefillData(boardResultsRepo.getBoardResult());

//    List<PinConfigPort> pinConfigPorts = boardResultsRepo.getBoardResult().getPinConfig().getPorts();
//    for(PinConfigPort pinConfigPort : pinConfigPorts){
//      if (sdaAndsclPorts.contains(pinConfigPort.getName())) {
//        for (PinConfigParam pinConfigParam : pinConfigPort.getConfigParams()) {
//          if (sdaAndsclPins.contains(pinConfigParam.getPin())) {
//            pinConfigParam.getPin();
//          }
//        }
//      }
//    }

    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  private void prefillData(BoardResult boardResult) {
    if (boardResult.getIpConfig() == null) {
      return;
    }
    for (IpConfigIp ip : boardResult.getIpConfig().getIps()) {
      if (ip.getName().equals(ipName)) {
        for (IpConfigPort ipConfigPort: ip.getPorts()) {
          String portName = ipConfigPort.getName();
          for (SignalParam SignalParam : ipConfigPort.getSignalParams()) {
            if (Objects.equals(SignalParam.getName(), getSDAParam())) {
              ipConfigModel.setSdaPin(portName + " Pin: " + SignalParam.getPin());
            } else if (Objects.equals(SignalParam.getName(), getSCLParam())) {
              ipConfigModel.setSclPin(portName + " Pin: " + SignalParam.getPin());
            }
          }
        }

        for (Param param : ip.getParams()) {
          if (Objects.equals(param.getName(), Constants.SYS_CLK_PARAM)) {
            ipConfigModel.setSysClock(param.getValue());
          } else if (Objects.equals(param.getName(), Constants.I2C_FREQ_PARAM)) {
            ipConfigModel.setI2cFreq(param.getValue());
          } else if (Objects.equals(param.getName(), Constants.SDR_FREQ_PARAM)) {
            ipConfigModel.setSdrFreq(param.getValue());
          }
        }

        DeviceDescriptor deviceDescriptor = ip.getDeviceDescriptor();
        List<DeviceConfiguration> deviceConfigurations = deviceDescriptor.getDeviceConfigurations();
        noOfSlavesField.setText(String.valueOf(deviceConfigurations.size()));
        for (int i = 0; i < deviceConfigurations.size(); i++) {
          slaveWidgetControllers.get(i).setDeviceConfiguration(deviceConfigurations.get(i));
        }
      }
    }
  }

  private String getSDAParam() {
    return ipName + "_SDA";
  }

  private String getSCLParam() {
    return ipName + "_SCL";
  }

    @Override
    public void saveData() {
      BoardResultsRepo boardResultsRepo = BoardResultsRepo.getInstance();
      BoardResult boardResult = boardResultsRepo.getBoardResult();

      checkForEmptyIps(boardResult);

      IpConfigIp ipConfigIp = prepareIpConfigData();

      for (IpConfigIp ip : boardResult.getIpConfig().getIps()) {
        if (ip.getName().equals(ipName)) {
          int index = boardResult.getIpConfig().getIps().indexOf(ip);
          boardResult.getIpConfig().getIps().set(index, ipConfigIp);
          return;
        }
      }
      boardResult.getIpConfig().getIps().add(ipConfigIp);
    }

    private IpConfigIp prepareIpConfigData() {
      List<String> sdaPinData = List.of(ipConfigModel.getSdaPin().split(" Pin: "));
      SignalParam sdaParam = new SignalParam(sdaPinData.get(1), ipName + "_SDA");
      List<String> sclPinData = List.of(ipConfigModel.getSclPin().split(" Pin: "));
      SignalParam sclParam = new SignalParam(sclPinData.get(1), ipName + "_SCL");

      List<IpConfigPort> ipConfigPortsList = new ArrayList<>();
      if (sdaPinData.get(0) == sclPinData.get(0)) {
        IpConfigPort ipConfigPort = new IpConfigPort(sdaPinData.get(0));
        ipConfigPort.setSignalParams(List.of(sclParam, sdaParam));
        ipConfigPortsList.add(ipConfigPort);
      } else {
        IpConfigPort sdaConfigPort = new IpConfigPort(sdaPinData.get(0));
        sdaConfigPort.setSignalParams(List.of(sdaParam));
        IpConfigPort sclConfigPort = new IpConfigPort(sclPinData.get(0));
        sclConfigPort.setSignalParams(List.of(sclParam));
        ipConfigPortsList.add(sdaConfigPort);
        ipConfigPortsList.add(sclConfigPort);
      }

      Param sysClock = new Param(ipConfigModel.getSysClock(), Constants.SYS_CLK_PARAM);
      Param i2cFreq = new Param(ipConfigModel.getI2cFreq(), Constants.I2C_FREQ_PARAM);
      Param sdrFreq = new Param(ipConfigModel.getSdrFreq(), Constants.SDR_FREQ_PARAM);

      DeviceDescriptor deviceDescriptor = new DeviceDescriptor();
      List<DeviceConfiguration> deviceConfigurations = new ArrayList<>();
      for (SlaveWidgetController slaveWidgetController: slaveWidgetControllers) {
        deviceConfigurations.add(slaveWidgetController.getDeviceConfiguration());
      }
      deviceDescriptor.setDeviceConfigurations(deviceConfigurations);

      IpConfigIp ipConfigIp = new IpConfigIp(ipName);
      ipConfigIp.setPorts(ipConfigPortsList);
      ipConfigIp.setParams(List.of(sysClock, i2cFreq, sdrFreq));
      ipConfigIp.setDeviceDescriptor(deviceDescriptor);
      return ipConfigIp;
    }

    private void checkForEmptyIps(BoardResult boardResult) {
      com.board.config.boardconfiggui.data.outputmodels.ipconfig.IpConfig ipConfig = boardResult.getIpConfig();
      if (ipConfig.getIps().isEmpty()) {
        ipConfig.setIps(new ArrayList<>());
      }
    }

    private boolean checkIpAvailabilityInResult(BoardResult boardResult) {
      for (IpConfigIp ip : boardResult.getIpConfig().getIps()) {
        if (ip.getName().equals(ipName)) {
          return true;
        }
      }
      return false;
    }

    private void updateIpType() {
      InputConfigRepo inputConfigRepo = InputConfigRepo.getInstance();
      for (Ip ip : inputConfigRepo.getIpConfig().getIpList()) {
        for (Instance instance : ip.getInstanceList()) {
          if (instance.getName().equals(ipName)) {
            if (instance.getDeviceRole().equals(DeviceRole.MASTER) || instance.getDeviceRole().equals(DeviceRole.SECONDARY_MASTER)) {
              noOfSlavesField.setVisible(true);
              noOfSlavesLabel.setVisible(true);
            } else {
              noOfSlavesField.setVisible(false);
              noOfSlavesLabel.setVisible(false);
            }
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
    return value != null && !value.isEmpty() && !value.isBlank() && Integer.parseInt(value)!=0;
  }
}
