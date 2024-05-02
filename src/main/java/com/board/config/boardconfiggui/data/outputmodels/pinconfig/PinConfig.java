package com.board.config.boardconfiggui.data.outputmodels.pinconfig;

import com.sun.istack.NotNull;
import org.apache.commons.collections.CollectionUtils;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
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

    public void addPort(PinConfigPort pinConfigPort) {
        if (Objects.isNull(ports)) {
            ports = new ArrayList<>();
        }
        ports.add(pinConfigPort);
    }

    public void removePort(PinConfigPort pinConfigPort) {
        if (Objects.isNull(pinConfigPort)) {
            return;
        }
        ports.remove(pinConfigPort);
    }


    public void setPorts(List<PinConfigPort> ports) {
        this.ports = ports;
    }

    public PinConfigParam getPinConfigParamsData(String portNumber, int pinNumber) {
        if (ports != null) {
            for (PinConfigPort pinConfigPort : ports) {
                if (portNumber.equals(pinConfigPort.name)) {
                    return pinConfigPort.getPinConfigParamData(pinNumber);
                }
            }
        }
        return null;
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

    public void savePinConfig(String portNumber, int pinNumber, @NotNull PinConfigParam pinConfigParam) {

        if (CollectionUtils.isEmpty(ports)) {
            PinConfigPort pinConfigPort1 = new PinConfigPort(portNumber);
            pinConfigPort1.setPinConfigParamData(pinNumber, pinConfigParam);
            addPort(pinConfigPort1);
        } else {
            PinConfigPort pinConfigPort = ports.stream()
                    .filter(x -> x.getName().equals(portNumber))
                    .findFirst()
                    .orElse(null);
            if (Objects.isNull(pinConfigPort)) {
                PinConfigPort pinConfigPort1 = new PinConfigPort(portNumber);
                pinConfigPort1.setPinConfigParamData(pinNumber, pinConfigParam);
                addPort(pinConfigPort1);
            } else {
                if (portNumber.equals(pinConfigPort.name)) {
                    pinConfigPort.setPinConfigParamData(pinNumber, pinConfigParam);
                }
            }

        }
    }

    public void removePinConfig(String portNumber, String pinNumber) {

        if (CollectionUtils.isNotEmpty(ports)) {
            PinConfigPort pinConfigPort = ports.stream()
                    .filter(x -> x.getName().equals(portNumber))
                    .findFirst()
                    .orElse(null);
            if (Objects.nonNull(pinConfigPort)) {
                pinConfigPort.removePinConfigParamData(pinNumber);
            }

        }
    }
}
