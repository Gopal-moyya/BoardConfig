package com.board.config.boardconfiggui;

import com.board.config.boardconfiggui.controllers.OnOffButtonWidgetController;
import com.board.config.boardconfiggui.data.Constants;
import com.board.config.boardconfiggui.data.IpPinConfig;
import com.board.config.boardconfiggui.data.enums.ConfigParam;
import com.board.config.boardconfiggui.data.enums.ViewType;
import com.board.config.boardconfiggui.data.inputmodels.pinconfig.Pin;
import com.board.config.boardconfiggui.data.inputmodels.pinconfig.Port;
import com.board.config.boardconfiggui.data.outputmodels.BoardResult;
import com.board.config.boardconfiggui.data.outputmodels.ipconfig.*;
import com.board.config.boardconfiggui.data.outputmodels.pinconfig.PinConfig;
import com.board.config.boardconfiggui.data.outputmodels.pinconfig.PinConfigParam;
import com.board.config.boardconfiggui.data.repo.BoardResultsRepo;
import com.board.config.boardconfiggui.data.repo.InputConfigRepo;
import com.board.config.boardconfiggui.interfaces.BoardPageDataSaverInterface;
import com.board.config.boardconfiggui.ui.models.ConfigParamModel;
import com.board.config.boardconfiggui.ui.models.SPIConfigModel;
import com.board.config.boardconfiggui.ui.utils.IpPinConfigListCell;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import java.net.URL;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.board.config.boardconfiggui.data.Constants.NUMERIC_FIELD_ONLY;

public class SPIIpConfigController implements Initializable, BoardPageDataSaverInterface {
    private final String ipName;
    private List<SPIConfigModel> spiConfigModels;
    private Map<String, IpPinConfig> prefilledIpPinConfigs;
    private List<ConfigParamModel> configParamModels;

    @FXML
    private OnOffButtonWidgetController onOffWidgetController;
    @FXML
    private GridPane spiControlsGridPane;
    @FXML
    private GridPane configControlsGridPane;
    @FXML
    private Label configControlHeader;

    private BoardResultsRepo boardResultsRepo;

    private PinConfig pinConfig;

