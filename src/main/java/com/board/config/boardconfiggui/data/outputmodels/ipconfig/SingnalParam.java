package com.board.config.boardconfiggui.data.outputmodels.ipconfig;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.Objects;

@XmlRootElement(name = "signalParam")
@XmlAccessorType(XmlAccessType.FIELD)
public class SingnalParam {

    @XmlAttribute(name = "Pin")
    String pin;

    @XmlAttribute(name = "Name")
    String name;

    public SingnalParam() {
    }

    public SingnalParam(String pin, String name) {
        this.pin = name;
        this.name = name;
    }

    public String getPin() {
        return pin;
    }

    public String getName() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SingnalParam that = (SingnalParam) o;
        return Objects.equals(pin, that.pin) && Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(pin, name);
    }
}
