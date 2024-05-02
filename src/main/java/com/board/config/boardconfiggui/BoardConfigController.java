package com.board.config.boardconfiggui;

import com.board.config.boardconfiggui.common.Utils;
import com.board.config.boardconfiggui.data.Constants;
import com.board.config.boardconfiggui.data.inputmodels.ipconfig.Instance;
import com.board.config.boardconfiggui.data.inputmodels.ipconfig.Ip;
import com.board.config.boardconfiggui.data.inputmodels.pinconfig.Pin;
import com.board.config.boardconfiggui.data.inputmodels.pinconfig.Port;
import com.board.config.boardconfiggui.data.outputmodels.BoardResult;
import com.board.config.boardconfiggui.data.outputmodels.Param;
import com.board.config.boardconfiggui.data.outputmodels.clockconfig.ClockConfig;
import com.board.config.boardconfiggui.data.outputmodels.clockconfig.ClockConfigParam;
import com.board.config.boardconfiggui.data.outputmodels.connectivityconfig.ConnectivityConfig;
import com.board.config.boardconfiggui.data.outputmodels.ipconfig.DeviceConfiguration;
import com.board.config.boardconfiggui.data.outputmodels.ipconfig.IpConfig;
import com.board.config.boardconfiggui.data.outputmodels.ipconfig.IpConfigIp;
import com.board.config.boardconfiggui.data.outputmodels.ipconfig.SignalParam;
import com.board.config.boardconfiggui.data.outputmodels.pinconfig.PinConfig;
import com.board.config.boardconfiggui.data.outputmodels.pinconfig.PinConfigParam;
import com.board.config.boardconfiggui.data.outputmodels.pinconfig.PinConfigPort;
import com.board.config.boardconfiggui.data.repo.BoardResultsRepo;
import com.board.config.boardconfiggui.data.repo.InputConfigRepo;
import com.board.config.boardconfiggui.interfaces.BoardPageDataSaverInterface;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.layout.StackPane;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.net.URL;
import java.util.*;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class BoardConfigController implements Initializable{

    private static final Logger logger = Logger.getLogger(BoardConfigController.class.getName());

    private final String PIN_CONFIG_NAME = "Pin Config";
    private final String IP_CONFIG_NAME = "Ip Config";
    private final String CLOCK_CONFIG_NAME = "Clock Config";

    private final List<String> ipNames = new ArrayList<>();
    private final Map<String, Map<String, Pin>> portPinsMap = new HashMap<>();
    private Object currentController;

    @FXML
    public TreeView<String> treeView;

    @FXML
    public StackPane contentArea;

    private final HomeViewController homeViewController;
    private final String xmlFolderPath;

    public BoardConfigController(String xmlFolderPath, HomeViewController homeViewController) {
        this.xmlFolderPath = xmlFolderPath;
        this.homeViewController = homeViewController;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        clearData();
        initializeData();
        setTreeView();
    }

    private void clearData() {
        portPinsMap.clear();
        ipNames.clear();
    }

    private void initializeData() {
        InputConfigRepo inputConfigRepo = InputConfigRepo.getInstance();
        List<Port> ports = inputConfigRepo.getPinConfig().getPorts();

        for(Port port : ports){
            Map<String, Pin> pinsMap = new HashMap<>();
            for(Pin pin : port.getPinList()) {
                pinsMap.put(pin.getName(), pin);
            }
            portPinsMap.put(port.getName(), pinsMap);
        }

        List<Ip> ipList = inputConfigRepo.getIpConfig().getIpList();
        for(Ip ip : ipList){
            if(ip.getName().equals(Constants.QSPI_IP_NAME)){ //For now we are not adding QSPI IP name in UI
                continue;
            }
            List<Instance> instanceList = ip.getInstanceList();
            for(Instance instance: instanceList){
                ipNames.add(instance.getName());
            }
        }
    }

    private void setTreeView() {

        TreeItem<String> root = new TreeItem<>();

        //Creating Pin config tree view
        TreeItem<String> pinConfig = new TreeItem<>(PIN_CONFIG_NAME);
        for(String port : portPinsMap.keySet()){
            TreeItem<String> portTree = new TreeItem<>(port);
            List<String> pinNames = new ArrayList<>(portPinsMap.get(port).keySet().stream().toList());
            Collections.sort(pinNames);
            for(String pin : pinNames){
                TreeItem<String> pinTree = new TreeItem<>(pin);
                portTree.getChildren().add(pinTree);
            }
            pinConfig.getChildren().add(portTree);
            pinConfig.setExpanded(true);
        }

        //Creating Ip config tree view
        TreeItem<String> ipConfig = new TreeItem<>(IP_CONFIG_NAME);
        for(String ip : ipNames){
            ipConfig.getChildren().add(new TreeItem<>(ip));
        }

        //Creating Clock config tree view
        TreeItem<String> clockConfig = new TreeItem<>(CLOCK_CONFIG_NAME);
        ipConfig.setExpanded(true);

        //Adding all tree views to root view
        root.getChildren().addAll(pinConfig, ipConfig, clockConfig);
        treeView.setRoot(root);
        treeView.setShowRoot(false);

        //Handling mouse click events
        treeView.setOnMouseClicked(event -> {
            TreeItem<String> item = treeView.getSelectionModel().getSelectedItem();
            if (item != null && item.isLeaf()) {
                saveCurrentControllerData();
                loadContentArea(item);
            }
        });
    }

    @FXML
    private void generateOutput() {
        BoardResultsRepo boardResultsRepo = BoardResultsRepo.getInstance();
        BoardResult boardResult = boardResultsRepo.getBoardResult();
        String message = validateResult(boardResult);
        if (StringUtils.isNotEmpty(message)) {
            Utils.alertDialog(
                    Alert.AlertType.ERROR,
                    "Validation Failed",
                    "Unable to generate board configuration, please check and resolve below errors",
                    message
            );
        } else {
            if(Utils.saveData(xmlFolderPath))
                homeViewController.onOutputGenerateClick();
        }
    }

    private void loadContentArea(TreeItem<String> item) {

        String PIN_CONFIG_FXML_NAME = "pin-config.fxml";
        String IP_CONFIG_FXML_NAME = "ip-config.fxml";
        String CLOCK_CONFIG_FXML_NAME = "clock-config.fxml";

        Parent fxml = null;
        FXMLLoader loader;

        try {
            if (item.getValue().equals(CLOCK_CONFIG_NAME)) {
                loader = new FXMLLoader(getClass().getResource(CLOCK_CONFIG_FXML_NAME));
                ClockConfigController clockConfigController = new ClockConfigController();
                currentController = clockConfigController;
                loader.setController(clockConfigController);
                fxml = loader.load();
            } else if (item.getParent().getValue().equals(IP_CONFIG_NAME)) {
                loader = new FXMLLoader(getClass().getResource(IP_CONFIG_FXML_NAME));
                IpConfigController ipConfigController = new IpConfigController(item.getValue());
                currentController = ipConfigController;
                loader.setController(ipConfigController);
                fxml = loader.load();
            }else{
                loader = new FXMLLoader(getClass().getResource(PIN_CONFIG_FXML_NAME));
                Pin pin = portPinsMap.get(item.getParent().getValue()).get(item.getValue());
                PinConfigController pinConfigController = new PinConfigController(item.getParent().getValue(), pin);
                currentController = pinConfigController;
                loader.setController(pinConfigController);
                fxml = loader.load();
            }
        }catch (IOException e) {
            logger.warning("IOException is occurred while loading content area data" + e);
        }

        if(fxml != null){
            contentArea.getChildren().removeAll();
            contentArea.getChildren().setAll(fxml);
        }
    }

    private void saveCurrentControllerData() {
        if (currentController instanceof BoardPageDataSaverInterface) {
            ((BoardPageDataSaverInterface) currentController).saveData();
        }
    }

    private String validateResult(BoardResult boardResult) {
        StringBuilder missingConfig = new StringBuilder();
        if (boardResult == null) {
            missingConfig.append("Board result is missing.");
            return missingConfig.toString();
        }

        String message = validateConnectivityConfig(boardResult.getConnectivityConfig());
        if (StringUtils.isNotEmpty(message)) {
            missingConfig.append(message);
        }

        message = validatePinConfig(boardResult.getPinConfig());
        if (StringUtils.isNotEmpty(message)) {
            missingConfig.append(message);
        }

        message = validateIpConfig(boardResult.getIpConfig());
        if (StringUtils.isNotEmpty(message)) {
            missingConfig.append(message);
        }

        message = validateClockConfig(boardResult.getClockConfig());
        if (StringUtils.isNotEmpty(message)) {
            missingConfig.append(message);
        }

        return missingConfig.toString();
    }

    private String validateConnectivityConfig(ConnectivityConfig connectivityConfig) {
        if (connectivityConfig == null) {
            return "ConnectivityConfig: Connectivity config is missing. \n";
        }

        if (connectivityConfig.getNumberOfIps() < 0) {
            return "ConnectivityConfig: Number of IPs selected are zero. \n";
        }

        if (connectivityConfig.getNumberOfIps() != connectivityConfig.getIp().size()) {
            return "ConnectivityConfig: Number of IPs param and selected IPs are not same. \n";
        }

        return null;
    }

    private String validatePinConfig(PinConfig pinConfig) {
        if (pinConfig == null || CollectionUtils.isEmpty(pinConfig.getPorts())) {
            return "PinConfig: Pin config is missing. \n";
        }

        StringBuilder missingConfig = new StringBuilder();

        for (PinConfigPort port : pinConfig.getPorts()) {
            if (CollectionUtils.isEmpty(port.getConfigParams())) {
                missingConfig.append("PinConfig: Pins are not configured for ")
                        .append(port.getName()).append(".\n");
                continue;
            }

            port.getConfigParams().stream()
                    .map(this::validatePinConfigParam)
                    .filter(StringUtils::isNotEmpty)
                    .forEach(message -> missingConfig.append("PinConfig ")
                            .append(port.getName())
                            .append(": ")
                            .append(message)
                            .append(".\n"));
        }

        return missingConfig.toString();
    }

    private String validatePinConfigParam(PinConfigParam pinConfigParam) {
        boolean isByPassMode = pinConfigParam.isByPassMode() != null && pinConfigParam.isByPassMode();
        boolean isIntEnable = pinConfigParam.isIntEnable() != null && pinConfigParam.isIntEnable();

        if (!isByPassMode) {
            if (StringUtils.isEmpty(pinConfigParam.getDirection())) {
                return "Direction is missing for pin " + pinConfigParam.getPin();
            }

            if (Constants.DIRECTION_OUTPUT.equals(pinConfigParam.getDirection())
                    && StringUtils.isEmpty(pinConfigParam.getValue())) {
                return "Output value is missing for pin " + pinConfigParam.getPin();
            }
        } else if (isIntEnable) {
            if (StringUtils.isEmpty(pinConfigParam.getIntType()) || StringUtils.isEmpty(pinConfigParam.getIntValue())) {
                return "IntType or IntValue is missing for pin " + pinConfigParam.getPin();
            }
        }

        return null;
    }


    private String validateIpConfig(IpConfig ipConfig) {
        if (ipConfig == null || CollectionUtils.isEmpty(ipConfig.getIps())) {
            return "IPConfig: IP config is missing. \n";
        }

        StringBuilder missingConfig = new StringBuilder();

        for (IpConfigIp ipConfigIp : ipConfig.getIps()) {
            if (CollectionUtils.isEmpty(ipConfigIp.getPorts())) {
                missingConfig.append("IPConfig: Ports are not configured for ")
                        .append(ipConfigIp.getName()).append(".\n");
            } else {
                // Validate IpConfig signal params
                List<SignalParam> ipSignalParams = ipConfigIp.getPorts().stream()
                        .flatMap(port -> {
                            if (CollectionUtils.isNotEmpty(port.getSignalParams())) {
                                return port.getSignalParams().stream();
                            } else {
                                return Stream.empty();
                            }
                        })
                        .collect(Collectors.toList());

                if (CollectionUtils.isNotEmpty(ipSignalParams)) {
                    String message = validateIpConfigSignalParams(ipConfigIp.getName(), ipSignalParams);
                    if (StringUtils.isNotEmpty(message)) {
                        missingConfig.append(message);
                    }
                }
            }

            // Validate IpConfig params
            if (CollectionUtils.isEmpty(ipConfigIp.getParams())) {
                missingConfig.append("IPConfig: Required params like sysclk, i2cFreq, sdrFreg are missing for ")
                        .append(ipConfigIp.getName()).append(".\n");
            } else {
                String message = validateIpConfigParams(ipConfigIp.getName(), ipConfigIp.getParams());
                if (StringUtils.isNotEmpty(message)) {
                    missingConfig.append(message);
                }
            }

            //Validate device Descriptor
            if (ipConfigIp.getDeviceDescriptor() != null) {
                String message = validateIpConfigDeviceConfiguration(ipConfigIp.getName(),
                        ipConfigIp.getDeviceDescriptor().getDeviceConfigurations());

                if (StringUtils.isNotEmpty(message)) {
                    missingConfig.append(message);
                }
            }
        }

        return missingConfig.toString();
    }

    private String validateIpConfigSignalParams(String ipName, List<SignalParam> signalParams) {
        // ToDo: Need to check the required signals
        List<String> signalsRequired = Arrays.asList(ipName + "_SCL", ipName + "_SDA");

        StringBuilder missingConfig = new StringBuilder();

        signalsRequired.stream()
                .filter(signal -> signalParams.stream().noneMatch(param -> param.getName().equals(signal)))
                .forEach(signal -> missingConfig.append("IPConfig: SignalParam ")
                        .append(signal)
                        .append(" is missing for ")
                        .append(ipName)
                        .append(".\n"));

        return missingConfig.toString();
    }

    private String validateIpConfigParams(String ipName, List<Param> params) {
        List<String> paramsRequired = Constants.I3C_IP_CONFIG_PARAMS_REQUIRED;

        StringBuilder missingConfig = new StringBuilder();

        paramsRequired.stream()
                .filter(paramRequired -> params.stream().noneMatch(p -> p.getName().equals(paramRequired)))
                .forEach(paramRequired -> missingConfig.append("IPConfig: Param ")
                        .append(paramRequired)
                        .append(" is missing for ")
                        .append(ipName)
                        .append(".\n"));

        return missingConfig.toString();
    }

    private String validateIpConfigDeviceConfiguration(String ipName, List<DeviceConfiguration> deviceConfigurations) {
        if (CollectionUtils.isEmpty(deviceConfigurations)) {
            return "IPConfig DeviceConfiguration: Device configurations are missing for " + ipName + ". \n";
        }

        StringBuilder missingConfig = new StringBuilder();

        List<String> paramsRequired = Constants.I3C_DEVICE_CONFIG_PARAMS_REQUIRED;
        List<String> ibiParamsRequired = Constants.I3C_CONFIG_IBI_DEVICE_PARAMS_REQUIRED;

        for (DeviceConfiguration deviceConfiguration : deviceConfigurations) {
            if (CollectionUtils.isEmpty(deviceConfiguration.getParams())) {
                missingConfig.append("IPConfig DeviceConfiguration: Required params are missing for ")
                        .append(deviceConfiguration.getName())
                        .append(" in ").append(ipName)
                        .append(".\n");
                continue;
            }

            List<Param> params = deviceConfiguration.getParams();
            boolean isIbiDevice = params.stream()
                    .anyMatch(param -> param.getName().equals(Constants.IS_IBI_DEVICE) && param.getValue().equals(Constants.ONE));

            List<String> combinedParamsRequired = new ArrayList<>(paramsRequired);
            if (isIbiDevice) {
                combinedParamsRequired.addAll(ibiParamsRequired);
            }

            for (String paramRequired : combinedParamsRequired) {
                if (params.stream().noneMatch(p -> p.getName().equals(paramRequired))) {
                    missingConfig.append("IPConfig DeviceConfiguration: Param ")
                            .append(paramRequired)
                            .append(" is missing for ")
                            .append(deviceConfiguration.getName())
                            .append(" in ").append(ipName).append(".\n");
                }
            }
        }

        return missingConfig.toString();
    }

    private String validateClockConfig(ClockConfig clockConfig) {
        if (clockConfig == null || CollectionUtils.isEmpty(clockConfig.getConfigParams())) {
            return "ClockConfig: Clock config is missing";
        }

        List<String> paramsRequired = Constants.CLOCK_CONFIG_PARAMS_REQUIRED;
        List<ClockConfigParam> configParams = clockConfig.getConfigParams();

        StringBuilder missingConfig = new StringBuilder();

        paramsRequired.stream()
                .filter(paramRequired -> configParams.stream().noneMatch(p -> p.getName().equals(paramRequired)))
                .forEach(paramRequired -> missingConfig.append("ClockConfig: Param ")
                        .append(paramRequired)
                        .append(" is missing.\n"));

        return missingConfig.toString();
    }

}