package com.board.config.boardconfiggui.data.outputmodels.ipconfig;

import javax.xml.bind.annotation.*;
import java.util.List;
import java.util.Objects;

@XmlRootElement(name = "ipConfig")
@XmlAccessorType(XmlAccessType.FIELD)
public class IpConfig {

    @XmlAttribute(name = "Name")
    String name;

    @XmlElement(name = "IP")
    List<IpConfigIp> ips;

    public IpConfig() {
    }

    public IpConfig(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public List<IpConfigIp> getIps() {
        return ips;
    }

    public void setIps(List<IpConfigIp> ips) {
        this.ips = ips;
    }

    @Override
    public String toString() {
        return "IpConfig{" +
                "name='" + name + '\'' +
                ", IP=" + ips +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        IpConfig ipConfig = (IpConfig) o;
        return Objects.equals(name, ipConfig.name) && Objects.equals(ips, ipConfig.ips);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, ips);
    }
}
