package com.board.config.boardconfiggui.data.inputmodels.ipconfig;

import com.board.config.boardconfiggui.data.enums.DeviceRole;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "Instance")
public class Instance {
    private String name;
    private List<Parameter> parameters;

    @XmlAttribute(name = "name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @XmlElement(name = "param")
    public List<Parameter> getParameters() {
        return parameters;
    }

    public void setParameters(List<Parameter> parameters) {
        this.parameters = parameters;
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
    public String toString() {
        return "Instance{" +
                "name='" + name + '\'' +
                ", parameters=" + parameters +
                '}';
    }
}
