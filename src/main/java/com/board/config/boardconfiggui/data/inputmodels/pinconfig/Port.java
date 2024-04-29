package com.board.config.boardconfiggui.data.inputmodels.pinconfig;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

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
    public String toString() {
        return "Port{" +
                "name='" + name + '\'' +
                ", pinList=" + pinList +
                '}';
    }
}

