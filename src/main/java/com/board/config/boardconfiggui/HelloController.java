package com.board.config.boardconfiggui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;

import java.util.HashMap;
import java.util.Map;

public class HelloController {

    private final String[] ports = {"Port1", "Port2"};
    private final String[] pins = {"Pin1", "Pin2","pin3"};
    private final String[] Ips = {"Ip1", "Ip2","Ip3"};

    private final Map<String, String[]> pinsMap = new HashMap<>();

    @FXML
    private Label welcomeText;

    @FXML
    protected void onHelloButtonClick() {
        welcomeText.setText("Welcome to JavaFX Application!");
    }

    @FXML
    public TreeView<String> treeView;

    @FXML
    private void initialize() {
        for(String port : ports){
            pinsMap.put(port, pins);
        }
        TreeItem<String> root = new TreeItem<>();
        TreeItem<String> pinConfig = new TreeItem<>("Pin Config");
        for(String port : pinsMap.keySet()){
            TreeItem<String> portTree = new TreeItem<>(port);
            for(String pin : pinsMap.get(port)){
                TreeItem<String> pinTree = new TreeItem<>(pin);
                portTree.getChildren().add(pinTree);
            }
            pinConfig.getChildren().add(portTree);
            pinConfig.setExpanded(true);
        }
        TreeItem<String> ipConfig = new TreeItem<>("Ip Config");
        for(String ip : Ips){
            ipConfig.getChildren().add(new TreeItem<>(ip));
        }
        TreeItem<String> clockConfig = new TreeItem<>("Clock Config");
        ipConfig.setExpanded(true);
        root.getChildren().addAll(pinConfig, ipConfig, clockConfig);
        treeView.setRoot(root);
        treeView.setShowRoot(false);
        treeView.setOnMouseClicked(event -> {
            TreeItem<String> item = treeView.getSelectionModel().getSelectedItem();
            if (item.isLeaf()) {
                // Display the item text
                welcomeText.setText(item.getValue() + "is clicked");
            }
        });
    }
}