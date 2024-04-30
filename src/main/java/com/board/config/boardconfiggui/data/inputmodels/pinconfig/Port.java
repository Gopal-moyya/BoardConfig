package com.board.config.boardconfiggui.data.inputmodels.pinconfig;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;
import java.util.Objects;

/**
 * Represents a port configuration.
 */
@XmlRootElement(name = "port")
public class Port {
    @XmlAttribute(name = "name")
    String name;
    @XmlElement(name = "pin")
    List<Pin> pinList;

    public String getName() {
        return name;
    }

    public List<Pin> getPinList() {
        return pinList;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Port port = (Port) o;
        return Objects.equals(name, port.name);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(name);
    }

    @Override
    public String toString() {
        return "Port{" +
                "name='" + name + '\'' +
                ", pinList=" + pinList +
                '}';
    }
}

