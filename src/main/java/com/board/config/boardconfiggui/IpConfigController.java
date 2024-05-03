package com.board.config.boardconfiggui;

import com.board.config.boardconfiggui.controllers.OnOffButtonWidgetController;
import com.board.config.boardconfiggui.controllers.SlaveWidgetController;
import com.board.config.boardconfiggui.data.Constants;
import com.board.config.boardconfiggui.data.IpPinConfig;
import com.board.config.boardconfiggui.data.enums.DeviceRole;
import com.board.config.boardconfiggui.data.inputmodels.ipconfig.Instance;
import com.board.config.boardconfiggui.data.inputmodels.ipconfig.Ip;
import com.board.config.boardconfiggui.data.inputmodels.pinconfig.Pin;
import com.board.config.boardconfiggui.data.inputmodels.pinconfig.Port;
import com.board.config.boardconfiggui.data.outputmodels.BoardResult;
import com.board.config.boardconfiggui.data.outputmodels.Param;
import com.board.config.boardconfiggui.data.outputmodels.ipconfig.*;
import com.board.config.boardconfiggui.data.outputmodels.pinconfig.PinConfig;
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
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.util.Pair;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.net.URL;
import java.util.*;

public class IpConfigController implements Initializable, BoardPageDataSaverInterface {

  private String ipName;
  @FXML
  private OnOffButtonWidgetController onOffWidgetController;
  @FXML
  private GridPane gridpaneWidget;
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
  private Spinner<Integer> noOfSlavesField;
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

  private BoardResultsRepo boardResultsRepo;

  private final List<IpPinConfig> ipPinConfigs = new ArrayList<>();

