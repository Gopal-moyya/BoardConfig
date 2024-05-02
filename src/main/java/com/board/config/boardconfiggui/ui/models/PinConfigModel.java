package com.board.config.boardconfiggui.ui.models;

import java.util.Objects;
import java.util.stream.Stream;

public class PinConfigModel {
    private final String portName;
    private final String pinNumber;
    private String selectedMode;
    private String selectedValue;
    private boolean pinStatus;

    public PinConfigModel(String portName, String pinName) {
        this.portName = portName;
        this.pinNumber = pinName;
    }


    public String getPortName() {
        return portName;
    }

    public String getPinNumber() {
        return pinNumber;
    }

    public String getSelectedMode() {
        return selectedMode;
    }

    public void setSelectedMode(String selectedMode) {
        this.selectedMode = selectedMode;
    }

    public String getSelectedValue() {
        return selectedValue;
    }

    public void setSelectedValue(String selectedValue) {
        this.selectedValue = selectedValue;
    }

    public boolean isPinStatus() {
        return pinStatus;
    }

    public void setPinStatus(boolean pinStatus) {
        this.pinStatus = pinStatus;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PinConfigModel that)) return false;
        return pinStatus == that.pinStatus && Objects.equals(portName, that.portName) && Objects.equals(pinNumber, that.pinNumber) && Objects.equals(selectedMode, that.selectedMode) && Objects.equals(selectedValue, that.selectedValue);
    }

    @Override
    public int hashCode() {
        return Objects.hash(portName, pinNumber, selectedMode, selectedValue, pinStatus);
    }

    @Override
    public String toString() {
        return "PinConfigModel{" +
                "portName='" + portName + '\'' +
                ", pinNumber='" + pinNumber + '\'' +
                ", selectedMode='" + selectedMode + '\'' +
                ", selectedValue='" + selectedValue + '\'' +
                ", pinStatus=" + pinStatus +
                '}';
    }
}
