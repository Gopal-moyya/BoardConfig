package com.board.config.boardconfiggui.data.outputmodels.pinconfig;

import javax.xml.bind.annotation.*;
import java.util.List;
import java.util.Objects;

@XmlRootElement(name = "Port")
@XmlAccessorType(XmlAccessType.FIELD)
public class PinConfigPort {

    @XmlAttribute(name = "Name")
    String name;

    @XmlElement(name = "configParam")
    List<PinConfigParam> configParams;

    public PinConfigPort() {
    }

    public PinConfigPort(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public List<PinConfigParam> getConfigParams() {
        return configParams;
    }

    public void setConfigParams(List<PinConfigParam> configParams) {
        this.configParams = configParams;
    }

    @Override
    public String toString() {
        return "PinConfigPort{" +
                "name='" + name + '\'' +
                ", configParam=" + configParams +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PinConfigPort that = (PinConfigPort) o;
        return Objects.equals(name, that.name) && Objects.equals(configParams, that.configParams);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, configParams);
    }
}
