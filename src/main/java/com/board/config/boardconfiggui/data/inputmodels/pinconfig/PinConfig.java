package com.board.config.boardconfiggui.data.inputmodels.pinconfig;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "root")
public class PinConfig {
    private String chipltetName;
    private Ports ports;

    @XmlAttribute(name = "chipltetName")
    public String getChipltetName() {
        return chipltetName;
    }

    public void setChipltetName(String chipltetName) {
        this.chipltetName = chipltetName;
    }

    @XmlElement(name = "ports")
    public Ports getPorts() {
        return ports;
    }

    public void setPorts(Ports ports) {
        this.ports = ports;
    }

    @Override
    public String toString() {
        return "PinConfig{" +
                "chipltetName='" + chipltetName + '\'' +
                ", ports=" + ports +
                '}';
    }
}
