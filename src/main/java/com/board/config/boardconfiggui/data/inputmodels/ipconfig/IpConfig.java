package com.board.config.boardconfiggui.data.inputmodels.ipconfig;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;
import java.util.Objects;

@XmlRootElement(name = "root")
public class IpConfig {
    @XmlElement(name = "IP")
    List<Ip> ipList;

    public List<Ip> getIpList() {
        return ipList;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        IpConfig ipConfig = (IpConfig) o;
        return Objects.equals(ipList, ipConfig.ipList);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(ipList);
    }

    @Override
    public String toString() {
        return "Ips{" +
                "ipList=" + ipList +
                '}';
    }
}
