package com.board.config.boardconfiggui.data.outputmodels.ipconfig;

import javax.xml.bind.annotation.*;
import java.util.List;
import java.util.Objects;

@XmlRootElement(name = "Port")
@XmlAccessorType(XmlAccessType.FIELD)
public class IpConfigPort {

    @XmlAttribute(name = "Name")
    String name;

    @XmlElement(name = "signalParam")
    List<SingnalParam> signalParams;

    public IpConfigPort() {
    }

    public IpConfigPort(String name) {
        this.name = name;
    }

    public String getName() { return name;}

    public List<SingnalParam> getSignalParams() {
        return signalParams;
    }

    public void setSignalParams(List<SingnalParam> signalParams) {
        this.signalParams = signalParams;
    }

    @Override
    public String toString() {
        return "Port{" +
                "name='" + name + '\'' +
                ", signalParam=" + signalParams +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        IpConfigPort port = (IpConfigPort) o;
        return Objects.equals(name, port.name) && Objects.equals(signalParams, port.signalParams);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, signalParams);
    }
}