    public SPIIpConfigController(String ipName) {
        this.ipName = ipName;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initializeData();
        buildUI();
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

    private void initializeData() {
        initializeSpiConfigData();
        initializeOtherConfigData();
        prefillData(boardResultsRepo.getBoardResult());
    }

    private void buildUI() {
        buildEnableStatus();
        buildSpiConfigGridData();
        buildOtherConfigGridData();
    }

    private void initializeSpiConfigData() {
        InputConfigRepo inputConfigRepo = InputConfigRepo.getInstance();
        List<Port> ports = inputConfigRepo.getPinConfig().getPorts();

        boardResultsRepo = BoardResultsRepo.getInstance();
        pinConfig = boardResultsRepo.getBoardResult().getPinConfig();

        Map<String, List<String>> bypassDisabledPins = pinConfig.getBypassDisabledPins();
        Map<String, List<String>> pinsUsedByOtherIps = pinConfig.getPinsUsedByOtherIps(ipName);

        Map<String, List<IpPinConfig>> spiConfigMap = new HashMap<>();
        spiConfigModels = new ArrayList<>();
        prefilledIpPinConfigs = new HashMap<>();

        // Pattern to match SPI IP param from the pin values
        Pattern pattern = Pattern.compile("("+ ipName + "\\w+)");

        for (Port port : ports) {
            List<String> byPassDisabledPortPins = Objects.isNull(bypassDisabledPins.get(port.getName()))
                    ? new ArrayList<>() : bypassDisabledPins.get(port.getName());
            List<String> pinsUsedByOtherIpsPortPins = Objects.isNull(pinsUsedByOtherIps.get(port.getName()))
                    ? new ArrayList<>() : pinsUsedByOtherIps.get(port.getName());
            for (Pin pin : port.getPinList()) {
                Matcher matcher = pattern.matcher(pin.getValues());
                if (matcher.find()) {
                    String matchedString = matcher.group(1);
                    List<IpPinConfig> ipPinConfigs = spiConfigMap.computeIfAbsent(matchedString, k -> new ArrayList<>());

                    IpPinConfig ipPinConfig = new IpPinConfig(port.getName(), pin.getName());
                    ipPinConfig.setDisabled(CollectionUtils.containsAny(byPassDisabledPortPins, pin.getName()) ||
                          CollectionUtils.containsAny(pinsUsedByOtherIpsPortPins, pin.getName()));
                    ipPinConfigs.add(ipPinConfig);
                }
            }
        }

        if (!spiConfigMap.isEmpty()) {
            spiConfigMap.forEach((key, value) -> {
                spiConfigModels.add(new SPIConfigModel(key, value, null));
            });
        }
    }

    private void initializeOtherConfigData() {
        List<ConfigParam> writeCompletionConfigParams = Constants.QSPI_WRITE_COMPLETION_CONFIG_CONTROLS;
        configParamModels = new ArrayList<>();

        writeCompletionConfigParams.forEach(param -> {
            configParamModels.add(new ConfigParamModel(param, ""));
        });

        configControlHeader.setText(Constants.WRITE_COMPLETION_CONFIG_HEADER + ": ");
    }

    private void prefillData(BoardResult boardResult) {
        if (Objects.isNull(boardResult.getIpConfig())) {
            return;
        }
        IpConfigIp ipConfigIp = boardResult.getIpConfig().getIpConfig(ipName);
        if (Objects.isNull(ipConfigIp)) {
            return;
        }

        for (IpConfigPort ipConfigPort: ipConfigIp.getPorts()) {
            String portName = ipConfigPort.getName();
            for (SignalParam signalParam : ipConfigPort.getSignalParams()) {
                IpPinConfig ipPinConfig = new IpPinConfig(portName, signalParam.getPin());
                SPIConfigModel spiConfigModel = spiConfigModels.stream()
                        .filter(configModel -> StringUtils.equals(configModel.getLabel(), signalParam.getName()))
                        .findFirst().orElse(null);

                if (Objects.nonNull(spiConfigModel)) {
                    spiConfigModel.setResult(ipPinConfig);
                    prefilledIpPinConfigs.putIfAbsent(spiConfigModel.getLabel(), ipPinConfig);
                }
            }
        }

        if (ipConfigIp.getWriteCompletionConfig() == null ||
                CollectionUtils.isEmpty(ipConfigIp.getWriteCompletionConfig().getConfigParams())) {
            return;
        }

        for (WriteCompletionConfigParam param : ipConfigIp.getWriteCompletionConfig().getConfigParams()) {
            configParamModels.stream()
                    .filter(configParamModel -> StringUtils.equals(configParamModel.getConfigParam().getParam(), param.getName()))
                    .findFirst().ifPresent(configParamModel -> configParamModel.setResult(param.getValue()));
        }

        enableIP();
    }

    private void buildEnableStatus() {
        onOffWidgetController.setButtonLabel("Enable Status:");
        onOffWidgetController.getButton().setOnAction(actionEvent -> {
            if (StringUtils.equals(OnOffButtonWidgetController.OFF_TXT, ((Button) actionEvent.getSource()).getText())) {
                enableIP();
            } else {
                disableIP();
                clearUI();
            }
        });
    }

    private void enableIP() {
        onOffWidgetController.setButtonTextColor(Color.valueOf("#008000"));
        onOffWidgetController.setButtonText("ON");
        spiControlsGridPane.setVisible(true);
        configControlsGridPane.setVisible(true);
        configControlHeader.setVisible(true);
    }

    private void disableIP() {
        onOffWidgetController.setButtonTextColor(Color.valueOf("#ff0000"));
        onOffWidgetController.setButtonText("OFF");
        spiControlsGridPane.setVisible(false);
        configControlsGridPane.setVisible(false);
        configControlHeader.setVisible(false);
    }

    private void buildSpiConfigGridData() {
        int col = 0;
        int row = 0;
        for (SPIConfigModel spiConfigModel : spiConfigModels) {
            String label = spiConfigModel.getLabel();

            Label labelControl = new Label(label);
            labelControl.setAlignment(Pos.BASELINE_CENTER);
            spiControlsGridPane.add(labelControl, col, row);

            ComboBox<IpPinConfig> comboBox = new ComboBox<>();
            comboBox.setItems(FXCollections.observableArrayList(spiConfigModel.getIpPinConfigs()));
            comboBox.setCellFactory(param -> new IpPinConfigListCell());
            comboBox.setButtonCell(new IpPinConfigListCell());
            comboBox.setPrefWidth(280);
            comboBox.setPromptText(spiConfigModel.getResult() != null ?
                    spiConfigModel.getResult().getDisplayValue() : Constants.SELECT);
            spiControlsGridPane.add(comboBox, col + 1, row);

            comboBox.setOnAction(actionEvent -> {
                spiConfigModel.setResult(comboBox.getSelectionModel().getSelectedItem());
            });

            col += 2;
            if (col >= 4) {
                col = 0;
                row++;
            }
        }
    }

    private void buildOtherConfigGridData() {
        int col = 0;
        int row = 0;
        for (ConfigParamModel configParamModel : configParamModels) {
            String label = configParamModel.getConfigParam().getDisplayValue();

            Label labelControl = new Label(label);
            configControlsGridPane.add(labelControl, col, row);

            if (configParamModel.getConfigParam().getViewType().equals(ViewType.TEXT_FIELD)) {
                TextField textField = new TextField();
                textField.setPromptText(NUMERIC_FIELD_ONLY);
                textField.setText(configParamModel.getResult());
                configControlsGridPane.add(textField, col + 1, row);
                // Store user-entered value into result field
                textField.textProperty().addListener((observable, oldValue, newValue) -> {
                    if (newValue.matches("\\d*")) {
                        configParamModel.setResult(newValue);
                    } else {
                        textField.setText(oldValue);
                    }
                });
            } else if (configParamModel.getConfigParam().getViewType().equals(ViewType.SPINNER)) {
                Spinner<Integer> spinner = new Spinner<>();

                SpinnerValueFactory<Integer> valueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 1, 0);
                spinner.setValueFactory(valueFactory);
                spinner.setPrefWidth(280);

                configControlsGridPane.add(spinner, col + 1, row);
                configParamModel.setResult(Constants.ZERO);
                spinner.valueProperty().addListener((observable, oldValue, newValue) -> {
                    configParamModel.setResult(newValue.toString());
                });
            }

            col += 2;
            if (col >= 4) {
                col = 0;
                row++;
            }
        }
    }

