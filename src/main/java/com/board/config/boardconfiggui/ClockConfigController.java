package com.board.config.boardconfiggui;

import com.board.config.boardconfiggui.data.Constants;
import com.board.config.boardconfiggui.data.enums.DataType;
import com.board.config.boardconfiggui.data.inputmodels.clockconfig.ClockConfigParameter;
import com.board.config.boardconfiggui.data.outputmodels.BoardResult;
import com.board.config.boardconfiggui.data.outputmodels.clockconfig.ClockConfig;
import com.board.config.boardconfiggui.data.outputmodels.clockconfig.ClockConfigParam;
import com.board.config.boardconfiggui.data.repo.BoardResultsRepo;
import com.board.config.boardconfiggui.data.repo.InputConfigRepo;
import com.board.config.boardconfiggui.interfaces.BoardPageDataSaverInterface;
import com.board.config.boardconfiggui.ui.models.ClockConfigModel;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;

import static com.board.config.boardconfiggui.data.Constants.NUMERIC_FIELD_ONLY;

public class ClockConfigController implements Initializable, BoardPageDataSaverInterface {

    private final List<ClockConfigModel> configModelList = new ArrayList<>();

    @FXML
    private Pane clockConfigGridPane;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        InputConfigRepo inputConfigRepo = InputConfigRepo.getInstance();
        List<ClockConfigParameter> configParameters = inputConfigRepo.getClockConfig().getClockConfigParams();
        if (CollectionUtils.isNotEmpty(configParameters)) {
            for (ClockConfigParameter configParameter : configParameters) {
                configModelList.add(new ClockConfigModel(configParameter.getId(), configParameter.getType(), configParameter.getDisplayValue()));
            }
        }

        prefillData();

        GridPane gridPane = new GridPane();
        gridPane.setAlignment(Pos.CENTER);
        gridPane.setPadding(new Insets(60));
        gridPane.setVgap(40); // Set vertical gap between rows
        gridPane.setHgap(40);


        int col = 0;
        int row = 0;
        for (ClockConfigModel clockConfigModel : configModelList) {

            Label labelControl = new Label(clockConfigModel.getLabel() + ":");
            Font myFont = Font.font("", FontWeight.BOLD, 14);
            labelControl.setFont(myFont);

            gridPane.add(labelControl, col, row);
            if (Objects.equals(clockConfigModel.getViewType(), DataType.Integer)) {
                TextField textField = new TextField();
                textField.setPromptText(NUMERIC_FIELD_ONLY);
                textField.setText(clockConfigModel.getResult());
                gridPane.add(textField, col + 1, row);
                // Store user-entered value into result field
                textField.textProperty().addListener((observable, oldValue, newValue) -> {
                    if (newValue.matches("\\d*")) {
                        clockConfigModel.setResult(newValue);
                    } else {
                        textField.setText(oldValue);
                    }
                });
            } else if (Objects.equals(clockConfigModel.getViewType(), DataType.Boolean)) {
                ToggleGroup toggleGroup = new ToggleGroup();
                RadioButton radioButton1 = new RadioButton(Constants.TRUE);
                RadioButton radioButton2 = new RadioButton(Constants.FALSE);
                radioButton1.setToggleGroup(toggleGroup);
                radioButton2.setToggleGroup(toggleGroup);
                HBox radioButtonBox = new HBox(10, radioButton1, radioButton2);
                gridPane.add(radioButtonBox, col + 1, row);

                if (StringUtils.equals(clockConfigModel.getResult(), Constants.TRUE)) {
                    radioButton1.setSelected(true);
                } else if (StringUtils.equals(clockConfigModel.getResult(), Constants.FALSE)) {
                    radioButton2.setSelected(true);
                }

                toggleGroup.selectedToggleProperty().addListener((observable, oldValue, newValue) -> {
                    if (StringUtils.isNotEmpty(newValue.toString())) {
                        RadioButton selectedRadioButton = (RadioButton) newValue;
                        clockConfigModel.setResult(selectedRadioButton.getText());
                    }
                });
            }
            col += 2;
            if (col >= 6) {
                col = 0;
                row++;
            }
        }

        clockConfigGridPane.getChildren().add(gridPane);

    }

    private void prefillData() {
        BoardResultsRepo boardResultsRepo = BoardResultsRepo.getInstance();
        BoardResult boardResult = boardResultsRepo.getBoardResult();
        ClockConfig clockConfig = boardResult.getClockConfig();

        if (Objects.isNull(clockConfig) || CollectionUtils.isEmpty(clockConfig.getConfigParams())) {
            return;
        }

        clockConfig.getConfigParams().forEach(clockConfigParam -> {
            if (!clockConfigParam.getValue().isEmpty()) {
                configModelList.forEach(data -> {
                    if (StringUtils.equals(data.getId(), clockConfigParam.getName())) {
                        data.setResult(clockConfigParam.getValue());
                    }
                });
            }
        });
    }

    @Override
    public void saveData() {
        List<ClockConfigParam> clockConfigParams = new ArrayList<>();
        configModelList.forEach(data -> {
            if (StringUtils.isNotEmpty(data.getResult())) {
                clockConfigParams.add(new ClockConfigParam(data.getLabel(), data.getResult()));
            }
        });

        BoardResultsRepo boardResultsRepo = BoardResultsRepo.getInstance();
        BoardResult boardResult = boardResultsRepo.getBoardResult();
        ClockConfig clockConfig = new ClockConfig();
        clockConfig.setConfigParams(clockConfigParams);
        boardResult.setClockConfig(clockConfig);

    }
}
