package com.board.config.boardconfiggui;

import com.board.config.boardconfiggui.controllers.SlaveWidgetController;
import com.board.config.boardconfiggui.data.Constants;
import com.board.config.boardconfiggui.data.enums.DeviceRole;
import com.board.config.boardconfiggui.data.inputmodels.ipconfig.Instance;
import com.board.config.boardconfiggui.data.inputmodels.ipconfig.Ip;
import com.board.config.boardconfiggui.data.inputmodels.pinconfig.Pin;
import com.board.config.boardconfiggui.data.inputmodels.pinconfig.Port;
import com.board.config.boardconfiggui.data.outputmodels.BoardResult;
import com.board.config.boardconfiggui.data.outputmodels.Param;
import com.board.config.boardconfiggui.data.outputmodels.ipconfig.*;
import com.board.config.boardconfiggui.data.repo.BoardResultsRepo;
import com.board.config.boardconfiggui.data.repo.InputConfigRepo;
import com.board.config.boardconfiggui.interfaces.BoardPageDataSaverInterface;
import com.board.config.boardconfiggui.ui.models.IpConfigModel;
import com.board.config.boardconfiggui.ui.models.SlaveDeviceConfigModel;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;

import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.util.Pair;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

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
  private TextArea disabledPinsTextArea;
  @FXML
  private IpConfigModel ipConfigModel;

  private List<Pair<String, String>> sclPinsList = new ArrayList<>();
  private List<Pair<String, String>> sdaPinsList = new ArrayList<>();

  private List<SlaveWidgetController> slaveWidgetControllers = new ArrayList<>();

  @FXML
  private void onSCLPinUpdate() {
    String sclPin = sclChoiceBox.getValue();
    Pair<String, String> sclPinPair = getPinPortPair(sclPin);
    ipConfigModel.setSclPort(sclPinPair.getKey());
    ipConfigModel.setSclPin(sclPinPair.getValue());
  }

  @FXML
  private void onSDAPinUpdate() {
    String sdaPin = sdaChoiceBox.getValue();
    Pair<String, String> sdaPinPair = getPinPortPair(sdaPin);
    ipConfigModel.setSdaPort(sdaPinPair.getKey());
    ipConfigModel.setSdaPin(sdaPinPair.getValue());
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
    try {
      String noOfSlaves = noOfSlavesField.getText();
      ipConfigModel.setNoOfSlaves(noOfSlaves);
      if (isNotEmpty(noOfSlaves)) {
        slavesInfoLabel.setVisible(true);
        ipConfigVBox.getChildren().clear();
        int count = Integer.parseInt(noOfSlaves);
        for (int i = 0; i < count; i++) {
          FXMLLoader loader = new FXMLLoader(getClass().getResource("slave-widget.fxml"));
          ipConfigVBox.getChildren().add(loader.load());
          SlaveWidgetController slaveWidgetController = loader.getController();
          slaveWidgetControllers.add(slaveWidgetController);
        }
      } else {
        slavesInfoLabel.setVisible(false);
        ipConfigVBox.getChildren().clear();
        slaveWidgetControllers.clear();
      }
    } catch (IOException e) {
      e.printStackTrace();
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
            sdaPinsList.add(new Pair<>(port.getName(), pin.getName()));
          } else if (pin.getValues().contains(getSCLParam())) {
            sclPinsList.add(new Pair<>(port.getName(), pin.getName()));
          }
        }
      }
      sdaChoiceBox.setItems(FXCollections.observableList(getPinDisplayValues(sdaPinsList)));
      sclChoiceBox.setItems(FXCollections.observableList(getPinDisplayValues(sclPinsList)));

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

  private List<String> getPinDisplayValues(List<Pair<String, String>> pinPortPairs) {
    List<String> displayValues = new ArrayList<>();
    for (Pair<String, String> pair: pinPortPairs) {
      displayValues.add(getPinDisplayValue(pair));
    }
    return displayValues;
  }

  private Pair<String, String> getPinPortPair(String data) {
    String[] strings = data.split(" Pin: ");
    return new Pair<>(strings[0], strings[1]);
  }

  private String getPinDisplayValue(Pair<String, String> pinPortPair) {
    return pinPortPair.getKey() + " Pin: " + pinPortPair.getValue();
  }

  private void prefillData(BoardResult boardResult) {
    if (boardResult.getIpConfig() == null) {
      return;
    }
    for (IpConfigIp ip : boardResult.getIpConfig().getIps()) {
      if (ip.getName().equals(ipName)) {
        for (IpConfigPort ipConfigPort: ip.getPorts()) {
          String portName = ipConfigPort.getName();
          for (SignalParam signalParam : ipConfigPort.getSignalParams()) {
            if (StringUtils.equals(signalParam.getName(), getSDAParam())) {
              ipConfigModel.setSdaPin(signalParam.getPin());
              ipConfigModel.setSdaPort(portName);
              String sdaPin = getPinDisplayValue(new Pair<>(portName, signalParam.getPin()));
              sdaChoiceBox.valueProperty().setValue(sdaPin);
            } else if (StringUtils.equals(signalParam.getName(), getSCLParam())) {
              ipConfigModel.setSclPin(signalParam.getPin());
              ipConfigModel.setSclPort(portName);
              String sclPin = getPinDisplayValue(new Pair<>(portName, signalParam.getPin()));
              sclChoiceBox.valueProperty().setValue(sclPin);
            }
          }
        }

        for (Param param : ip.getParams()) {
          if (StringUtils.equals(param.getName(), Constants.SYS_CLK_PARAM)) {
            ipConfigModel.setSysClock(param.getValue());
            sysClockField.setText(param.getValue());
          } else if (StringUtils.equals(param.getName(), Constants.I2C_FREQ_PARAM)) {
            ipConfigModel.setI2cFreq(param.getValue());
            i2cFreqField.setText(param.getValue());
          } else if (StringUtils.equals(param.getName(), Constants.SDR_FREQ_PARAM)) {
            ipConfigModel.setSdrFreq(param.getValue());
            sdrFreqField.setText(param.getValue());
          }
        }

        DeviceDescriptor deviceDescriptor = ip.getDeviceDescriptor();
        List<DeviceConfiguration> deviceConfigurations = deviceDescriptor.getDeviceConfigurations();
        noOfSlavesField.setText(String.valueOf(deviceConfigurations.size()));
        onNoOfSlavesUpdate();
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

      SignalParam sdaParam = new SignalParam(ipConfigModel.getSdaPin(), getSDAParam());
      SignalParam sclParam = new SignalParam(ipConfigModel.getSclPin(), getSCLParam());

      List<IpConfigPort> ipConfigPortsList = new ArrayList<>();
      if (StringUtils.isNotBlank(ipConfigModel.getSdaPort()) && StringUtils.equals(ipConfigModel.getSclPort(), ipConfigModel.getSdaPort())) {
        IpConfigPort ipConfigPort = new IpConfigPort(ipConfigModel.getSclPort());
        ipConfigPort.setSignalParams(List.of(sclParam, sdaParam));
        ipConfigPortsList.add(ipConfigPort);
      } else {
        if (StringUtils.isNotBlank(ipConfigModel.getSdaPort())) {
          IpConfigPort sdaConfigPort = new IpConfigPort(ipConfigModel.getSdaPort());
          sdaConfigPort.setSignalParams(List.of(sdaParam));
          ipConfigPortsList.add(sdaConfigPort);
        }
        if (StringUtils.isNotBlank(ipConfigModel.getSclPort())) {
          IpConfigPort sclConfigPort = new IpConfigPort(ipConfigModel.getSclPort());
          sclConfigPort.setSignalParams(List.of(sclParam));
          ipConfigPortsList.add(sclConfigPort);
        }
      }

      Param sysClock = new Param(Constants.SYS_CLK_PARAM, ipConfigModel.getSysClock());
      Param i2cFreq = new Param(Constants.I2C_FREQ_PARAM, ipConfigModel.getI2cFreq());
      Param sdrFreq = new Param(Constants.SDR_FREQ_PARAM, ipConfigModel.getSdrFreq());

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
      if (Objects.isNull(ipConfig)) {
        boardResult.setIpConfig(new IpConfig());
        boardResult.getIpConfig().setIps(new ArrayList<>());
        return;
      }
      if (CollectionUtils.isEmpty(ipConfig.getIps())) {
        ipConfig.setIps(new ArrayList<>());
      }
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
