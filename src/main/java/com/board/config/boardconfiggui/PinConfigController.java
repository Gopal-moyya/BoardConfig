package com.board.config.boardconfiggui;

import com.board.config.boardconfiggui.controllers.LabelComboBoxWidgetController;
import com.board.config.boardconfiggui.controllers.OnOffButtonWidgetController;
import com.board.config.boardconfiggui.data.Constants;
import com.board.config.boardconfiggui.data.inputmodels.pinconfig.Pin;
import com.board.config.boardconfiggui.data.outputmodels.ipconfig.IpConfig;
import com.board.config.boardconfiggui.data.outputmodels.ipconfig.IpConfigIp;
import com.board.config.boardconfiggui.data.outputmodels.ipconfig.IpConfigPort;
import com.board.config.boardconfiggui.data.outputmodels.ipconfig.SignalParam;
import com.board.config.boardconfiggui.data.outputmodels.pinconfig.PinConfig;
import com.board.config.boardconfiggui.data.outputmodels.pinconfig.PinConfigParam;
import com.board.config.boardconfiggui.data.repo.BoardResultsRepo;
import com.board.config.boardconfiggui.interfaces.BoardPageDataSaverInterface;
import com.board.config.boardconfiggui.ui.models.PinConfigModel;
import com.board.config.boardconfiggui.ui.models.PinType;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;

import java.net.URL;
import java.util.*;

import static com.board.config.boardconfiggui.data.Constants.*;

public class PinConfigController implements Initializable, BoardPageDataSaverInterface {

    private final String portName;
    private final Pin currentPin;
    private final PinConfigModel pinConfigUiModel;
    private final List<PinType> pinTypes;
    final Map<String, PinType> pinMap = new HashMap<>();
    final List<String> modeTypes = new ArrayList<>();

    @FXML
    private OnOffButtonWidgetController onOffWidgetController;
    @FXML
    private LabelComboBoxWidgetController modeTypesWidgetController;
    @FXML
    private LabelComboBoxWidgetController inPutOutPutWidgetController;

    @FXML
    private LabelComboBoxWidgetController inPutWidgetController;

    @FXML
    private LabelComboBoxWidgetController outPutWidgetController;

    @FXML
    private LabelComboBoxWidgetController exioWidgetController;

    @FXML
    private LabelComboBoxWidgetController levelWidgetController;

    @FXML
    private LabelComboBoxWidgetController edgeWidgetController;


    @FXML
    HBox modeTypesWidget;

    @FXML
    HBox inPutOutPutWidget;

    @FXML
    HBox inPutWidget;

    @FXML
    HBox outPutWidget;

    @FXML
    HBox exioWidget;

    @FXML
    HBox levelWidget;

    @FXML
    HBox edgeWidget;


    public PinConfigController(String portName, Pin pin, List<PinType> pinTypes) {
        this.portName = portName;
        this.currentPin = pin;
        this.pinTypes = pinTypes;
        pinConfigUiModel = new PinConfigModel(portName, pin.getName());
    }
    IpConfig ipConfig = BoardResultsRepo.getInstance().getBoardResult().getIpConfig();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        onOffWidgetController.setButtonLabel(PIN_STATUS_LABEL);

        onOffWidgetController.setButtonTextColor(Color.valueOf("#ff0000"));

        for (PinType pinType : pinTypes) {
            pinMap.put(pinType.getName(), pinType);
        }

        PinType pinType = pinMap.get(MODES_TEXT);
        if (Objects.nonNull(pinType)) {
            modeTypes.addAll(pinType.getChildren());
        }

        populatePreviousData();

        onOffWidgetController.getButton().setOnAction(actionEvent -> {

            if (StringUtils.equals(OnOffButtonWidgetController.OFF_TXT, ((Button) actionEvent.getSource()).getText())) {
                pinConfigUiModel.setPinStatus(true);
                handleButtonOnStatus(modeTypes);

            } else {
                pinConfigUiModel.setPinStatus(false);
                handleButtonOffStatus();
            }
        });