  private final List<SlaveWidgetController> slaveWidgetControllers = new ArrayList<>();

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
      Integer noOfSlaves = noOfSlavesField.getValue();
      ipConfigModel.setNoOfSlaves(String.valueOf(noOfSlaves));
      if (noOfSlaves > 0) {
        slavesInfoLabel.setVisible(true);
        ipConfigVBox.getChildren().clear();
        for (int i = 0; i < noOfSlaves; i++) {
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
    try {
      ipConfigModel = new IpConfigModel();
      updateIpType();
      initializeData();
      onOffWidgetController.setButtonLabel("IP Status:");
      onOffWidgetController.getButton().setOnAction(actionEvent -> {
        if (StringUtils.equals(OnOffButtonWidgetController.OFF_TXT, ((Button) actionEvent.getSource()).getText())) {
          onOffWidgetController.setButtonTextColor(Color.valueOf("#008000"));
          onOffWidgetController.setButtonText("ON");
          gridpaneWidget.setVisible(true);
          ipConfigVBox.setVisible(true);
        } else {
          onOffWidgetController.setButtonTextColor(Color.valueOf("#ff0000"));
          onOffWidgetController.setButtonText("OFF");
          gridpaneWidget.setVisible(false);
          ipConfigVBox.setVisible(false);
          ipConfigModel = new IpConfigModel();
          clearUi();
        }

      });
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  private void initializeData() {
    try {
      InputConfigRepo inputConfigRepo = InputConfigRepo.getInstance();
      List<Port> ports = inputConfigRepo.getPinConfig().getPorts();

      boardResultsRepo = BoardResultsRepo.getInstance();
      PinConfig pinConfig = boardResultsRepo.getBoardResult().getPinConfig();

      Map<String, List<String>> bypassConfiguredPins = pinConfig.getBypassConfiguredPins();

      for (Port port : ports) {
        List<String> byPassSupportedPortPins = Objects.isNull(bypassConfiguredPins.get(port.getName()))
                ? new ArrayList<>() : bypassConfiguredPins.get(port.getName());
        for (Pin pin : port.getPinList()) {
          IpPinConfig ipPinConfig = new IpPinConfig(port.getName(), pin.getName());
          ipPinConfig.setClock(pin.getValues().contains(getSCLParam()));
          ipPinConfig.setEnabled(CollectionUtils.containsAny(byPassSupportedPortPins, pin.getName()));
          ipPinConfigs.add(ipPinConfig);
        }
      }
      sdaChoiceBox.setItems(FXCollections.observableList(getPinDataDisplayValues()));
      sclChoiceBox.setItems(FXCollections.observableList(getPinClockDisplayValues()));

      prefillData(boardResultsRepo.getBoardResult());
      setDisabledPins();

    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  private List<String> getPinClockDisplayValues() {
    return ipPinConfigs.stream()
            .filter(ipPinConfig -> ipPinConfig.isEnabled() && ipPinConfig.isClock())
            .map(IpPinConfig::getDisplayValue)
            .toList();
  }

  private List<String> getPinDataDisplayValues() {
    return ipPinConfigs.stream()
            .filter(ipPinConfig -> ipPinConfig.isEnabled() && !ipPinConfig.isClock())
            .map(IpPinConfig::getDisplayValue)
            .toList();
  }
  private List<String> getDisabledPinDisplayValues() {
    return ipPinConfigs.stream()
            .filter(ipPinConfig -> !ipPinConfig.isEnabled())
            .map(IpPinConfig::getDisplayValue)
            .toList();
  }

  private Pair<String, String> getPinPortPair(String data) {
    if (Objects.isNull(data)) {
      return new Pair<>(null, null);
    }
    String[] strings = data.split(" Pin: ");
    return new Pair<>(strings[0], strings[1]);
  }

  private String getPinDisplayValue(String portName, String pinName) {
    return ipPinConfigs.stream()
            .filter(ipPinConfig -> StringUtils.equals(portName, ipPinConfig.getPortName()) &&
                    StringUtils.equals(pinName, ipPinConfig.getPinName()))
            .map(IpPinConfig::getDisplayValue)
            .findFirst().orElse("");
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
              String sdaPin = getPinDisplayValue(portName, signalParam.getPin());
              sdaChoiceBox.valueProperty().setValue(sdaPin);
            } else if (StringUtils.equals(signalParam.getName(), getSCLParam())) {
              ipConfigModel.setSclPin(signalParam.getPin());
              ipConfigModel.setSclPort(portName);
              String sclPin = getPinDisplayValue(portName, signalParam.getPin());
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
        noOfSlavesField.getValueFactory().setValue(deviceConfigurations.size());
        onNoOfSlavesUpdate();
        for (int i = 0; i < deviceConfigurations.size(); i++) {
          slaveWidgetControllers.get(i).setDeviceConfiguration(deviceConfigurations.get(i));
        }
        onOffWidgetController.setButtonTextColor(Color.valueOf("#008000"));
        onOffWidgetController.setButtonText("ON");
        gridpaneWidget.setVisible(true);
        ipConfigVBox.setVisible(true);      }
    }
  }

  private void setDisabledPins() {
    List<String> displayValues = getDisabledPinDisplayValues();
    disabledPinsTextArea.setText(String.join(", ", displayValues));
  }

  private String getSDAParam() {
    return ipName + "_SDA";
  }

  private String getSCLParam() {
    return ipName + "_SCL";
  }

    @Override
    public void saveData() {
      if (StringUtils.equals(OnOffButtonWidgetController.OFF_TXT, onOffWidgetController.getButton().getText())) {
        removeFromRepo();
        return;
      }
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

  private void clearUi() {
    sdaChoiceBox.setItems(null);
    sdaChoiceBox.setPromptText("select");
    sdaChoiceBox.setItems(FXCollections.observableList(getPinDataDisplayValues()));
    sclChoiceBox.setItems(null);
    sdaChoiceBox.setPromptText("select");
    sclChoiceBox.setItems(FXCollections.observableList(getPinClockDisplayValues()));
    sysClockField.textProperty().setValue(null);
    i2cFreqField.textProperty().setValue(null);
    sdrFreqField.textProperty().setValue(null);
    noOfSlavesField.getValueFactory().setValue(0);
    onNoOfSlavesUpdate();
  }

  private void removeFromRepo() {
    IpConfig ipConfig = boardResultsRepo.getBoardResult().getIpConfig();
    if (Objects.nonNull(ipConfig)) {
      boardResultsRepo.getBoardResult().getIpConfig().removeIpConfig(ipName);
    }
  }
}
