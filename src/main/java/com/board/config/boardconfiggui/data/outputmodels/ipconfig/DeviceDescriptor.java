package com.board.config.boardconfiggui.data.outputmodels.ipconfig;

import com.board.config.boardconfiggui.data.Constants;

import javax.xml.bind.annotation.*;
import java.util.List;
import java.util.Objects;

@XmlRootElement(name = "deviceDescriptor")
@XmlAccessorType(XmlAccessType.FIELD)
public class DeviceDescriptor {

    @XmlAttribute(name = "Name")
    final static String name = Constants.DEVICE_DESCRIPTOR_NAME;

    @XmlElement(name = "deviceConfiguration")
    List<DeviceConfiguration> deviceConfigurations;

    public DeviceDescriptor() {
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
                ", deviceConfiguration=" + deviceConfigurations +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DeviceDescriptor that = (DeviceDescriptor) o;
        return Objects.equals(deviceConfigurations, that.deviceConfigurations);
    }

    @Override
    public int hashCode() {
        return Objects.hash(deviceConfigurations);
    }
}
