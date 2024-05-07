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
import com.board.config.boardconfiggui.data.outputmodels.pinconfig.PinConfigParam;
import com.board.config.boardconfiggui.data.repo.BoardResultsRepo;
import com.board.config.boardconfiggui.data.repo.InputConfigRepo;
import com.board.config.boardconfiggui.interfaces.BoardPageDataSaverInterface;
import com.board.config.boardconfiggui.ui.models.IpConfigModel;
import com.board.config.boardconfiggui.ui.models.SlaveDeviceConfigModel;
import com.board.config.boardconfiggui.ui.utils.IpPinConfigListCell;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.net.URL;
import java.util.*;

public class IpConfigController implements Initializable, BoardPageDataSaverInterface {

  private final String ipName;
  @FXML
  private OnOffButtonWidgetController onOffWidgetController;
  @FXML
  private GridPane gridpaneWidget;
  @FXML
  private ComboBox<IpPinConfig> sclChoiceBox;
  @FXML
  private ComboBox<IpPinConfig> sdaChoiceBox;
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

  private PinConfig pinConfig;

  private IpPinConfig sclIpConfigModel,
          sdaIpConfigModel;

  private final List<IpPinConfig> ipPinConfigs = new ArrayList<>();

  private final List<SlaveWidgetController> slaveWidgetControllers = new ArrayList<>();

  @FXML
  private void onSCLPinUpdate() {
    IpPinConfig ipPinConfig = sclChoiceBox.getValue();
    if (Objects.isNull(ipPinConfig)) {
      return;
    }
    ipConfigModel.setSclPort(ipPinConfig.getPortName());
    ipConfigModel.setSclPin(ipPinConfig.getPinName());
  }

