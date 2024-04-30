package com.board.config.boardconfiggui.data.outputmodels.connectivityconfig;

import javax.xml.bind.annotation.*;
import java.util.Objects;

@XmlRootElement(name = "ip")
@XmlAccessorType(XmlAccessType.FIELD)
public class ConnectivityIp {

    @XmlAttribute(name = "Name")
    String name;

    public ConnectivityIp() {
    }

    public ConnectivityIp(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "ConnectivityIp{" +
                "name='" + name + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ConnectivityIp that = (ConnectivityIp) o;
        return Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(name);
    }
}
