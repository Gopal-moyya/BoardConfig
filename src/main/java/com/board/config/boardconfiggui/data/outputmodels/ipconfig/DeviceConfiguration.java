package com.board.config.boardconfiggui.data.outputmodels.ipconfig;

import com.board.config.boardconfiggui.data.outputmodels.Param;

import javax.xml.bind.annotation.*;
import java.util.List;
import java.util.Objects;

@XmlRootElement(name = "deviceConfiguration")
@XmlAccessorType(XmlAccessType.FIELD)
public class DeviceConfiguration {

    @XmlAttribute(name = "Name")
    String name;

    @XmlElement(name = "param")
    List<Param> params;

    public DeviceConfiguration() {
    }

    public DeviceConfiguration(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public List<Param> getParams() {
        return params;
    }

    public void setParams(List<Param> params) {
        this.params = params;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DeviceConfiguration that = (DeviceConfiguration) o;
        return Objects.equals(name, that.name) && Objects.equals(params, that.params);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, params);
    }
}