        modeTypesWidgetController.getCmbInfoItem().addListener((observable, oldValue, newValue) -> {
            pinConfigUiModel.setSelectedMode(newValue);

            if (StringUtils.isBlank(newValue)) {
                return;
            }

            PinType modePinType = pinMap.get(newValue);
            List<String> ioOptions = new ArrayList<>();
            if (Objects.nonNull(modePinType)) {
                ioOptions = modePinType.getChildren();
            }

            inPutOutPutWidget.setVisible(true);
            inPutOutPutWidgetController.setComboBoxLabel(newValue.concat(TYPE), Constants.SELECT);
            inPutOutPutWidgetController.setItems(FXCollections.observableArrayList(ioOptions));

            //resetting child widgets
            inPutWidget.setVisible(false);
            outPutWidget.setVisible(false);
            exioWidget.setVisible(false);
            levelWidget.setVisible(false);
            edgeWidget.setVisible(false);

        });

        inPutOutPutWidgetController.getCmbInfoItem().addListener((observable, oldValue, newValue) -> {
            pinConfigUiModel.setSelectedIOType(newValue);
            if (BY_PASS.equals(pinConfigUiModel.getSelectedMode())) {
                pinConfigUiModel.setSelectedBypassType(newValue);
                return;
            }

            inPutWidgetController.setItems(null);
            outPutWidgetController.setItems(null);

            PinType ioPinType = pinMap.get(newValue);

            List<String> inputOrOutputOptions = new ArrayList<>();
            if (Objects.nonNull(ioPinType)) {
                inputOrOutputOptions = ioPinType.getChildren();
            }

            if (INPUT.equals(newValue)) {
                inPutWidget.setVisible(true);
                outPutWidget.setVisible(false);
                pinConfigUiModel.setSelectedOutputType(null);
                outPutWidget.managedProperty().bind(outPutWidget.visibleProperty());
                inPutWidgetController.setComboBoxLabel(newValue.concat(TYPE), Constants.SELECT);
                inPutWidgetController.setItems(FXCollections.observableArrayList(inputOrOutputOptions));
            } else {
                outPutWidget.setVisible(true);
                inPutWidget.setVisible(false);
                exioWidget.setVisible(false);
                levelWidget.setVisible(false);
                edgeWidget.setVisible(false);
                pinConfigUiModel.setSelectedInputType(null);
                inPutWidget.managedProperty().bind(inPutWidget.visibleProperty());
                outPutWidgetController.setComboBoxLabel(newValue.concat(TYPE), Constants.SELECT);
                outPutWidgetController.setItems(FXCollections.observableArrayList(inputOrOutputOptions));
            }
        });

        inPutWidgetController.getCmbInfoItem().addListener((observable, oldValue, newValue) -> {
            pinConfigUiModel.setSelectedInputType(newValue);
            onInputValueChange(newValue);

        });

        exioWidgetController.getCmbInfoItem().addListener((observable, oldValue, newValue) -> {
            pinConfigUiModel.setSelectedExioType(newValue);
            onExtIValueChange(newValue);
        });

        levelWidgetController.getCmbInfoItem().addListener((observable, oldValue, newValue) -> {
            pinConfigUiModel.setSelectedLevelType(newValue);
        });

        edgeWidgetController.getCmbInfoItem().addListener((observable, oldValue, newValue) -> {
            pinConfigUiModel.setSelectedEdgeType(newValue);
        });

