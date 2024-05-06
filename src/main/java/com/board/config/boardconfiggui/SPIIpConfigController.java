package com.board.config.boardconfiggui;

import com.board.config.boardconfiggui.data.Constants;
import com.board.config.boardconfiggui.data.IpPinConfig;
import com.board.config.boardconfiggui.data.inputmodels.pinconfig.Pin;
import com.board.config.boardconfiggui.data.inputmodels.pinconfig.Port;
import com.board.config.boardconfiggui.data.repo.InputConfigRepo;
import com.board.config.boardconfiggui.interfaces.BoardPageDataSaverInterface;
import com.board.config.boardconfiggui.ui.models.SPIConfigModel;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

import java.net.URL;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SPIIpConfigController implements Initializable, BoardPageDataSaverInterface {
    private final String ipName;
    private List<SPIConfigModel> spiConfigModels;

    @FXML
    private Pane spiConfigGridPane;

    public SPIIpConfigController(String ipName) {
        this.ipName = ipName;
    }

    @Override
    public void saveData() {

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
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

        GridPane gridPane = new GridPane();
        gridPane.setAlignment(Pos.CENTER);
        gridPane.setPadding(new Insets(60));
        gridPane.setVgap(40); // Set vertical gap between rows
        gridPane.setHgap(40);

        int col = 0;
        int row = 0;
        for (SPIConfigModel spiConfigModel : spiConfigModels) {
            String label = spiConfigModel.getLabel();

            Label labelControl = new Label(label + ":");
            Font myFont = Font.font("", FontWeight.BOLD, 14);
            labelControl.setFont(myFont);

            gridPane.add(labelControl, col, row);

            TextField textField = new TextField();
            textField.setText(spiConfigModel.getResult());
            gridPane.add(textField, col + 1, row);
            // Store user-entered value into result field
            textField.textProperty().addListener((observable, oldValue, newValue) -> {
                spiConfigModel.setResult(newValue);
            });

            col += 2;
            if (col >= 6) {
                col = 0;
                row++;
            }
        }

        spiConfigGridPane.getChildren().add(gridPane);
    }
}
