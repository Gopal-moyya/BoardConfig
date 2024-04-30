package com.board.config.boardconfiggui.ui.models;

import java.util.Objects;
import java.util.stream.Stream;

public class SlaveDeviceConfigModel {
    private String deviceName;
    private String i2cFmPlusSpeed;
    private String i2c10bAddr;
    private String hdrCapable;
    private String legacyI2CDev;
    private String hasStaticAddress;
    private String staticAddress;
    private String dynamicAddress;
    private String isIbiDevice;
    private String devRole;
    private String ibiPayloadSize;
    private String ibiPayloadSpeedLimit;

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }

    public String getDeviceName() {
        return deviceName;
    }

    public String getI2cFmPlusSpeed() {
        return i2cFmPlusSpeed;
    }

    public void setI2cFmPlusSpeed(String i2cFmPlusSpeed) {
        this.i2cFmPlusSpeed = i2cFmPlusSpeed;
    }

    public String getI2c10bAddr() {
        return i2c10bAddr;
    }

    public void setI2c10bAddr(String i2c10bAddr) {
        this.i2c10bAddr = i2c10bAddr;
    }

    public String getHdrCapable() {
        return hdrCapable;
    }

    public void setHdrCapable(String hdrCapable) {
        this.hdrCapable = hdrCapable;
    }

    public String getLegacyI2CDev() {
        return legacyI2CDev;
    }

    public void setLegacyI2CDev(String legacyI2CDev) {
        this.legacyI2CDev = legacyI2CDev;
    }

    public String getHasStaticAddress() {
        return hasStaticAddress;
    }

    public void setHasStaticAddress(String hasStaticAddress) {
        this.hasStaticAddress = hasStaticAddress;
    }

    public String getStaticAddress() {
        return staticAddress;
    }

    public void setStaticAddress(String staticAddress) {
        this.staticAddress = staticAddress;
    }

    public String getDynamicAddress() {
        return dynamicAddress;
    }

    public void setDynamicAddress(String dynamicAddress) {
        this.dynamicAddress = dynamicAddress;
    }

    public String getIsIbiDevice() {
        return isIbiDevice;
    }

    public void setIsIbiDevice(String isIbiDevice) {
        this.isIbiDevice = isIbiDevice;
    }

    public String getDevRole() {
        return devRole;
    }

    public void setDevRole(String devRole) {
        this.devRole = devRole;
    }

    public String getIbiPayloadSize() {
        return ibiPayloadSize;
    }

    public void setIbiPayloadSize(String ibiPayloadSize) {
        this.ibiPayloadSize = ibiPayloadSize;
    }

    public String getIbiPayloadSpeedLimit() {
        return ibiPayloadSpeedLimit;
    }

    public void setIbiPayloadSpeedLimit(String ibiPayloadSpeedLimit) {
        this.ibiPayloadSpeedLimit = ibiPayloadSpeedLimit;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SlaveDeviceConfigModel that = (SlaveDeviceConfigModel) o;
        return Objects.equals(deviceName, that.deviceName) &&
                Objects.equals(i2cFmPlusSpeed, that.i2cFmPlusSpeed) &&
                Objects.equals(i2c10bAddr, that.i2c10bAddr) &&
                Objects.equals(hdrCapable, that.hdrCapable) &&
                Objects.equals(legacyI2CDev, that.legacyI2CDev) &&
                Objects.equals(hasStaticAddress, that.hasStaticAddress) &&
                Objects.equals(staticAddress, that.staticAddress) &&
                Objects.equals(dynamicAddress, that.dynamicAddress) &&
                Objects.equals(isIbiDevice, that.isIbiDevice) &&
                Objects.equals(devRole, that.devRole) &&
                Objects.equals(ibiPayloadSize, that.ibiPayloadSize) &&
                Objects.equals(ibiPayloadSpeedLimit, that.ibiPayloadSpeedLimit);
    }

    @Override
    public int hashCode() {
        return Objects.hash(deviceName, i2cFmPlusSpeed, i2c10bAddr, hdrCapable,
                legacyI2CDev, hasStaticAddress, staticAddress, dynamicAddress,
                isIbiDevice, devRole, ibiPayloadSize, ibiPayloadSpeedLimit);
    }

    @Override
    public String toString() {
        return "SlaveDeviceConfigModel{" + "deviceName='" + deviceName + '\'' +
                ", i2cFmPlusSpeed='" + i2cFmPlusSpeed + '\'' + ", i2c10bAddr='" +
                i2c10bAddr + '\'' + ", hdrCapable='" + hdrCapable + '\'' + ", legacyI2CDev='" +
                legacyI2CDev + '\'' + ", hasStaticAddress='" + hasStaticAddress + '\'' +
                ", staticAddress='" + staticAddress + '\'' + ", dynamicAddress='" +
                dynamicAddress + '\'' + ", isIbiDevice='" + isIbiDevice + '\'' + ", devRole='" +
                devRole + '\'' + ", ibiPayloadSize='" + ibiPayloadSize + '\'' +
                ", ibiPayloadSpeedLimit='" + ibiPayloadSpeedLimit + '\'' + '}';
    }

    public boolean hasNullFields() {
        return Stream.of(deviceName, i2cFmPlusSpeed, i2c10bAddr, hdrCapable, legacyI2CDev, staticAddress,
                dynamicAddress, isIbiDevice, devRole, ibiPayloadSize, ibiPayloadSpeedLimit).anyMatch(Objects::isNull);
    }
}