  @FXML
  private void onSDAPinUpdate() {
    IpPinConfig ipPinConfig = sdaChoiceBox.getValue();
    if (Objects.isNull(ipPinConfig)) {
      return;
    }
    ipConfigModel.setSdaPort(ipPinConfig.getPortName());
    ipConfigModel.setSdaPin(ipPinConfig.getPinName());
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
      String prevSlaves = ipConfigModel.getNoOfSlaves();
      int prevSlavesCount = StringUtils.isNotBlank(prevSlaves) ? Integer.parseInt(prevSlaves) : 0;
      if (noOfSlaves > prevSlavesCount) {
        for (int i = 0; i < (noOfSlaves - prevSlavesCount); i++) {
          FXMLLoader loader = new FXMLLoader(getClass().getResource("slave-widget.fxml"));
          ipConfigVBox.getChildren().add(loader.load());
          SlaveWidgetController slaveWidgetController = loader.getController();
          slaveWidgetControllers.add(slaveWidgetController);
        }
      } else if (noOfSlaves < prevSlavesCount) {
        for (int i = 0; i < (prevSlavesCount - noOfSlaves); i++) {
          ipConfigVBox.getChildren().removeLast();
          slaveWidgetControllers.removeLast();
        }
      }
      slavesInfoLabel.setVisible(noOfSlaves > 0);
      ipConfigModel.setNoOfSlaves(String.valueOf(noOfSlaves));
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
      onOffWidgetController.setButtonLabel("Enable Status:");
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
      // Set how to display custom objects in the ComboBox
      sclChoiceBox.setCellFactory(param -> new IpPinConfigListCell());
      sclChoiceBox.setButtonCell(new IpPinConfigListCell());
      sdaChoiceBox.setCellFactory(param -> new IpPinConfigListCell());
      sdaChoiceBox.setButtonCell(new IpPinConfigListCell());
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  private void initializeData() {
    try {
      InputConfigRepo inputConfigRepo = InputConfigRepo.getInstance();
      List<Port> ports = inputConfigRepo.getPinConfig().getPorts();

      boardResultsRepo = BoardResultsRepo.getInstance();
      pinConfig = boardResultsRepo.getBoardResult().getPinConfig();

      Map<String, List<String>> bypassDisabledPins = pinConfig.getBypassDisabledPins();

      for (Port port : ports) {
        List<String> byPassDisabledPortPins = Objects.isNull(bypassDisabledPins.get(port.getName()))
                ? new ArrayList<>() : bypassDisabledPins.get(port.getName());
        for (Pin pin : port.getPinList()) {
          if (pin.getValues().contains(getSCLParam()) || pin.getValues().contains(getSDAParam())) {
            IpPinConfig ipPinConfig = new IpPinConfig(port.getName(), pin.getName());
            ipPinConfig.setClock(pin.getValues().contains(getSCLParam()));
            ipPinConfig.setDisabled(CollectionUtils.containsAny(byPassDisabledPortPins, pin.getName()));
            ipPinConfigs.add(ipPinConfig);
          }
        }
      }
      sdaChoiceBox.setItems(FXCollections.observableList(getDataPins()));
      sclChoiceBox.setItems(FXCollections.observableList(getClockPins()));

      prefillData(boardResultsRepo.getBoardResult());

    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  private List<IpPinConfig> getClockPins() {
    return ipPinConfigs.stream()
            .filter(IpPinConfig::isClock)
            .toList();
  }

  private List<IpPinConfig> getDataPins() {
    return ipPinConfigs.stream()
            .filter(ipPinConfig -> !ipPinConfig.isClock())
            .toList();
  }

  private void prefillData(BoardResult boardResult) {
    if (Objects.isNull(boardResult.getIpConfig())) {
      return;
    }
    IpConfigIp ipConfigIp = boardResult.getIpConfig().getIpConfig(ipName);
    if (Objects.isNull(ipConfigIp)) {
      return;
    }

    if(CollectionUtils.isNotEmpty(ipConfigIp.getPorts()))
      for (IpConfigPort ipConfigPort: ipConfigIp.getPorts()) {
        String portName = ipConfigPort.getName();
        for (SignalParam signalParam : ipConfigPort.getSignalParams()) {
          IpPinConfig ipPinConfig = new IpPinConfig(portName, signalParam.getPin());
          if (StringUtils.equals(signalParam.getName(), getSDAParam())) {
            ipConfigModel.setSdaPin(signalParam.getPin());
            ipConfigModel.setSdaPort(portName);
            sdaIpConfigModel = ipPinConfig;
            sdaChoiceBox.valueProperty().setValue(ipPinConfig);
          } else if (StringUtils.equals(signalParam.getName(), getSCLParam())) {
            ipConfigModel.setSclPin(signalParam.getPin());
            ipConfigModel.setSclPort(portName);
            sclIpConfigModel = ipPinConfig;
            sclChoiceBox.valueProperty().setValue(ipPinConfig);
          }
        }
      }

    if(CollectionUtils.isNotEmpty(ipConfigIp.getParams()))
      for (Param param : ipConfigIp.getParams()) {
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

    DeviceDescriptor deviceDescriptor = ipConfigIp.getDeviceDescriptor();
    if (Objects.nonNull(deviceDescriptor)) {
      List<DeviceConfiguration> deviceConfigurations = deviceDescriptor.getDeviceConfigurations();
      noOfSlavesField.getValueFactory().setValue(deviceConfigurations.size());
      onNoOfSlavesUpdate();
      for (int i = 0; i < deviceConfigurations.size(); i++) {
        slaveWidgetControllers.get(i).setDeviceConfiguration(deviceConfigurations.get(i));
      }
    }
    onOffWidgetController.setButtonTextColor(Color.valueOf("#008000"));
    onOffWidgetController.setButtonText("ON");
    gridpaneWidget.setVisible(true);
    ipConfigVBox.setVisible(true);
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

      prepareIpPinConfigData();

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

      IpConfigIp ipConfigIp = new IpConfigIp(ipName);
      ipConfigIp.setPorts(ipConfigPortsList);
      ipConfigIp.setParams(List.of(sysClock, i2cFreq, sdrFreq));

      if (CollectionUtils.isNotEmpty(slaveWidgetControllers)) {
        DeviceDescriptor deviceDescriptor = new DeviceDescriptor();
        List<DeviceConfiguration> deviceConfigurations = new ArrayList<>();
        for (SlaveWidgetController slaveWidgetController: slaveWidgetControllers) {
          deviceConfigurations.add(slaveWidgetController.getDeviceConfiguration());
        }
        deviceDescriptor.setDeviceConfigurations(deviceConfigurations);
        ipConfigIp.setDeviceDescriptor(deviceDescriptor);
      }
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
    sdaChoiceBox.setValue(null);
    sdaChoiceBox.setPromptText(Constants.SELECT);
    sclChoiceBox.setValue(null);
    sclChoiceBox.setPromptText(Constants.SELECT);
    sysClockField.textProperty().setValue(null);
    i2cFreqField.textProperty().setValue(null);
    sdrFreqField.textProperty().setValue(null);
    noOfSlavesField.getValueFactory().setValue(0);
    ipConfigVBox.getChildren().clear();
    slaveWidgetControllers.clear();
    onNoOfSlavesUpdate();
  }

  private void removeFromRepo() {
    IpConfig ipConfig = boardResultsRepo.getBoardResult().getIpConfig();
    if (Objects.nonNull(ipConfig)) {
      boardResultsRepo.getBoardResult().getIpConfig().removeIpConfig(ipName);
      if (Objects.nonNull(sclIpConfigModel)) {
        pinConfig.removePinConfig(sclIpConfigModel.getPortName(), sclIpConfigModel.getPinName());
      }
      if (Objects.nonNull(sdaIpConfigModel)) {
        pinConfig.removePinConfig(sdaIpConfigModel.getPortName(), sdaIpConfigModel.getPinName());
      }
    }
  }

  /**
   * Prepares and manages IP PIN configurations based on the provided IP configuration model.
   * If a PIN configuration does not exist, it creates and saves it. If an existing PIN
   * configuration has a different PIN, it updates the configuration.
   */
  private void prepareIpPinConfigData() {
    // Get the SCL port and PIN from the IP configuration model
    String sclPortName = ipConfigModel.getSclPort();
    String sclPinName = ipConfigModel.getSclPin();

    if (StringUtils.isNotEmpty(sclPortName) && StringUtils.isNotEmpty(sclPinName)) {

      if (Objects.nonNull(sclIpConfigModel) &&
              (!StringUtils.equals(sclIpConfigModel.getPortName(), sclPortName)
                      || !StringUtils.equals(sclIpConfigModel.getPinName(), sclPinName))) {
        // remove the pin configuration of the existing port.
        pinConfig.removePinConfig(sclIpConfigModel.getPortName(), sclIpConfigModel.getPinName());
      }

      // If no existing configuration, create and save a new one
      prepareAndSaveIpPinConfig(sclPortName, sclPinName, getSCLParam());
    }

    // Get the SDA port and PIN from the IP configuration model
    String sdaPortName = ipConfigModel.getSdaPort();
    String sdaPinName = ipConfigModel.getSdaPin();
    if (StringUtils.isNotEmpty(sdaPortName) && StringUtils.isNotEmpty(sdaPinName)) {

      if (Objects.nonNull(sdaIpConfigModel) &&
              (!StringUtils.equals(sdaIpConfigModel.getPortName(), sdaPortName) ||
                      !StringUtils.equals(sdaIpConfigModel.getPinName(), sdaPinName))) {
        // remove the pin configuration of the existing port.
        pinConfig.removePinConfig(sdaIpConfigModel.getPortName(), sdaIpConfigModel.getPinName());
      }

      // If no existing configuration, create and save a new one
      prepareAndSaveIpPinConfig(sdaPortName, sdaPinName, getSDAParam());

    }
  }

  /**
   * Prepares and saves an IP PIN configuration.
   *
   * @param portNumber   The port number associated with the IP PIN configuration.
   * @param pinNumber    The pinNumber for the IP PIN configuration.
   * @param pinParamValue The value of the IP PIN parameter (e.g., SDA, SCL).
   */
  private void prepareAndSaveIpPinConfig(String portNumber, String pinNumber, String pinParamValue) {
    // Create a PinConfigParam object using the IP pin from ipConfigModel
    PinConfigParam pinConfigParam = new PinConfigParam(pinNumber);
    // Set bypass mode to true (if applicable)
    pinConfigParam.setByPassMode(true);
    // Set pinParamValue to pinConfigParam
    pinConfigParam.setValue(pinParamValue);
    // Save the IP PIN configuration
    pinConfig.savePinConfig(portNumber, pinNumber, pinConfigParam);
  }

}
