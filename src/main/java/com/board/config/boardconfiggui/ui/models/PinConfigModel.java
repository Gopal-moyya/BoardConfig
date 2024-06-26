package com.board.config.boardconfiggui.ui.models;

import java.util.Objects;

public class PinConfigModel {
    private final String portName;
    private final String pinNumber;
    private String selectedMode;
    private String selectedIOType;
    private String selectedInputType;
    private String selectedOutputType;
    private String selectedExioType;
    private String selectedLevelType;
    private String selectedEdgeType;
    private String selectedBypassType;
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

    public String getSelectedBypassType() {
        return selectedBypassType;
    }

    public String getSelectedIp() {
        return selectedBypassType.split("_")[0];
    }

    public void setSelectedBypassType(String selectedValue) {
        this.selectedBypassType = selectedValue;
    }

    public boolean isPinStatus() {
        return pinStatus;
    }

    public void setPinStatus(boolean pinStatus) {
        this.pinStatus = pinStatus;
    }

    public String getSelectedIOType() {
        return selectedIOType;
    }

    public void setSelectedIOType(String selectedIOType) {
        this.selectedIOType = selectedIOType;
    }

    public String getSelectedInputType() {
        return selectedInputType;
    }

    public void setSelectedInputType(String selectedInputType) {
        this.selectedInputType = selectedInputType;
    }

    public String getSelectedOutputType() {
        return selectedOutputType;
    }

    public void setSelectedOutputType(String selectedOutputType) {
        this.selectedOutputType = selectedOutputType;
    }

    public String getSelectedExioType() {
        return selectedExioType;
    }

    public void setSelectedExioType(String selectedExioType) {
        this.selectedExioType = selectedExioType;
    }

    public String getSelectedLevelType() {
        return selectedLevelType;
    }

    public void setSelectedLevelType(String selectedLevelType) {
        this.selectedLevelType = selectedLevelType;
    }

    public String getSelectedEdgeType() {
        return selectedEdgeType;
    }

    public void setSelectedEdgeType(String selectedEdgeType) {
        this.selectedEdgeType = selectedEdgeType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PinConfigModel that)) return false;
        return pinStatus == that.pinStatus && Objects.equals(portName, that.portName) && Objects.equals(pinNumber, that.pinNumber) && Objects.equals(selectedMode, that.selectedMode) && Objects.equals(selectedIOType, that.selectedIOType) && Objects.equals(selectedInputType, that.selectedInputType) && Objects.equals(selectedOutputType, that.selectedOutputType) && Objects.equals(selectedExioType, that.selectedExioType) && Objects.equals(selectedLevelType, that.selectedLevelType) && Objects.equals(selectedEdgeType, that.selectedEdgeType);
    }

    @Override
    public int hashCode() {
        return Objects.hash(portName, pinNumber, selectedMode, selectedIOType, selectedInputType, selectedOutputType, selectedExioType, selectedLevelType, selectedEdgeType, pinStatus);
    }

    @Override
    public String toString() {
        return "PinConfigModel{" +
                "portName='" + portName + '\'' +
                ", pinNumber='" + pinNumber + '\'' +
                ", selectedMode='" + selectedMode + '\'' +
                ", selectedIOType='" + selectedIOType + '\'' +
                ", selectedInputType='" + selectedInputType + '\'' +
                ", selectedOutputType='" + selectedOutputType + '\'' +
                ", selectedExioType='" + selectedExioType + '\'' +
                ", selectedLevelType='" + selectedLevelType + '\'' +
                ", selectedEdgeType='" + selectedEdgeType + '\'' +
                ", pinStatus=" + pinStatus +
                '}';
    }
}
