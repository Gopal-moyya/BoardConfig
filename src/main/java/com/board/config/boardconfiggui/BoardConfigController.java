package com.board.config.boardconfiggui;

import com.board.config.boardconfiggui.common.Utils;
import com.board.config.boardconfiggui.common.ValidationUtils;
import com.board.config.boardconfiggui.data.Constants;
import com.board.config.boardconfiggui.data.inputmodels.ipconfig.Instance;
import com.board.config.boardconfiggui.data.inputmodels.ipconfig.Ip;
import com.board.config.boardconfiggui.data.inputmodels.pinconfig.Pin;
import com.board.config.boardconfiggui.data.inputmodels.pinconfig.Port;
import com.board.config.boardconfiggui.data.outputmodels.genralconfig.GeneralConfig;
import com.board.config.boardconfiggui.data.outputmodels.genralconfig.Option;
import com.board.config.boardconfiggui.data.repo.BoardResultsRepo;
import com.board.config.boardconfiggui.data.repo.InputConfigRepo;
import com.board.config.boardconfiggui.interfaces.BoardPageDataSaverInterface;
import com.board.config.boardconfiggui.ui.dialogs.CustomAlert;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.layout.StackPane;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.net.URL;
import java.util.*;
import java.util.logging.Logger;

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
    private final String boardName;

    public BoardConfigController(String xmlFolderPath, String boardName, HomeViewController homeViewController) {
        this.xmlFolderPath = xmlFolderPath;
        this.boardName = boardName;
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

        //set the board name information
        GeneralConfig generalConfig = new GeneralConfig(new Option(boardName));
        BoardResultsRepo.getInstance().getBoardResult().setGeneralConfig(generalConfig);
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
        saveCurrentControllerData();
        String message = ValidationUtils.validateData();
        if (StringUtils.isNotEmpty(message)) {
            new CustomAlert(
                    Alert.AlertType.ERROR,
                    "Validation Failed",
                    "Unable to generate board configuration. Please review and resolve the following errors.",
                    message
            ).showAndWait();
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

}