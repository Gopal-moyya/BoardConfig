package com.board.config.boardconfiggui.data.outputmodels.pinconfig;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import javax.xml.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@XmlRootElement(name = "Port")
@XmlAccessorType(XmlAccessType.FIELD)
public class PinConfigPort {

    @XmlAttribute(name = "Name")
    String name;

    @XmlElement(name = "configParam")
    List<PinConfigParam> configParams;

    public void addPinConfigParam(PinConfigParam pinConfigParam) {
        if (Objects.isNull(configParams)) {
            configParams = new ArrayList<>();
        }
        configParams.add(pinConfigParam);
    }


    public void removePinConfigParam(PinConfigParam pinConfigParam) {
        if (Objects.isNull(configParams)) {
            return;
        }
        configParams.remove(pinConfigParam);
    }

    public PinConfigPort() {
    }

    public PinConfigPort(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public List<PinConfigParam> getConfigParams() {
        return configParams;
    }

    public void setConfigParams(List<PinConfigParam> configParams) {
        this.configParams = configParams;
    }

    public PinConfigParam getPinConfigParamData(String pinNumber) {
        if (configParams != null) {
            for (PinConfigParam pinConfigParam : configParams) {
                if (StringUtils.equals(pinConfigParam.pin, pinNumber)) {
                    return pinConfigParam;
                }
            }
        }
        return null;
    }

    public void setPinConfigParamData(String pinNumber, PinConfigParam pinConfigParam) {

        if (CollectionUtils.isEmpty(configParams)) {
            addPinConfigParam(pinConfigParam);
        } else {
            PinConfigParam pinConfigParam1 = configParams.stream()
                    .filter(x -> StringUtils.equals(x.getPin(), pinNumber))
                    .findFirst()
                    .orElse(null);

            if (Objects.isNull(pinConfigParam1)) {
                addPinConfigParam(pinConfigParam);
            } else {
                removePinConfigParam(pinConfigParam1);
                addPinConfigParam(pinConfigParam);

            }
        }
    }

    public void removePinConfigParamData(String pinNumber) {

        if (CollectionUtils.isNotEmpty(configParams)) {
            PinConfigParam pinConfigParam = configParams.stream()
                    .filter(x -> StringUtils.equals(x.getPin(), pinNumber))
                    .findFirst()
                    .orElse(null);
            if (Objects.nonNull(pinConfigParam)) {
                removePinConfigParam(pinConfigParam);
            }
        }
    }

    @Override
    public String toString() {
        return "PinConfigPort{" +
                "name='" + name + '\'' +
                ", configParam=" + configParams +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PinConfigPort that = (PinConfigPort) o;
        return Objects.equals(name, that.name) && Objects.equals(configParams, that.configParams);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, configParams);
    }
}
