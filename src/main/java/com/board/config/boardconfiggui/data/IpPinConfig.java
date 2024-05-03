package com.board.config.boardconfiggui.data;

/**
 * POJO class used to maintain the IP and PIn configuration
 */
public class IpPinConfig {
    private final String portName;
    private final String pinName;
    private boolean isClock;
    private boolean isEnabled;

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

    public boolean isEnabled() {
        return isEnabled;
    }

    public void setEnabled(boolean enabled) {
        isEnabled = enabled;
    }

    public String getDisplayValue() {
        return portName + " Pin: " + pinName;
    }

}
