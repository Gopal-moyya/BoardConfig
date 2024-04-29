package com.board.config.boardconfiggui.data.inputmodels.pinconfig;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "ports")
public class Ports {
    private List<Port> portList;

    @XmlElement(name = "port")
    public List<Port> getPortList() {
        return portList;
    }

    public void setPortList(List<Port> portList) {
        this.portList = portList;
    }

    @Override
    public String toString() {
        return "Ports{" +
                "portList=" + portList +
                '}';
    }
}


