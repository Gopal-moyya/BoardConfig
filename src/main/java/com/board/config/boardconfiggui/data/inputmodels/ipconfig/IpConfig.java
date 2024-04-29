package com.board.config.boardconfiggui.data.inputmodels.ipconfig;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "root")
public class IpConfig {
    private List<Ip> ipList;

    @XmlElement(name = "IP")
    public List<Ip> getIpList() {
        return ipList;
    }

    public void setIpList(List<Ip> ipList) {
        this.ipList = ipList;
    }

    @Override
    public String toString() {
        return "Ips{" +
                "ipList=" + ipList +
                '}';
    }
}
