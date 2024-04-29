package com.board.config.boardconfiggui.data.inputmodels.pinconfig;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

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
    public String toString() {
        return "PinConfig{" +
                "chipltetName='" + chipltetName + '\'' +
                ", ports=" + ports +
                '}';
    }
}
