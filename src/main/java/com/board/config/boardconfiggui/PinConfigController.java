package com.board.config.boardconfiggui;

import com.board.config.boardconfiggui.controllers.LabelComboBoxWidgetController;
import com.board.config.boardconfiggui.controllers.OnOffButtonWidgetController;
import com.board.config.boardconfiggui.data.inputmodels.pinconfig.Pin;
import com.board.config.boardconfiggui.data.outputmodels.pinconfig.PinConfig;
import com.board.config.boardconfiggui.data.outputmodels.pinconfig.PinConfigParam;
import com.board.config.boardconfiggui.data.repo.BoardResultsRepo;
import com.board.config.boardconfiggui.interfaces.BoardPageDataSaverInterface;
import com.board.config.boardconfiggui.ui.models.PinConfigModel;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import org.apache.commons.codec.binary.StringUtils;

import java.net.URL;
import java.util.*;
import java.util.stream.Collectors;

import static com.board.config.boardconfiggui.data.Constants.*;

public class PinConfigController implements Initializable, BoardPageDataSaverInterface {

    private final String portName;
    private final Pin currentPin;
    private final PinConfigModel pinConfigUiModel;
    @FXML
    private OnOffButtonWidgetController onOffWidgetController;
    @FXML
    private LabelComboBoxWidgetController dropDownWidgetController;
    @FXML
    private LabelComboBoxWidgetController inPutOutPutWidgetController;

    @FXML
    HBox dropDownWidget;

    @FXML
    HBox inPutOutPutWidget;

    public PinConfigController(String portName, Pin pin) {
        this.portName = portName;
        this.currentPin = pin;
        pinConfigUiModel = new PinConfigModel(portName, pin.getName());
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        PinConfig pinConfig = BoardResultsRepo.getInstance().getBoardResult().getPinConfig();
        if (Objects.nonNull(pinConfig)) {
            PinConfigParam pinConfigParam = pinConfig.getPinConfigParamsData(portName, currentPin.getName());

            if (Objects.nonNull(pinConfigParam)) {
                if (pinConfigParam.isByPassMode()) {
                    pinConfigUiModel.setSelectedMode(BY_PASS);
                    inPutOutPutWidget.setVisible(false);
                } else {
                    pinConfigUiModel.setSelectedMode(GPIO);
                    String value = pinConfigParam.getDirection() == null ? pinConfigParam.getIntValue() : pinConfigParam.getDirection();
                    pinConfigUiModel.setSelectedValue(value);
                    inPutOutPutWidget.setVisible(true);
                }
                pinConfigUiModel.setPinStatus(true);
            }

        }
        List<String> modeTypes = getModeTypes(currentPin.getValues());
        List<String> gpioOptions = getGpioOptions(currentPin.getValues());

        if (Objects.nonNull(pinConfigUiModel) && pinConfigUiModel.isPinStatus()) {
            onOffWidgetController.setButtonTextColor(Color.valueOf("#008000"));
            onOffWidgetController.setButtonText(OnOffButtonWidgetController.ON_TXT);
            dropDownWidget.setVisible(true);
            dropDownWidgetController.setComboBoxLabel("Mode Type:", "select");
            dropDownWidgetController.setItems(FXCollections.observableArrayList(modeTypes));
            dropDownWidgetController.setSelectedMode(pinConfigUiModel.getSelectedMode());
            inPutOutPutWidgetController.setComboBoxLabel("I/O Type:", "select");
            inPutOutPutWidgetController.setItems(FXCollections.observableArrayList(gpioOptions));
            inPutOutPutWidgetController.setSelectedMode(pinConfigUiModel.getSelectedValue());
        } else {
            onOffWidgetController.setButtonText(OnOffButtonWidgetController.OFF_TXT);
            onOffWidgetController.setButtonTextColor(Color.valueOf("#ff0000"));
            dropDownWidget.setVisible(false);
            inPutOutPutWidget.setVisible(false);
            dropDownWidgetController.setItems(null);
            inPutOutPutWidgetController.setItems(null);
        }
        onOffWidgetController.setButtonLabel("PIN Status:");

        onOffWidgetController.getButton().setOnAction(actionEvent -> {
            if (StringUtils.equals(OnOffButtonWidgetController.OFF_TXT, ((Button) actionEvent.getSource()).getText())) {
                onOffWidgetController.setButtonTextColor(Color.valueOf("#008000"));
                onOffWidgetController.setButtonText("ON");
                pinConfigUiModel.setPinStatus(true);
                dropDownWidget.setVisible(true);
                inPutOutPutWidget.setVisible(false);
                dropDownWidgetController.setItems(null);
                dropDownWidgetController.setComboBoxLabel("Mode Type:", "select");
                dropDownWidgetController.setItems(FXCollections.observableArrayList(modeTypes));
            } else {
                onOffWidgetController.setButtonTextColor(Color.valueOf("#ff0000"));
                onOffWidgetController.setButtonText("OFF");
                dropDownWidget.setVisible(false);
                inPutOutPutWidget.setVisible(false);
                pinConfigUiModel.setPinStatus(false);
                pinConfigUiModel.setSelectedMode(null);
                pinConfigUiModel.setSelectedValue(null);
            }

        });


        dropDownWidgetController.getCmbInfoItem().addListener((observable, oldValue, newValue) -> {
            if (GPIO.equals(newValue)) {
                inPutOutPutWidget.setVisible(true);
                inPutOutPutWidgetController.setComboBoxLabel("I/O Type:", "select");
                inPutOutPutWidgetController.setItems(FXCollections.observableArrayList(gpioOptions));
                pinConfigUiModel.setSelectedMode(inPutOutPutWidgetController.getCmbInfoItem().getValue());
            } else {
                inPutOutPutWidget.setVisible(false);
            }
            pinConfigUiModel.setSelectedMode(dropDownWidgetController.getCmbInfoItem().getValue());

        });

        inPutOutPutWidgetController.getCmbInfoItem().addListener((observable, oldValue, newValue) -> {
            pinConfigUiModel.setSelectedValue(inPutOutPutWidgetController.getCmbInfoItem().getValue());
        });

    }

