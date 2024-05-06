package com.board.config.boardconfiggui.data;

import java.util.Objects;

/**
 * POJO class used to maintain the IP and PIn configuration
 */
public class IpPinConfig {
    private final String portName;
    private final String pinName;
    private boolean isClock;
    private boolean isDisabled;

    public IpPinConfig(String portName, String pinName) {
        this.portName = portName;
        this.pinName = pinName;
    }

    public String getPortName() {
        return portName;
    }

    public String getPinName() {
        return pinName;
    }

    public boolean isClock() {
        return isClock;
    }

    public void setClock(boolean clock) {
        isClock = clock;
    }

    public boolean isDisabled() {
        return isDisabled;
    }

    public void setDisabled(boolean disabled) {
        isDisabled = disabled;
    }

    public String getDisplayValue() {
        return portName + " Pin: " + pinName;
    }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    IpPinConfig that = (IpPinConfig) o;
    return Objects.equals(portName, that.portName) && Objects.equals(pinName, that.pinName);
  }

  @Override
  public int hashCode() {
    return Objects.hash(portName, pinName);
  }
}
