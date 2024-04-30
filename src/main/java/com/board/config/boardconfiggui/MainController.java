package com.board.config.boardconfiggui;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.TreeCell;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.util.Callback;

import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.ResourceBundle;

public class MainController implements Initializable {

    private final String[] ports = {"Port1", "Port2"};
    private final String[] pins = {"Pin1", "Pin2","pin3"};
    private final String[] Ips = {"Ip1", "Ip2","Ip3"};

    private final Map<String, String[]> pinsMap = new HashMap<>();

    @FXML
    public TreeView<String> treeView;

    @FXML
    public StackPane contentArea;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        for(String port : ports){
            pinsMap.put(port, pins);
        }

        setTreeView();
    }

    private void setTreeView() {

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
            if (item != null && item.isLeaf()) {
                loadContentArea(item);
            }
        });
    }

    private void loadContentArea(TreeItem<String> item) {

        Parent fxml = null;

        try {
            if (item.getValue().equals("Clock Config")) {
                fxml = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("clock-config.fxml")));
            } else if (item.getParent().getValue().equals("Ip Config")) {
                fxml = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("ip-config.fxml")));
            }else{
                fxml = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("pin-config.fxml")));
            }
        }catch (IOException e) {
            e.printStackTrace();
        }

        if(fxml != null){
            contentArea.getChildren().removeAll();
            contentArea.getChildren().setAll(fxml);
        }
    }


}