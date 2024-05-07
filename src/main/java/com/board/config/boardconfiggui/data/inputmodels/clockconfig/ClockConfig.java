package com.board.config.boardconfiggui.data.inputmodels.clockconfig;


import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;
import java.util.Objects;

/**
 * Represents a Clock configuration.
 */
@XmlRootElement(name = "clockConfig")
public class ClockConfig {

    @XmlElement(name = "configParam")
    List<ClockConfigParameter> clockConfigParams;

    public List<ClockConfigParameter> getClockConfigParams() {
        return clockConfigParams;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ClockConfig that = (ClockConfig) o;
        return Objects.equals(clockConfigParams, that.clockConfigParams);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(clockConfigParams);
    }

    @Override
    public String toString() {
        return "ClockConfig{" +
                "clockConfigParams=" + clockConfigParams + '}';
    }
}