    private List<String> getModeTypes(String modes) {
        List<String> options = new ArrayList<>();
        if (modes.contains("],")) {
            String[] splitValues = modes.split("],");
            for (String mode : splitValues) {
                if (mode.contains(":")) {
                    String[] split = mode.split(":");
                    if (!split[0].contains(EXTI)) { //Not adding EXTIO to the list
                        options.add(split[0].trim());
                    }
                }
            }
        }
        return options;
    }

    private List<String> getGpioOptions(String pinData) {

        List<String> options = new ArrayList<>();
        String[] values = pinData.split("[\\[\\]]");
        for (String value : values) {
            if (value.endsWith(":") && !value.contains(BY_PASS)) { //In UI, We have to show only the options of modes other than bypass
                continue;
            }
            if (value.contains(BY_PASS)) { //If we get Bypass value, then we are exiting the loop as we don't need further options as they are sublist of Bypass
                break;
            }
            if (value.contains(",")) {
                options.addAll(Arrays.stream(value.split(",")).toList());
            }
        }
        return options.stream().map(String::trim).collect(Collectors.toList());
    }

    @Override
    public void saveData() {
        PinConfigParam pinConfigParam = new PinConfigParam(currentPin.getName());
        PinConfig pinConfig = BoardResultsRepo.getInstance().getBoardResult().getPinConfig();

        if (Objects.isNull(pinConfigUiModel.getSelectedMode())) {
            pinConfig.removePinConfig(portName, currentPin.getName());
            return;
        }
        if (pinConfigUiModel.getSelectedMode().equals(BY_PASS)) {
            pinConfigParam.setByPassMode(true);
        } else {
            switch (pinConfigUiModel.getSelectedValue()) {
                case INPUT:
                case OUTPUT_LOW:
                case OUTPUT_HIGH:
                    pinConfigParam.setByPassMode(false);
                    pinConfigParam.setDirection(pinConfigUiModel.getSelectedValue());
                    break;
                case LEVEL_TRIG_HIGH:
                case LEVEL_TRIG_LOW:
                case EDGE_TRIG_FALL:
                case EDGE_TRIG_RISE:
                case EDGE_TRIG_ANY:
                    pinConfigParam.setIntEnable(true);
                    String selectedValue = pinConfigUiModel.getSelectedValue();
                    pinConfigParam.setIntType(selectedValue.startsWith(LEVEL) ?
                            LEVEL_TRIGGERED : EDGE_TRIGGERED);
                    pinConfigParam.setIntValue(pinConfigUiModel.getSelectedValue());
                    break;
                default:
                    break;
            }
        }
        pinConfig.savePinConfig(portName, currentPin.getName(), pinConfigParam);
    }
}
