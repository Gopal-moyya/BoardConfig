package com.board.config.boardconfiggui;

import com.board.config.boardconfiggui.controllers.OnOffButtonWidgetController;
import com.board.config.boardconfiggui.data.Constants;
import com.board.config.boardconfiggui.data.IpPinConfig;
import com.board.config.boardconfiggui.data.enums.ConfigParam;
import com.board.config.boardconfiggui.data.enums.ViewType;
import com.board.config.boardconfiggui.data.inputmodels.pinconfig.Pin;
import com.board.config.boardconfiggui.data.inputmodels.pinconfig.Port;
import com.board.config.boardconfiggui.data.repo.InputConfigRepo;
import com.board.config.boardconfiggui.interfaces.BoardPageDataSaverInterface;
import com.board.config.boardconfiggui.ui.models.ConfigParamModel;
import com.board.config.boardconfiggui.ui.models.SPIConfigModel;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.paint.Color;
import org.apache.commons.lang3.StringUtils;

import java.net.URL;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import static com.board.config.boardconfiggui.data.Constants.NUMERIC_FIELD_ONLY;

public class SPIIpConfigController implements Initializable, BoardPageDataSaverInterface {
    private final String ipName;
    private List<SPIConfigModel> spiConfigModels;
    private List<ConfigParamModel> configParamModels;

    @FXML
    private OnOffButtonWidgetController onOffWidgetController;
    @FXML
    private GridPane spiControlsGridPane;
    @FXML
    private GridPane configControlsGridPane;
    @FXML
    private Label configControlHeader;

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
                spiControlsGridPane.setVisible(true);
                configControlsGridPane.setVisible(true);
                configControlHeader.setVisible(true);
            } else {
                onOffWidgetController.setButtonTextColor(Color.valueOf("#ff0000"));
                onOffWidgetController.setButtonText("OFF");
                spiControlsGridPane.setVisible(false);
                configControlsGridPane.setVisible(false);
                configControlHeader.setVisible(false);
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

        spiControlsGridPane.getRowConstraints().add(new RowConstraints(10, 60, 60));
        int col = 0;
        int row = 0;
        for (SPIConfigModel spiConfigModel : spiConfigModels) {
            String label = spiConfigModel.getLabel();

            Label labelControl = new Label(label);
            labelControl.setAlignment(Pos.BASELINE_CENTER);
            spiControlsGridPane.add(labelControl, col, row);

            ComboBox<String> comboBox = new ComboBox<>();
            List<String> displayValues = spiConfigModel.getIpPinConfigs()
                    .stream()
                    .map(IpPinConfig::getDisplayValue)
                    .collect(Collectors.toList());
            comboBox.setItems(FXCollections.observableArrayList(displayValues));
            comboBox.setPromptText(Constants.SELECT);
            comboBox.setPrefWidth(280);
            spiControlsGridPane.add(comboBox, col + 1, row);

            comboBox.itemsProperty().addListener((observable, oldValue, newValue) -> {
                spiConfigModel.setResult(comboBox.getSelectionModel().getSelectedItem());
            });

            col += 2;
            if (col >= 4) {
                col = 0;
                row++;
                spiControlsGridPane.getRowConstraints().add(new RowConstraints(10, 60, 60));
            }
        }

        buildConfigControls();

    }

    private void buildConfigControls() {
        List<ConfigParam> writeCompletionConfigParams = Constants.QSPI_WRITE_COMPLETION_CONFIG_CONTROLS;
        configParamModels = new ArrayList<>();

        writeCompletionConfigParams.forEach(param -> {
            configParamModels.add(new ConfigParamModel(param, ""));
        });

        configControlHeader.setText("Write completion config controls: ");

        int col = 0;
        int row = 0;
        configControlsGridPane.getRowConstraints().add(new RowConstraints(10, 60, 60));
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
                spinner.valueProperty().addListener((observable, oldValue, newValue) -> {
                    configParamModel.setResult(newValue.toString());
                });
            }

            col += 2;
            if (col >= 4) {
                col = 0;
                row++;
                configControlsGridPane.getRowConstraints().add(new RowConstraints(10, 60, 60));
            }
        }
    }
}
