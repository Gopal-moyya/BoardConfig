package com.board.config.boardconfiggui.controllers;

import com.board.config.boardconfiggui.common.Utils;
import com.board.config.boardconfiggui.data.Constants;
import com.board.config.boardconfiggui.data.outputmodels.Param;
import com.board.config.boardconfiggui.data.outputmodels.ipconfig.DeviceConfiguration;
import com.board.config.boardconfiggui.ui.models.SlaveDeviceConfigModel;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import org.apache.commons.lang3.StringUtils;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
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

        ibiPayloadSizeController.setDisabled(true);
        ibiPayloadSpeedLimitController.setDisabled(true);

        devRoleController.setComboBoxLabel(Constants.DEV_ROLE, Constants.SELECT_DEVICE_ROLE);

        devRoleController.setItems(FXCollections.observableArrayList(Utils.getSlaveDeviceRoles()));

        // listening the value from fields
        deviceNameController.getText().addListener((observable, oldValue, newValue) ->
                slaveDeviceConfigModel.setDeviceName(newValue));

        i2cFmPlusSpeedController.getText().addListener((observable, oldValue, newValue) ->
                slaveDeviceConfigModel.setI2cFmPlusSpeed(newValue));

        addressController.getText().addListener((observable, oldValue, newValue) ->
                slaveDeviceConfigModel.setI2c10bAddr(newValue));

        hdrCapableController.getText().addListener((observable, oldValue, newValue) ->
                slaveDeviceConfigModel.setHdrCapable(newValue));

        legacyI2CDevController.getText().addListener((observable, oldValue, newValue) ->
                slaveDeviceConfigModel.setLegacyI2CDev(newValue));

        hasStaticAddressController.getText().addListener((observable, oldValue, newValue) ->
                slaveDeviceConfigModel.setHasStaticAddress(newValue));

        staticAddressController.getText().addListener((observable, oldValue, newValue) ->
                slaveDeviceConfigModel.setStaticAddress(newValue));

        dynamicAddressController.getText().addListener((observable, oldValue, newValue) ->
                slaveDeviceConfigModel.setDynamicAddress(newValue));

        isIbiDeviceController.getText().addListener((observable, oldValue, newValue) ->
          {
            ibiPayloadSizeController.setDisabled(!StringUtils.equals(newValue, Constants.IBI_DEVICE_ENABLED));
            ibiPayloadSpeedLimitController.setDisabled(!StringUtils.equals(newValue, Constants.IBI_DEVICE_ENABLED));
            slaveDeviceConfigModel.setIsIbiDevice(newValue);
          });

        ibiPayloadSizeController.getText().addListener((observable, oldValue, newValue) ->
                slaveDeviceConfigModel.setIbiPayloadSize(newValue));

        ibiPayloadSpeedLimitController.getText().addListener((observable, oldValue, newValue) ->
                slaveDeviceConfigModel.setIbiPayloadSpeedLimit(newValue));

        devRoleController.getCmbInfoItem().addListener((observable, oldValue, newValue) ->{
                slaveDeviceConfigModel.setDevRole(newValue);
        });
    }

    /**
     * Method to get the {@link DeviceConfiguration} object from the entered values
     *
     * @return {@link DeviceConfiguration}
     */
    public DeviceConfiguration getDeviceConfiguration() {
      DeviceConfiguration deviceConfiguration = new DeviceConfiguration(slaveDeviceConfigModel.getDeviceName());
      List<Param> params = new ArrayList<>();
      params.add(new Param(Constants.I2CFM_PLUS_SPEED, slaveDeviceConfigModel.getI2cFmPlusSpeed()));
      params.add(new Param(Constants.I2C10B_ADDRESS, slaveDeviceConfigModel.getI2c10bAddr()));
      params.add(new Param(Constants.HDR_CAPABLE, slaveDeviceConfigModel.getHdrCapable()));
      params.add(new Param(Constants.LEGACY_I2C_DEV, slaveDeviceConfigModel.getLegacyI2CDev()));
      params.add(new Param(Constants.HAS_STATIC_ADDRESS, slaveDeviceConfigModel.getHasStaticAddress()));
      params.add(new Param(Constants.STATIC_ADDRESS, slaveDeviceConfigModel.getStaticAddress()));
      params.add(new Param(Constants.DYNAMIC_ADDRESS, slaveDeviceConfigModel.getDynamicAddress()));
      params.add(new Param(Constants.IS_IBI_DEVICE, slaveDeviceConfigModel.getIsIbiDevice()));
      params.add(new Param(Constants.DEV_ROLE, slaveDeviceConfigModel.getDevRole()));
      if (StringUtils.equals(slaveDeviceConfigModel.getIsIbiDevice(), Constants.IBI_DEVICE_ENABLED)) {
        params.add(new Param(Constants.IBI_PAYLOAD_SIZE, slaveDeviceConfigModel.getIbiPayloadSize()));
        params.add(new Param(Constants.IBI_PAYLOAD_SPEED_LIMIT, slaveDeviceConfigModel.getIbiPayloadSpeedLimit()));
      }

      deviceConfiguration.setParams(params);
      return deviceConfiguration;
    }

    /**
     * Method to set the {@link DeviceConfiguration} object to the {@link SlaveDeviceConfigModel}
     * @param deviceConfiguration reference object to set it to the SlaveDeviceConfigModel
     */
    public void setDeviceConfiguration(DeviceConfiguration deviceConfiguration) {
      String deviceName = deviceConfiguration.getName();
      slaveDeviceConfigModel.setDeviceName(deviceName);
      deviceNameController.setText(deviceName);
      for (Param param : deviceConfiguration.getParams()) {
        switch (param.getName()) {
          case Constants.I2CFM_PLUS_SPEED:
            slaveDeviceConfigModel.setI2cFmPlusSpeed(param.getValue());
            i2cFmPlusSpeedController.setText(param.getValue());
            break;
          case Constants.I2C10B_ADDRESS:
            slaveDeviceConfigModel.setI2c10bAddr(param.getValue());
            addressController.setText(param.getValue());
            break;
          case Constants.HDR_CAPABLE:
            slaveDeviceConfigModel.setHdrCapable(param.getValue());
            hdrCapableController.setText(param.getValue());
            break;
          case Constants.LEGACY_I2C_DEV:
            slaveDeviceConfigModel.setLegacyI2CDev(param.getValue());
            legacyI2CDevController.setText(param.getValue());
            break;
          case Constants.HAS_STATIC_ADDRESS:
            slaveDeviceConfigModel.setHasStaticAddress(param.getValue());
            hasStaticAddressController.setText(param.getValue());
            break;
          case Constants.STATIC_ADDRESS:
            slaveDeviceConfigModel.setStaticAddress(param.getValue());
            staticAddressController.setText(param.getValue());
            break;
          case Constants.DYNAMIC_ADDRESS:
            slaveDeviceConfigModel.setDynamicAddress(param.getValue());
            dynamicAddressController.setText(param.getValue());
            break;
          case Constants.IS_IBI_DEVICE:
            slaveDeviceConfigModel.setIsIbiDevice(param.getValue());
            isIbiDeviceController.setText(param.getValue());
            break;
          case Constants.DEV_ROLE:
            slaveDeviceConfigModel.setDevRole(param.getValue());
            devRoleController.setSelectedMode(param.getValue());
            break;
          case Constants.IBI_PAYLOAD_SIZE:
            slaveDeviceConfigModel.setIbiPayloadSize(param.getValue());
            ibiPayloadSizeController.setText(param.getValue());
            break;
          case Constants.IBI_PAYLOAD_SPEED_LIMIT:
            slaveDeviceConfigModel.setIbiPayloadSpeedLimit(param.getValue());
            ibiPayloadSpeedLimitController.setText(param.getValue());
            break;
          default:
            break;
        }
      }
    }
}
