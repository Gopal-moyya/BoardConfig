package com.board.config.boardconfiggui.controllers;

import com.board.config.boardconfiggui.common.Utils;
import com.board.config.boardconfiggui.data.Constants;
import com.board.config.boardconfiggui.ui.models.SlaveDeviceConfigModel;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import java.net.URL;
import java.util.ResourceBundle;

public class SlaveWidgetController implements Initializable {
    @FXML
    public LabelTextFieldWidgetController deviceNameController;

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
        slaveDeviceConfigModel = new SlaveDeviceConfigModel();

        deviceNameController.setLabel(Constants.DEVICE_NAME);
        i2cFmPlusSpeedController.setLabel(Constants.I2CFM_PLUS_SPEED);
        addressController.setLabel(Constants.I2C10B_ADDRESS);
        hdrCapableController.setLabel(Constants.HDR_CAPABLE);
        legacyI2CDevController.setLabel(Constants.LEGACY_I2C_DEV);
        hasStaticAddressController.setLabel(Constants.HAS_STATIC_ADDRESS);
        staticAddressController.setLabel(Constants.STATIC_ADDRESS);
        dynamicAddressController.setLabel(Constants.DYNAMIC_ADDRESS);
        isIbiDeviceController.setLabel(Constants.IS_IBI_DEVICE);
        ibiPayloadSizeController.setLabel(Constants.IBI_PAYLOAD_SIZE);
        ibiPayloadSpeedLimitController.setLabel(Constants.IBI_PAYLOAD_SPEED_LIMIT);

        devRoleController.setComboBoxLabel(Constants.DEV_ROLE, Constants.SELECT_DEVICE_ROLE);

        devRoleController.setItems(FXCollections.observableArrayList(Utils.getSlaveDeviceRoles()));

        // listening the value from fields
        deviceNameController.getText().addListener((observable, oldValue, newValue) ->
                slaveDeviceConfigModel.setDeviceName(newValue.matches("\\d*") ? newValue : oldValue));

        i2cFmPlusSpeedController.getText().addListener((observable, oldValue, newValue) ->
                slaveDeviceConfigModel.setI2cFmPlusSpeed(newValue.matches("\\d*") ? newValue : oldValue));

        addressController.getText().addListener((observable, oldValue, newValue) ->
                slaveDeviceConfigModel.setI2c10bAddr(newValue.matches("\\d*") ? newValue : oldValue));

        hdrCapableController.getText().addListener((observable, oldValue, newValue) ->
                slaveDeviceConfigModel.setHdrCapable(newValue.matches("\\d*") ? newValue : oldValue));

        legacyI2CDevController.getText().addListener((observable, oldValue, newValue) ->
                slaveDeviceConfigModel.setLegacyI2CDev(newValue.matches("\\d*") ? newValue : oldValue));

        hasStaticAddressController.getText().addListener((observable, oldValue, newValue) ->
                slaveDeviceConfigModel.setHasStaticAddress(newValue.matches("\\d*") ? newValue : oldValue));

        staticAddressController.getText().addListener((observable, oldValue, newValue) ->
                slaveDeviceConfigModel.setStaticAddress(newValue.matches("\\d*") ? newValue : oldValue));

        dynamicAddressController.getText().addListener((observable, oldValue, newValue) ->
                slaveDeviceConfigModel.setDynamicAddress(newValue.matches("\\d*") ? newValue : oldValue));

        isIbiDeviceController.getText().addListener((observable, oldValue, newValue) ->
                slaveDeviceConfigModel.setIsIbiDevice(newValue.matches("\\d*") ? newValue : oldValue));

        ibiPayloadSizeController.getText().addListener((observable, oldValue, newValue) ->
                slaveDeviceConfigModel.setIbiPayloadSize(newValue.matches("\\d*") ? newValue : oldValue));

        ibiPayloadSpeedLimitController.getText().addListener((observable, oldValue, newValue) ->
                slaveDeviceConfigModel.setIbiPayloadSpeedLimit(newValue.matches("\\d*") ? newValue : oldValue));

        devRoleController.getCmbInfoItem().addListener((observable, oldValue, newValue) ->
                slaveDeviceConfigModel.setDevRole(newValue.matches("\\d*") ? newValue : oldValue));
    }
}
