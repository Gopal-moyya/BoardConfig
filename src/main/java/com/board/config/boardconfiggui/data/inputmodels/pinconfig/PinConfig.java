package com.board.config.boardconfiggui.data.inputmodels.pinconfig;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;
import java.util.Objects;

@XmlRootElement(name = "root")
public class PinConfig {
    @XmlAttribute(name = "chipltetName")
    String chipltetName;

    @XmlElementWrapper(name="ports")
    @XmlElement(name = "port")
    List<Port> ports;

    public String getChipltetName() {
        return chipltetName;
    }

    public List<Port> getPorts() {
        return ports;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PinConfig pinConfig = (PinConfig) o;
        return Objects.equals(chipltetName, pinConfig.chipltetName) && Objects.equals(ports, pinConfig.ports);
    }

    @Override
    public int hashCode() {
        return Objects.hash(chipltetName, ports);
    }

    @Override
    public String toString() {
        return "PinConfig{" +
                "chipltetName='" + chipltetName + '\'' +
                ", ports=" + ports +
                '}';
    }
}
