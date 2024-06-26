package com.board.config.boardconfiggui;

import com.board.config.boardconfiggui.data.outputmodels.BoardResult;
import com.board.config.boardconfiggui.data.outputmodels.clockconfig.ClockConfig;
import com.board.config.boardconfiggui.data.outputmodels.clockconfig.ClockConfigParam;
import com.board.config.boardconfiggui.data.repo.BoardResultsRepo;
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

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import static com.board.config.boardconfiggui.data.Constants.NUMERIC_FIELD_ONLY;

public class ClockConfigController implements Initializable, BoardPageDataSaverInterface {

    private List<ClockConfigModel> viewsData;

    @FXML
    private Pane clockConfigGridPane;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        viewsData = new ArrayList<>();

        viewsData.add(new ClockConfigModel(ClockConfigModel.ViewType.RADIO_BUTTON, "FREF_BYPASS", ""));
        viewsData.add(new ClockConfigModel(ClockConfigModel.ViewType.RADIO_BUTTON, "DAC_EN", ""));
        viewsData.add(new ClockConfigModel(ClockConfigModel.ViewType.RADIO_BUTTON, "DSM_EN", ""));
        viewsData.add(new ClockConfigModel(ClockConfigModel.ViewType.RADIO_BUTTON, "FBDIV_INT", ""));
        viewsData.add(new ClockConfigModel(ClockConfigModel.ViewType.RADIO_BUTTON, "FBDIV_FRAC", ""));
        viewsData.add(new ClockConfigModel(ClockConfigModel.ViewType.RADIO_BUTTON, "FOUT_POSTDIV_EN", ""));
        viewsData.add(new ClockConfigModel(ClockConfigModel.ViewType.RADIO_BUTTON, "FOUT_VCO_EN", ""));
        viewsData.add(new ClockConfigModel(ClockConfigModel.ViewType.TEXT_FIELD, "FREF", ""));
        viewsData.add(new ClockConfigModel(ClockConfigModel.ViewType.RADIO_BUTTON, "OFFSET_CAL_EN", ""));
        viewsData.add(new ClockConfigModel(ClockConfigModel.ViewType.RADIO_BUTTON, "OFFSET_CAL_BYP", ""));
        viewsData.add(new ClockConfigModel(ClockConfigModel.ViewType.TEXT_FIELD, "OFFSET_CAL_CNT", ""));
        viewsData.add(new ClockConfigModel(ClockConfigModel.ViewType.TEXT_FIELD, "OFFSET_CAL_IN", ""));
        viewsData.add(new ClockConfigModel(ClockConfigModel.ViewType.RADIO_BUTTON, "OFFSET_FAST_CAL", ""));
        viewsData.add(new ClockConfigModel(ClockConfigModel.ViewType.RADIO_BUTTON, "PLL_EN", ""));
        viewsData.add(new ClockConfigModel(ClockConfigModel.ViewType.TEXT_FIELD, "REF_DIV", ""));
        viewsData.add(new ClockConfigModel(ClockConfigModel.ViewType.TEXT_FIELD, "POSTDIV1", ""));
        viewsData.add(new ClockConfigModel(ClockConfigModel.ViewType.TEXT_FIELD, "POSTDIV2", ""));
        viewsData.add(new ClockConfigModel(ClockConfigModel.ViewType.TEXT_FIELD, "FOUT_POSTDIV1", ""));
        viewsData.add(new ClockConfigModel(ClockConfigModel.ViewType.TEXT_FIELD, "FOUT_POSTDIV2", ""));
        viewsData.add(new ClockConfigModel(ClockConfigModel.ViewType.TEXT_FIELD, "FOUT_POSTDIV3", ""));
        viewsData.add(new ClockConfigModel(ClockConfigModel.ViewType.TEXT_FIELD, "FOUT_POSTDIV4", ""));
        viewsData.add(new ClockConfigModel(ClockConfigModel.ViewType.TEXT_FIELD, "FOUT_POSTDIV5", ""));

        prefillData();

        GridPane gridPane = new GridPane();
        gridPane.setAlignment(Pos.CENTER);
        gridPane.setPadding(new Insets(60));
        gridPane.setVgap(40); // Set vertical gap between rows
        gridPane.setHgap(40);


        int col = 0;
        int row = 0;
        for (ClockConfigModel viewData : viewsData) {
            ClockConfigModel.ViewType viewType = viewData.getViewType();
            String label = viewData.getLabel();

            Label labelControl = new Label(label + ":");
            Font myFont = Font.font("", FontWeight.BOLD, 14);
            labelControl.setFont(myFont);

            gridPane.add(labelControl, col, row);
            if (viewType.equals(ClockConfigModel.ViewType.TEXT_FIELD)) {
                TextField textField = new TextField();
                textField.setPromptText(NUMERIC_FIELD_ONLY);
                textField.setText(viewData.getResult());
                gridPane.add(textField, col + 1, row);
                // Store user-entered value into result field
                textField.textProperty().addListener((observable, oldValue, newValue) -> {
                    if (newValue.matches("\\d*")) {
                        viewData.setResult(newValue);
                    } else {
                        textField.setText(oldValue);
                    }
                });
            } else if (viewType.equals(ClockConfigModel.ViewType.RADIO_BUTTON)) {
                ToggleGroup toggleGroup = new ToggleGroup();
                RadioButton radioButton1 = new RadioButton("TRUE");
                RadioButton radioButton2 = new RadioButton("FALSE");
                radioButton1.setToggleGroup(toggleGroup);
                radioButton2.setToggleGroup(toggleGroup);
                HBox radioButtonBox = new HBox(10, radioButton1, radioButton2);
                gridPane.add(radioButtonBox, col + 1, row);

                if (viewData.getResult().equals("TRUE")) {
                    radioButton1.setSelected(true);
                } else if (viewData.getResult().equals("FALSE")) {
                    radioButton2.setSelected(true);
                }
                toggleGroup.selectedToggleProperty().addListener((observable, oldValue, newValue) -> {
                    if (newValue != null) {
                        RadioButton selectedRadioButton = (RadioButton) newValue;
                        viewData.setResult(selectedRadioButton.getText());
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

        if (clockConfig == null || clockConfig.getConfigParams().isEmpty()) {
            return;
        }

        clockConfig.getConfigParams().forEach(clockConfigParam -> {
            if (!clockConfigParam.getValue().isEmpty()) {
                viewsData.forEach(data -> {
                    if (data.getLabel().equals(clockConfigParam.getName())) {
                        data.setResult(clockConfigParam.getValue());
                    }
                });
            }
        });
    }

    @Override
    public void saveData() {
        List<ClockConfigParam> clockConfigParams = new ArrayList<>();
        viewsData.forEach(data -> {
            if (!data.getResult().isEmpty()) {
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
