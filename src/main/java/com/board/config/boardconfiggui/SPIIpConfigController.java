package com.board.config.boardconfiggui;

import com.board.config.boardconfiggui.controllers.OnOffButtonWidgetController;
import com.board.config.boardconfiggui.data.Constants;
import com.board.config.boardconfiggui.data.IpPinConfig;
import com.board.config.boardconfiggui.data.inputmodels.pinconfig.Pin;
import com.board.config.boardconfiggui.data.inputmodels.pinconfig.Port;
import com.board.config.boardconfiggui.data.repo.InputConfigRepo;
import com.board.config.boardconfiggui.interfaces.BoardPageDataSaverInterface;
import com.board.config.boardconfiggui.ui.models.SPIConfigModel;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import org.apache.commons.lang3.StringUtils;

import java.net.URL;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class SPIIpConfigController implements Initializable, BoardPageDataSaverInterface {
    private final String ipName;
    private List<SPIConfigModel> spiConfigModels;

    @FXML
    private OnOffButtonWidgetController onOffWidgetController;
    @FXML
    private GridPane gridpaneWidget;

    public SPIIpConfigController(String ipName) {
        this.ipName = ipName;
    }

    @Override
    public void saveData() {

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        onOffWidgetController.setButtonLabel("Enable Status:");
        onOffWidgetController.getButton().setOnAction(actionEvent -> {
            if (StringUtils.equals(OnOffButtonWidgetController.OFF_TXT, ((Button) actionEvent.getSource()).getText())) {
                onOffWidgetController.setButtonTextColor(Color.valueOf("#008000"));
                onOffWidgetController.setButtonText("ON");
                gridpaneWidget.setVisible(true);
            } else {
                onOffWidgetController.setButtonTextColor(Color.valueOf("#ff0000"));
                onOffWidgetController.setButtonText("OFF");
                gridpaneWidget.setVisible(false);
                //clear data
            }

        });

        InputConfigRepo inputConfigRepo = InputConfigRepo.getInstance();
        List<Port> ports = inputConfigRepo.getPinConfig().getPorts();

        Map<String, List<IpPinConfig>> spiConfigMap = new HashMap<>();
        spiConfigModels = new ArrayList<>();

        // Pattern to match SPI IP param from the pin values
        Pattern pattern = Pattern.compile("("+ ipName + "\\w+)");

        for (Port port : ports) {
            for (Pin pin : port.getPinList()) {
                Matcher matcher = pattern.matcher(pin.getValues());
                if (matcher.find()) {
                    String matchedString = matcher.group(1);
                    List<IpPinConfig> ipPinConfigs = spiConfigMap.computeIfAbsent(matchedString, k -> new ArrayList<>());

                    IpPinConfig ipPinConfig = new IpPinConfig(port.getName(), pin.getName());
                    ipPinConfigs.add(ipPinConfig);
                }
            }
        }

        if (!spiConfigMap.isEmpty()) {
            spiConfigMap.forEach((key, value) -> {
                spiConfigModels.add(new SPIConfigModel(key, value, ""));
            });
        }

//        GridPane gridPane = new GridPane();
//        gridpaneWidget.setAlignment(Pos.CENTER);
        gridpaneWidget.setPadding(new Insets(60));
        gridpaneWidget.setVgap(40); // Set vertical gap between rows
        gridpaneWidget.setHgap(40);

        int col = 0;
        int row = 0;
        for (SPIConfigModel spiConfigModel : spiConfigModels) {
            String label = spiConfigModel.getLabel();

            Label labelControl = new Label(label);
            gridpaneWidget.add(labelControl, col, row);

            ComboBox comboBox = new ComboBox();
            List<String> displayValues = spiConfigModel.getIpPinConfigs()
                    .stream()
                    .map(IpPinConfig::getDisplayValue)
                    .collect(Collectors.toList());
            comboBox.setItems(FXCollections.observableArrayList(displayValues));
            comboBox.setPromptText(Constants.SELECT);
            gridpaneWidget.add(comboBox, col + 1, row);

            comboBox.itemsProperty().addListener((observable, oldValue, newValue) -> {
                spiConfigModel.setResult(comboBox.getSelectionModel().getSelectedItem().toString());
            });

            col += 2;
            if (col >= 4) {
                col = 0;
                row++;
            }
        }

    }
}