        outPutWidgetController.getCmbInfoItem().addListener((observable, oldValue, newValue) -> {
            pinConfigUiModel.setSelectedOutputType(newValue);
        });
    }

    private void populatePreviousData() {

        PinConfig pinConfig = BoardResultsRepo.getInstance().getBoardResult().getPinConfig();
        if (Objects.nonNull(pinConfig)) {
            PinConfigParam pinConfigParam = pinConfig.getPinConfigParamsData(portName, currentPin.getName());

            if (Objects.nonNull(pinConfigParam)) {
                if (BooleanUtils.isTrue(pinConfigParam.isByPassMode())) {
                    if(StringUtils.isNotEmpty(pinConfigParam.getValue())) {
                        pinConfigUiModel.setPinStatus(true);
                        pinConfigUiModel.setSelectedMode(BY_PASS);
                        pinConfigUiModel.setSelectedBypassType(pinConfigParam.getValue());
                        inPutOutPutWidget.setVisible(true);
                    }
                } else{
                    pinConfigUiModel.setPinStatus(true);
                    pinConfigUiModel.setSelectedMode(GPIO);

                    if (StringUtils.isEmpty(pinConfigParam.getDirection())) {

                        if (pinConfigParam.isIntEnable()) {
                            String extiValue = pinMap.keySet().stream().filter(s -> s.contains(EXTI)).findFirst().orElse(null);
                            pinConfigUiModel.setSelectedIOType(INPUT);
                            pinConfigUiModel.setSelectedInputType(extiValue);
                            if (EDGE_TRIGGERED.equalsIgnoreCase(pinConfigParam.getIntType())) {
                                pinConfigUiModel.setSelectedExioType(EDGE);
                                pinConfigUiModel.setSelectedEdgeType(pinConfigParam.getIntValue());
                            } else {
                                pinConfigUiModel.setSelectedExioType(LEVEL);
                                pinConfigUiModel.setSelectedLevelType(pinConfigParam.getIntValue());
                            }
                        }
                    } else {
                        if (DIRECTION_INPUT.equalsIgnoreCase(pinConfigParam.getDirection())) {
                            pinConfigUiModel.setSelectedIOType(INPUT);
                            pinConfigUiModel.setSelectedInputType(INPUT);
                        } else {
                            pinConfigUiModel.setSelectedIOType(OUTPUT);
                            pinConfigUiModel.setSelectedOutputType(pinConfigParam.getValue());
                        }
                    }
                }
                updateUI();
            }
        }
    }

    private void updateUI() {
        if (Objects.nonNull(pinConfigUiModel) && pinConfigUiModel.isPinStatus()) {

            handleButtonOnStatus(modeTypes);
            String selectedMode = pinConfigUiModel.getSelectedMode();
            modeTypesWidgetController.setSelectedValue(selectedMode);
            if (StringUtils.isNotEmpty(selectedMode)) {
                inPutOutPutWidget.setVisible(true);
                inPutOutPutWidgetController.setComboBoxLabel(selectedMode.concat(TYPE), Constants.SELECT);
                inPutOutPutWidgetController.setItems(FXCollections.observableArrayList(pinMap.get(selectedMode).getChildren()));

                if(BY_PASS.equals(selectedMode)){
                    inPutOutPutWidgetController.setSelectedValue(pinConfigUiModel.getSelectedBypassType());
                    return;
                }
                String selectedIOType = pinConfigUiModel.getSelectedIOType();
                inPutOutPutWidgetController.setSelectedValue(selectedIOType);
                if (StringUtils.isNotEmpty(selectedIOType)) {
                    List<String> options = pinMap.get(selectedIOType).getChildren();

                    if (INPUT.equals(selectedIOType)) {
                        inPutWidget.setVisible(true);
                        outPutWidget.managedProperty().bind(outPutWidget.visibleProperty());
                        inPutWidgetController.setComboBoxLabel(selectedIOType.concat(TYPE), Constants.SELECT);
                        inPutWidgetController.setItems(FXCollections.observableArrayList(options));

                        String selectedInputType = pinConfigUiModel.getSelectedInputType();
                        inPutWidgetController.setSelectedValue(selectedInputType);

                        if (StringUtils.isNotEmpty(selectedInputType) && selectedInputType.contains(EXTI)) {
                            exioWidget.setVisible(true);
                            exioWidgetController.setComboBoxLabel(selectedInputType.concat(TYPE), SELECT);
                            exioWidgetController.setItems(FXCollections.observableArrayList(pinMap.get(selectedInputType).getChildren()));

                            String selectedExioType = pinConfigUiModel.getSelectedExioType();
                            exioWidgetController.setSelectedValue(selectedExioType);

                            List<String> triggerOptions = pinMap.get(selectedExioType).getChildren();
                            if(LEVEL.equalsIgnoreCase(selectedExioType)){
                                levelWidget.setVisible(true);
                                edgeWidget.managedProperty().bind(edgeWidget.visibleProperty());
                                levelWidgetController.setComboBoxLabel(selectedExioType.concat(TYPE), SELECT);
                                levelWidgetController.setItems(FXCollections.observableArrayList(triggerOptions));
                                levelWidgetController.setSelectedValue(pinConfigUiModel.getSelectedLevelType());
                            } else {
                                edgeWidget.setVisible(true);
                                levelWidget.managedProperty().bind(levelWidget.visibleProperty());
                                edgeWidgetController.setComboBoxLabel(selectedExioType.concat(TYPE), SELECT);
                                edgeWidgetController.setItems(FXCollections.observableArrayList(triggerOptions));
                                edgeWidgetController.setSelectedValue(pinConfigUiModel.getSelectedEdgeType());
                            }
                        }
                    } else {
                        outPutWidget.setVisible(true);
                        inPutWidget.managedProperty().bind(inPutWidget.visibleProperty());
                        outPutWidgetController.setComboBoxLabel(selectedIOType.concat(TYPE), Constants.SELECT);
                        outPutWidgetController.setItems(FXCollections.observableArrayList(options));
                        outPutWidgetController.setSelectedValue(pinConfigUiModel.getSelectedOutputType());
                    }
                }
            }
        } else {
            handleButtonOffStatus();
        }
    }

    private void handleButtonOnStatus(List<String> modeTypes) {
        onOffWidgetController.setButtonTextColor(Color.valueOf("#008000"));
        onOffWidgetController.setButtonText(OnOffButtonWidgetController.ON_TXT);
        modeTypesWidget.setVisible(true);
        modeTypesWidgetController.setComboBoxLabel(MODE_TYPE_LABEL, Constants.SELECT);
        modeTypesWidgetController.setItems(FXCollections.observableArrayList(modeTypes));
    }

    private void handleButtonOffStatus() {
        onOffWidgetController.setButtonText(OnOffButtonWidgetController.OFF_TXT);
        onOffWidgetController.setButtonTextColor(Color.valueOf("#ff0000"));
        IpConfigIp selectedIpConfig = ipConfig.getIpConfig(pinConfigUiModel.getSelectedIp());
        selectedIpConfig.deleteExistingSignalParam(new SignalParam(currentPin.getName(), pinConfigUiModel.getSelectedBypassType()));
        clearAllUIData();
    }

    private void onInputValueChange(String inputValue) {

        pinConfigUiModel.setSelectedExioType(null);
        onExtIValueChange(null);
        exioWidget.setVisible(false);
        exioWidgetController.setItems(null);

        if (StringUtils.isBlank(inputValue)) {
            pinConfigUiModel.setSelectedInputType(null);
            return;
        }

        if (pinConfigUiModel.getSelectedInputType().contains(EXTI)) {

            PinType extPin = pinMap.get(inputValue);
            List<String> ioOptions = new ArrayList<>();
            if (Objects.nonNull(extPin)) {
                ioOptions.addAll(extPin.getChildren());
            }

            exioWidget.setVisible(true);
            exioWidgetController.setComboBoxLabel(inputValue.concat(TYPE), Constants.SELECT);
            exioWidgetController.setItems(FXCollections.observableArrayList(ioOptions));
        }
    }

    private void clearAllUIData() {
        modeTypesWidget.setVisible(false);
        inPutOutPutWidget.setVisible(false);
        inPutWidget.setVisible(false);
        outPutWidget.setVisible(false);
        exioWidget.setVisible(false);
        levelWidget.setVisible(false);
        edgeWidget.setVisible(false);

        pinConfigUiModel.setPinStatus(false);
        pinConfigUiModel.setSelectedMode(null);
        pinConfigUiModel.setSelectedIOType(null);
        pinConfigUiModel.setSelectedInputType(null);
        pinConfigUiModel.setSelectedOutputType(null);
        pinConfigUiModel.setSelectedExioType(null);
        pinConfigUiModel.setSelectedLevelType(null);
        pinConfigUiModel.setSelectedEdgeType(null);

        modeTypesWidgetController.setItems(null);
    }

    private void onExtIValueChange(String extValue) {

        if (StringUtils.isBlank(extValue)) {
            pinConfigUiModel.setSelectedEdgeType(null);
            pinConfigUiModel.setSelectedLevelType(null);
            edgeWidget.setVisible(false);
            levelWidget.setVisible(false);
            levelWidgetController.setItems(null);
            edgeWidgetController.setItems(null);
            return;
        }

        PinType pin = pinMap.get(extValue);
        List<String> exioOptions = new ArrayList<>();

        if (Objects.nonNull(pin)) {
            exioOptions.addAll(pin.getChildren());
        }

        if (pinConfigUiModel.getSelectedExioType().equals(LEVEL)) {
            levelWidget.setVisible(true);
            edgeWidget.setVisible(false);
            edgeWidget.managedProperty().bind(edgeWidget.visibleProperty());
            levelWidgetController.setComboBoxLabel(extValue.concat(TYPE), Constants.SELECT);
            levelWidgetController.setItems(FXCollections.observableArrayList(exioOptions));
        } else {
            edgeWidget.setVisible(true);
            levelWidget.setVisible(false);
            levelWidget.managedProperty().bind(levelWidget.visibleProperty());
            edgeWidgetController.setComboBoxLabel(extValue.concat(TYPE), Constants.SELECT);
            edgeWidgetController.setItems(FXCollections.observableArrayList(exioOptions));
        }
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

            if(StringUtils.isEmpty(pinConfigUiModel.getSelectedBypassType())){
                return;
            }
            pinConfigParam.setByPassMode(true);
            pinConfigParam.setValue(pinConfigUiModel.getSelectedBypassType());
            SignalParam signalParam = new SignalParam(currentPin.getName(), pinConfigUiModel.getSelectedBypassType());

            IpConfigIp selectedIpConfig = ipConfig.getIpConfig(pinConfigUiModel.getSelectedIp());
            if(ObjectUtils.isEmpty(selectedIpConfig)){
                IpConfigIp newIpConfigIp = new IpConfigIp(pinConfigUiModel.getSelectedIp());

                IpConfigPort portInfo = new IpConfigPort(portName);
                portInfo.addSignalParam(signalParam);

                newIpConfigIp.addPort(portInfo);
                ipConfig.addIpConfigIp(newIpConfigIp);
            }else {
                selectedIpConfig.deleteExistingSignalParam(signalParam);
                IpConfigPort portInfo = selectedIpConfig.getPortInfo(portName);

                if(ObjectUtils.isNotEmpty(portInfo))
                    portInfo.addSignalParam(signalParam);
                else {
                    portInfo = new IpConfigPort(portName);
                    portInfo.addSignalParam(signalParam);

                    selectedIpConfig.addPort(portInfo);
                }
            }
        } else {
            //If not selected input/output type
            if (StringUtils.isEmpty(pinConfigUiModel.getSelectedIOType())) {
                return;
            }

            if (INPUT.equalsIgnoreCase(pinConfigUiModel.getSelectedIOType())) {
                String selectedInputType = pinConfigUiModel.getSelectedInputType();
                if (StringUtils.isEmpty(selectedInputType)) {
                    return;
                }
                if (INPUT.equalsIgnoreCase(selectedInputType)) {
                    pinConfigParam.setByPassMode(false);
                    pinConfigParam.setDirection(DIRECTION_INPUT);
                } else {
                    pinConfigParam.setIntEnable(true);
                    String selectedExioType = pinConfigUiModel.getSelectedExioType();
                    if (StringUtils.isNotEmpty(selectedExioType)) {
                        if (selectedExioType.equalsIgnoreCase(LEVEL)) {
                            pinConfigParam.setIntType(LEVEL_TRIGGERED);
                            pinConfigParam.setIntValue(pinConfigUiModel.getSelectedLevelType());
                        } else {
                            pinConfigParam.setIntType(EDGE_TRIGGERED);
                            pinConfigParam.setIntValue(pinConfigUiModel.getSelectedEdgeType());
                        }
                    }
                }
            } else {
                pinConfigParam.setByPassMode(false);
                pinConfigParam.setDirection(DIRECTION_OUTPUT);
                pinConfigParam.setValue(pinConfigUiModel.getSelectedOutputType());
            }
        }
        pinConfig.savePinConfig(portName, currentPin.getName(), pinConfigParam);
    }
}