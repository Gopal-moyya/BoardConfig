package com.board.config.boardconfiggui.data.inputmodels.pinconfig;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "port")
public class Port {
    private String name;
    private List<Pin> pinList;

    @XmlAttribute(name = "name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @XmlElement(name = "pin")
    public List<Pin> getPinList() {
        return pinList;
    }

    public void setPinList(List<Pin> pinList) {
        this.pinList = pinList;
    }

    @Override
    public String toString() {
        return "Port{" +
                "name='" + name + '\'' +
                ", pinList=" + pinList +
                '}';
    }
}

