package com.board.config.boardconfiggui.controllers;

import com.board.config.boardconfiggui.common.Utils;
import com.board.config.boardconfiggui.ui.models.SlaveDeviceConfigModel;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;

import java.net.URL;
import java.util.Arrays;
import java.util.ResourceBundle;

public class SlaveWidgetController implements Initializable {
    @FXML
    private LabelTextFieldWidgetController i2cFmPlusSpeedController;

    @FXML
    private LabelTextFieldWidgetController addressController;

    @FXML
    private LabelTextFieldWidgetController hdrCapableController;
    @FXML
    private LabelTextFieldWidgetController legacyI2CDevController;
    @FXML
    private LabelTextFieldWidgetController hasStaticAddressController;
    @FXML
    private LabelTextFieldWidgetController staticAddressController;
    @FXML
    private LabelTextFieldWidgetController dynamicAddressController;
    @FXML
    private LabelTextFieldWidgetController isIbiDeviceController;
    @FXML
    private LabelTextFieldWidgetController ibiPayloadSizeController;
    @FXML
    private LabelTextFieldWidgetController ibiPayloadSpeedLimitController;

    @FXML
    private LabelComboBoxWidgetController devRoleController;

    private SlaveDeviceConfigModel slaveDeviceConfigModel;

    public void setSlaveWidgetObject() {
        //TODO: need to get the object from maim class and update the UI
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        slaveDeviceConfigModel = new SlaveDeviceConfigModel("Device0");

        i2cFmPlusSpeedController.setTxtFieldLabel("i2cFmPlusSpeed");
        addressController.setTxtFieldLabel("i2c10bAddr");
        hdrCapableController.setTxtFieldLabel("hdrCapable");
        legacyI2CDevController.setTxtFieldLabel("legacyI2CDev");
        hasStaticAddressController.setTxtFieldLabel("hasStaticAddress");
        staticAddressController.setTxtFieldLabel("staticAddress");
        dynamicAddressController.setTxtFieldLabel("dynamicAddress");
        isIbiDeviceController.setTxtFieldLabel("isIbiDevice");
        ibiPayloadSizeController.setTxtFieldLabel("ibiPayloadSize");
        ibiPayloadSpeedLimitController.setTxtFieldLabel("ibiPayloadSpeedLimit");

        devRoleController.setComboBoxLabel("devRole", "Select Device Role");

        devRoleController.setCmbInfo(FXCollections.observableArrayList(Utils.getSlaveDeviceRoles()));

        // listening the value from fields

        i2cFmPlusSpeedController.getTxtInformation().addListener((observable, oldValue, newValue) ->
                slaveDeviceConfigModel.setI2cFmPlusSpeed(newValue.matches("\\d*") ? newValue : oldValue));

        addressController.getTxtInformation().addListener((observable, oldValue, newValue) ->
                slaveDeviceConfigModel.setI2c10bAddr(newValue.matches("\\d*") ? newValue : oldValue));

        hdrCapableController.getTxtInformation().addListener((observable, oldValue, newValue) ->
                slaveDeviceConfigModel.setHdrCapable(newValue.matches("\\d*") ? newValue : oldValue));

        legacyI2CDevController.getTxtInformation().addListener((observable, oldValue, newValue) ->
                slaveDeviceConfigModel.setLegacyI2CDev(newValue.matches("\\d*") ? newValue : oldValue));

        hasStaticAddressController.getTxtInformation().addListener((observable, oldValue, newValue) ->
                slaveDeviceConfigModel.setHasStaticAddress(newValue.matches("\\d*") ? newValue : oldValue));

        staticAddressController.getTxtInformation().addListener((observable, oldValue, newValue) ->
                slaveDeviceConfigModel.setStaticAddress(newValue.matches("\\d*") ? newValue : oldValue));

        dynamicAddressController.getTxtInformation().addListener((observable, oldValue, newValue) ->
                slaveDeviceConfigModel.setDynamicAddress(newValue.matches("\\d*") ? newValue : oldValue));

        isIbiDeviceController.getTxtInformation().addListener((observable, oldValue, newValue) ->
                slaveDeviceConfigModel.setIsIbiDevice(newValue.matches("\\d*") ? newValue : oldValue));

        ibiPayloadSizeController.getTxtInformation().addListener((observable, oldValue, newValue) ->
                slaveDeviceConfigModel.setIbiPayloadSize(newValue.matches("\\d*") ? newValue : oldValue));

        ibiPayloadSpeedLimitController.getTxtInformation().addListener((observable, oldValue, newValue) ->
                slaveDeviceConfigModel.setIbiPayloadSpeedLimit(newValue.matches("\\d*") ? newValue : oldValue));

        devRoleController.getCmbInfoItem().addListener((observable, oldValue, newValue) ->
                slaveDeviceConfigModel.setDevRole(newValue.matches("\\d*") ? newValue : oldValue));
    }
}
