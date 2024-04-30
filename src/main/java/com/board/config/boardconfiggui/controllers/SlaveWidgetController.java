package com.board.config.boardconfiggui.controllers;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;

import java.net.URL;
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

    public void setSlaveWidgetObject() {
        //TODO: need to get the object from maim class and update the UI
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

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

        String[] week_days = {"Monday", "Tuesday", "Wednesday", "Thursday", "Friday"};
        devRoleController.setCmbInfo(FXCollections.observableArrayList(week_days));

        // listening the value from fields
        i2cFmPlusSpeedController.getTxtInformation().addListener((observable, oldValue, newValue) -> System.out.println(newValue));
        addressController.getTxtInformation().addListener((observable, oldValue, newValue) -> System.out.println(newValue));
        hdrCapableController.getTxtInformation().addListener((observable, oldValue, newValue) -> System.out.println(newValue));
        legacyI2CDevController.getTxtInformation().addListener((observable, oldValue, newValue) -> System.out.println(newValue));
        hasStaticAddressController.getTxtInformation().addListener((observable, oldValue, newValue) -> System.out.println(newValue));
        staticAddressController.getTxtInformation().addListener((observable, oldValue, newValue) -> System.out.println(newValue));
        dynamicAddressController.getTxtInformation().addListener((observable, oldValue, newValue) -> System.out.println(newValue));
        isIbiDeviceController.getTxtInformation().addListener((observable, oldValue, newValue) -> System.out.println(newValue));
        ibiPayloadSizeController.getTxtInformation().addListener((observable, oldValue, newValue) -> System.out.println(newValue));
        ibiPayloadSpeedLimitController.getTxtInformation().addListener((observable, oldValue, newValue) -> System.out.println(newValue));

        devRoleController.getCmbInfoItem().addListener((observable, oldValue, newValue) -> System.out.println(newValue));
    }

    public boolean isValidSlaveModel() {


        //TODo: need to check the saved Slave model if any values are null or not.
        return false;
    }
}
