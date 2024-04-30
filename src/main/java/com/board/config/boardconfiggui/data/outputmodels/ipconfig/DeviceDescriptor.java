package com.board.config.boardconfiggui.data.outputmodels.ipconfig;

import javax.xml.bind.annotation.*;
import java.util.List;
import java.util.Objects;

@XmlRootElement(name = "deviceDescriptor")
@XmlAccessorType(XmlAccessType.FIELD)
public class DeviceDescriptor {

    @XmlAttribute(name = "Name")
    String name;

    @XmlElement(name = "deviceConfiguration")
    List<DeviceConfiguration> deviceConfigurations;

    public DeviceDescriptor() {
    }

    public DeviceDescriptor(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public List<DeviceConfiguration> getDeviceConfigurations() {
        return deviceConfigurations;
    }

    public void setDeviceConfigurations(List<DeviceConfiguration> deviceConfigurations) {
        this.deviceConfigurations = deviceConfigurations;
    }

    @Override
    public String toString() {
        return "DeviceDescriptor{" +
                "name='" + name + '\'' +
                ", deviceConfiguration=" + deviceConfigurations +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DeviceDescriptor that = (DeviceDescriptor) o;
        return Objects.equals(name, that.name) && Objects.equals(deviceConfigurations, that.deviceConfigurations);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, deviceConfigurations);
    }
}
