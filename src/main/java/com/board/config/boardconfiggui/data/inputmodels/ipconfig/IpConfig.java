package com.board.config.boardconfiggui.data.inputmodels.ipconfig;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;
import java.util.Objects;

/**
 * Represents an IP configuration containing a list of IPs.
 */
@XmlRootElement(name = "root")
public class IpConfig {
    @XmlElement(name = "IP")
    List<Ip> ipList;

    @XmlElement(name = "ChipInfo")
    ChipInfo chipInfo;

    public List<Ip> getIpList() {
        return ipList;
    }

    public ChipInfo getChipInfo() {
        return chipInfo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        IpConfig ipConfig = (IpConfig) o;
        return Objects.equals(ipList, ipConfig.ipList) && Objects.equals(chipInfo, ipConfig.chipInfo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(ipList, chipInfo);
    }

    @Override
    public String toString() {
        return "IpConfig{" +
                "ipList=" + ipList +
                ", chipInfo=" + chipInfo +
                '}';
    }
}
