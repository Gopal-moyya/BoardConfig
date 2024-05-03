package com.board.config.boardconfiggui.data.outputmodels.clockconfig;

import javax.xml.bind.annotation.*;
import java.util.List;
import java.util.Objects;

@XmlRootElement(name = "clockConfig")
@XmlAccessorType(XmlAccessType.FIELD)
public class ClockConfig {

    @XmlElement(name = "configParam")
    List<ClockConfigParam> configParams;

    public List<ClockConfigParam> getConfigParams() {
        return configParams;
    }

    public void setConfigParams(List<ClockConfigParam> configParams) {
        this.configParams = configParams;
    }

    @Override
    public String toString() {
        return "ClockConfig{" +
                "configParam=" + configParams +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ClockConfig that = (ClockConfig) o;
        return Objects.equals(configParams, that.configParams);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(configParams);
    }
}