    private void clearUI() {
        spiConfigModels.forEach(spiConfigModel -> {
            spiConfigModel.setResult(null);
        });
        configParamModels.forEach(configParamModel -> {
            configParamModel.setResult(null);
        });
        spiControlsGridPane.getChildren().clear();
        configControlsGridPane.getChildren().clear();
        buildSpiConfigGridData();
        buildOtherConfigGridData();
    }

    private void removeFromRepo() {
        IpConfig ipConfig = boardResultsRepo.getBoardResult().getIpConfig();
        if (Objects.nonNull(ipConfig)) {
            boardResultsRepo.getBoardResult().getIpConfig().removeIpConfig(ipName);
            spiConfigModels.forEach(spiConfigModel -> {
                IpPinConfig ipPinConfig = spiConfigModel.getResult();
                if (Objects.nonNull(ipPinConfig)) {
                    pinConfig.removePinConfig(ipPinConfig.getPortName(), ipPinConfig.getPinName());
                }
            });
        }
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

    private IpConfigIp prepareIpConfigData() {
        IpConfigIp ipConfigIp = new IpConfigIp(ipName);
        Map<String, IpConfigPort> ipConfigPortMap = new HashMap<>();

        spiConfigModels.forEach(spiConfigModel -> {
            if (spiConfigModel.getResult() != null) {
                IpPinConfig ipPinConfig = spiConfigModel.getResult();
                String portName = ipPinConfig.getPortName();
                IpConfigPort ipConfigPort = ipConfigPortMap.computeIfAbsent(portName, k -> new IpConfigPort(portName));

                SignalParam signalParam = new SignalParam(ipPinConfig.getPinName(), spiConfigModel.getLabel());
                ipConfigPort.addSignalParam(signalParam);
            }
        });

        if (!ipConfigPortMap.isEmpty()) {
            ipConfigIp.setPorts(ipConfigPortMap.values().stream().sorted(Comparator.comparing(IpConfigPort::getName)).toList());
        }

        List<WriteCompletionConfigParam> writeCompletionConfigParams = new ArrayList<>();
        configParamModels.forEach(configParamModel -> {
            writeCompletionConfigParams.add(
                    new WriteCompletionConfigParam(
                            configParamModel.getConfigParam().getParam(),
                            configParamModel.getResult()
                    )
            );
        });

        if (CollectionUtils.isNotEmpty(writeCompletionConfigParams)) {
            WriteCompletionConfig writeCompletionConfig = new WriteCompletionConfig();
            writeCompletionConfig.setConfigParams(writeCompletionConfigParams);
            ipConfigIp.setWriteCompletionConfig(writeCompletionConfig);
        }

        return ipConfigIp;
    }

    private void prepareIpPinConfigData() {
        spiConfigModels.forEach(spiConfigModel -> {
            IpPinConfig ipPinConfig = spiConfigModel.getResult();
            if (Objects.nonNull(ipPinConfig)) {
                IpPinConfig prefilledIpPinConfig = prefilledIpPinConfigs.get(spiConfigModel.getLabel());
                if (prefilledIpPinConfig != null && ipPinConfig != prefilledIpPinConfig) {
                    pinConfig.removePinConfig(prefilledIpPinConfig.getPortName(), prefilledIpPinConfig.getPinName());
                }
                prepareAndSaveIpPinConfig(ipPinConfig.getPortName(), ipPinConfig.getPinName(), spiConfigModel.getLabel());
            }
        });
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
