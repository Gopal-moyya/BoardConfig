package com.board.config.boardconfiggui;
import com.board.config.boardconfiggui.datamodels.ClockConfigModel;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import java.util.ArrayList;
import java.util.List;

public class ClockConfig extends Application {

    private List<ClockConfigModel> dataModel = new ArrayList<>();

    @Override
    public void start(Stage primaryStage) {
        List<ClockConfigModel> viewsData = new ArrayList<>();

        viewsData.add(new ClockConfigModel(ClockConfigModel.ViewType.RADIO_BUTTON, "FREF_BYPASS", ""));
        viewsData.add(new ClockConfigModel(ClockConfigModel.ViewType.RADIO_BUTTON, "DAC_EN", ""));
        viewsData.add(new ClockConfigModel(ClockConfigModel.ViewType.RADIO_BUTTON, "DSM_EN", ""));
        viewsData.add(new ClockConfigModel(ClockConfigModel.ViewType.RADIO_BUTTON, "FBDIV_INT", ""));
        viewsData.add(new ClockConfigModel(ClockConfigModel.ViewType.RADIO_BUTTON, "FBDIV_FRAC", ""));
        viewsData.add(new ClockConfigModel(ClockConfigModel.ViewType.RADIO_BUTTON, "FOUT_POSTDIV_EN", ""));
        viewsData.add(new ClockConfigModel(ClockConfigModel.ViewType.RADIO_BUTTON, "FOUT_VCO_EN", ""));
        viewsData.add( new ClockConfigModel(ClockConfigModel.ViewType.TEXT_FIELD, "FREF", ""));
        viewsData.add(new ClockConfigModel(ClockConfigModel.ViewType.RADIO_BUTTON, "OFFSET_CAL_EN", ""));
        viewsData.add(new ClockConfigModel(ClockConfigModel.ViewType.RADIO_BUTTON, "OFFSET_CAL_BYP", ""));
        viewsData.add(new ClockConfigModel(ClockConfigModel.ViewType.TEXT_FIELD, "OFFSET_CAL_CNT", ""));
        viewsData.add(new ClockConfigModel(ClockConfigModel.ViewType.TEXT_FIELD, "OFFSET_CAL_IN", ""));
        viewsData.add( new ClockConfigModel(ClockConfigModel.ViewType.RADIO_BUTTON, "OFFSET_FAST_CAL", ""));
        viewsData.add(new ClockConfigModel(ClockConfigModel.ViewType.RADIO_BUTTON, "PLL_EN", ""));
        viewsData.add(new ClockConfigModel(ClockConfigModel.ViewType.TEXT_FIELD, "REF_DIV", ""));
        viewsData.add(new ClockConfigModel(ClockConfigModel.ViewType.TEXT_FIELD, "POSTDIV1", ""));
        viewsData.add(new ClockConfigModel(ClockConfigModel.ViewType.TEXT_FIELD, "POSTDIV2", ""));
        viewsData.add(new ClockConfigModel(ClockConfigModel.ViewType.TEXT_FIELD, "FOUT_POSTDIV1", ""));
        viewsData.add(new ClockConfigModel(ClockConfigModel.ViewType.TEXT_FIELD, "FOUT_POSTDIV2", ""));
        viewsData.add(new ClockConfigModel(ClockConfigModel.ViewType.TEXT_FIELD, "FOUT_POSTDIV3", ""));
        viewsData.add(new ClockConfigModel(ClockConfigModel.ViewType.TEXT_FIELD, "FOUT_POSTDIV4", ""));
        viewsData.add(new ClockConfigModel(ClockConfigModel.ViewType.TEXT_FIELD, "FOUT_POSTDIV5", ""));

        dataModel.addAll(viewsData);

        GridPane gridPane = new GridPane();
        gridPane.setPadding(new Insets(20));
        gridPane.setVgap(10);

        int row = 0;
        for (ClockConfigModel viewData : viewsData) {
            ClockConfigModel.ViewType viewType = viewData.getViewType();
            String label = viewData.getLabel();

            Label labelControl = new Label(label + ":");
            gridPane.add(labelControl, 0, row);

            if (viewType.equals(ClockConfigModel.ViewType.TEXT_FIELD)) {
                TextField textField = new TextField();
                gridPane.add(textField, 1, row);
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
                gridPane.add(radioButtonBox, 1, row);
                // Store selected radio button value into result field
                toggleGroup.selectedToggleProperty().addListener((observable, oldValue, newValue) -> {
                    if (newValue != null) {
                        RadioButton selectedRadioButton = (RadioButton) newValue;
                        viewData.setResult(selectedRadioButton.getText());
                    }
                });
            }

            row++;
        }

        Scene scene = new Scene(gridPane, 750, 750);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Clock Config");
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }


}
