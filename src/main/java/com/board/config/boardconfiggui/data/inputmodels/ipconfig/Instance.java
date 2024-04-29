package com.board.config.boardconfiggui.data.inputmodels.ipconfig;

import com.board.config.boardconfiggui.data.enums.DeviceRole;

import javax.xml.bind.annotation.*;
import java.util.List;
import java.util.Objects;

@XmlRootElement(name = "Instance")
public class Instance {
    @XmlAttribute(name = "name")
    String name;
    @XmlElement(name = "param")
    List<Parameter> parameters;

    public String getName() {
        return name;
    }

    public List<Parameter> getParameters() {
        return parameters;
    }

    public DeviceRole getDeviceRole() {
        for (Parameter parameter : parameters) {
            if ("devRole".equals(parameter.getName())) {
                return DeviceRole.valueOf(parameter.getValue().toUpperCase());
            }
        }
        // Default to null if devRole parameter is not found
        return null;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Instance instance = (Instance) o;
        return Objects.equals(name, instance.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    @Override
    public String toString() {
        return "Instance{" +
                "name='" + name + '\'' +
                ", parameters=" + parameters +
                '}';
    }
}
