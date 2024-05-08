package com.board.config.boardconfiggui.data.outputmodels.ipconfig;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.Objects;

@XmlRootElement(name = "signalParam")
@XmlAccessorType(XmlAccessType.FIELD)
public class SignalParam {

    @XmlAttribute(name = "Pin")
    String pin;

    @XmlAttribute(name = "Name")
    String name;

    public SignalParam() {
    }

    public SignalParam(String pin, String name) {
        this.pin = pin;
        this.name = name;
    }

    public String getPin() {
        return pin;
    }

    public String getName() {
        return name;
    }

    public void setPin(String pin) {
        this.pin = pin;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isClock(){
      return name.split("_")[1].equals("SCL");
    }

    public boolean isData(){
        return name.split("_")[1].equals("SDA");
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SignalParam that = (SignalParam) o;
        return Objects.equals(pin, that.pin) && Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(pin, name);
    }
}
