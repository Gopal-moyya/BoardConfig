package com.board.config.boardconfiggui.data.outputmodels.pinconfig;

import com.board.config.boardconfiggui.data.outputmodels.BooleanAdapter;

import javax.xml.bind.annotation.*;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.util.Objects;

@XmlRootElement(name = "configParam")
@XmlAccessorType(XmlAccessType.FIELD)
public class PinConfigParam {

    @XmlAttribute(name = "Pin")
    String pin;

    @XmlAttribute(name = "ByPassMode")
    @XmlJavaTypeAdapter(BooleanAdapter.class)
    Boolean byPassMode;

    @XmlAttribute(name = "Direction")
    String direction;

    @XmlAttribute(name = "Value")
    String value;

    @XmlAttribute(name = "IntEnable")
    @XmlJavaTypeAdapter(BooleanAdapter.class)
    Boolean intEnable;

    @XmlAttribute(name = "IntType")
    String intType;

    @XmlAttribute(name = "IntValue")
    String intValue;

    public PinConfigParam() {
    }

    public PinConfigParam(String pin) {
        this.pin = pin;
    }

    public String getPin() {
        return pin;
    }

    public Boolean isByPassMode() {
        return byPassMode;
    }

    public void setByPassMode(Boolean byPassMode) {
        this.byPassMode = byPassMode;
    }

    public String getDirection() {
        return direction;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public Boolean isIntEnable() {
        return intEnable;
    }

    public void setIntEnable(Boolean intEnable) {
        this.intEnable = intEnable;
    }

    public String getIntType() {
        return intType;
    }

    public void setIntType(String intType) {
        this.intType = intType;
    }

    public String getIntValue() {
        return intValue;
    }

    public void setIntValue(String intValue) {
        this.intValue = intValue;
    }

    @Override
    public String toString() {
        return "PinConfigParam{" +
                "pin='" + pin + '\'' +
                ", byPassMode=" + byPassMode +
                ", direction='" + direction + '\'' +
                ", value='" + value + '\'' +
                ", intEnable=" + intEnable +
                ", intType='" + intType + '\'' +
                ", intValue='" + intValue + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PinConfigParam that = (PinConfigParam) o;
        return Objects.equals(pin, that.pin) && byPassMode == that.byPassMode && intEnable == that.intEnable && Objects.equals(direction, that.direction) && Objects.equals(value, that.value) && Objects.equals(intType, that.intType) && Objects.equals(intValue, that.intValue);
    }

    @Override
    public int hashCode() {
        return Objects.hash(pin, byPassMode, direction, value, intEnable, intType, intValue);
    }
}
