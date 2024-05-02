package com.board.config.boardconfiggui.ui.models;

import com.board.config.boardconfiggui.data.outputmodels.ipconfig.SignalParam;

import java.util.List;
import java.util.Objects;

/**
 * Represents an IP config UI model.
 */
public class IpConfigModel {
  String sclPin;
  String sdaPin;
  String sysClock;
  String i2cFreq;
  String sdrFreq;
  String noOfSlaves;
  List<SlaveDeviceConfigModel> slaveDeviceConfigModelList;

  public IpConfigModel() {
  }

  public String getSclPin() {
    return sclPin;
  }

  public void setSclPin(String sclPin) {
    this.sclPin = sclPin;
  }

  public String getSdaPin() {
    return sdaPin;
  }

  public void setSdaPin(String sdaPin) {
    this.sdaPin = sdaPin;
  }

  public String getSysClock() {
    return sysClock;
  }

  public void setSysClock(String sysClock) {
    this.sysClock = sysClock;
  }

  public String getI2cFreq() {
    return i2cFreq;
  }

  public void setI2cFreq(String i2cFreq) {
    this.i2cFreq = i2cFreq;
  }

  public String getSdrFreq() {
    return sdrFreq;
  }

  public void setSdrFreq(String sdrFreq) {
    this.sdrFreq = sdrFreq;
  }

  public String getNoOfSlaves() {
    return noOfSlaves;
  }

  public void setNoOfSlaves(String noOfSlaves) {
    this.noOfSlaves = noOfSlaves;
  }

  public List<SlaveDeviceConfigModel> getSlaveDeviceConfigModelList() {
    return slaveDeviceConfigModelList;
  }

  public void setSlaveDeviceConfigModelList(List<SlaveDeviceConfigModel> slaveDeviceConfigModelList) {
    this.slaveDeviceConfigModelList = slaveDeviceConfigModelList;
  }

  public String getSCLPortName() {
    if (Objects.isNull(sclPin)) {
      return "";
    }
    return getSdaPin().split(" Pin: ")[0];
  }

  public String getSCLPinName() {
    if (Objects.isNull(sclPin)) {
      return "";
    }
    return getSdaPin().split(" Pin: ")[1];
  }

  public String getSDAPortName() {
    if (Objects.isNull(sdaPin)) {
      return "";
    }
    return getSdaPin().split(" Pin: ")[0];
  }

  public String getSDAPinName() {
    if (Objects.isNull(sdaPin)) {
      return "";
    }
    return getSdaPin().split(" Pin: ")[1];
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    IpConfigModel that = (IpConfigModel) o;
    return Objects.equals(sclPin, that.sclPin) &&
            Objects.equals(sdaPin, that.sdaPin) &&
            Objects.equals(sysClock, that.sysClock) &&
            Objects.equals(i2cFreq, that.i2cFreq) &&
            Objects.equals(sdrFreq, that.sdrFreq) &&
            Objects.equals(noOfSlaves, that.noOfSlaves) &&
            Objects.equals(slaveDeviceConfigModelList, that.slaveDeviceConfigModelList) ;
  }

  @Override
  public int hashCode() {
    return Objects.hash(sclPin, sdaPin, sysClock, i2cFreq, sdrFreq, noOfSlaves);
  }

  @Override
  public String toString() {
    return "IpConfigModel{" +
      "sclPin='" + sclPin + '\'' +
      ", sdaPin='" + sdaPin + '\'' +
      ", sysClock='" + sysClock + '\'' +
      ", i2cFreq='" + i2cFreq + '\'' +
      ", sdrFreq='" + sdrFreq + '\'' +
      ", noOfSlaves='" + noOfSlaves + '\'' +
      ", slaveDeviceConfigModelList=" + slaveDeviceConfigModelList +
      '}';
  }

}
