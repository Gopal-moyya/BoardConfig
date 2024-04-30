package com.board.config.boardconfiggui.data.outputmodels.connectivityconfig;

import javax.xml.bind.annotation.*;
import java.util.List;
import java.util.Objects;

@XmlRootElement(name = "connectivityConfig")
@XmlAccessorType(XmlAccessType.FIELD)
public class ConnectivityConfig {
    
    @XmlElement(name="ip")
    List<ConnectivityIp> ip;

    @XmlAttribute(name = "numberOfIps")
    int numberOfIps;

    public ConnectivityConfig() {
    }

    public ConnectivityConfig(List<ConnectivityIp> ip, int numberOfIps) {
        this.ip = ip;
        this.numberOfIps = numberOfIps;
    }

    public List<ConnectivityIp> getIp() {
        return ip;
    }

    public int getNumberOfIps() {
        return numberOfIps;
    }

    @Override
    public String toString() {
        return "ConnectivityConfig{" +
                "ip=" + ip +
                ", numberOfIps=" + numberOfIps +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ConnectivityConfig that = (ConnectivityConfig) o;
        return numberOfIps == that.numberOfIps && Objects.equals(ip, that.ip);
    }

    @Override
    public int hashCode() {
        return Objects.hash(ip, numberOfIps);
    }
}
