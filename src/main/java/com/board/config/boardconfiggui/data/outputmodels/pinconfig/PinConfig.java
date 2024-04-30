package com.board.config.boardconfiggui.data.outputmodels.pinconfig;

import javax.xml.bind.annotation.*;
import java.util.List;
import java.util.Objects;

@XmlRootElement(name = "pinConfig")
@XmlAccessorType(XmlAccessType.FIELD)
public class PinConfig {

    @XmlElement(name = "Port")
    List<PinConfigPort> ports;

    public List<PinConfigPort> getPorts() {
        return ports;
    }

    public void setPorts(List<PinConfigPort> ports) {
        this.ports = ports;
    }

    @Override
    public String toString() {
        return "PinConfig{" +
                "Port=" + ports +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PinConfig pinConfig = (PinConfig) o;
        return Objects.equals(ports, pinConfig.ports);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(ports);
    }
}
