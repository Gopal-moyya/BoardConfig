package com.board.config.boardconfiggui.data.outputmodels;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.Objects;

@XmlRootElement(name = "param")
@XmlAccessorType(XmlAccessType.FIELD)
public class Param {

    @XmlAttribute(name = "name")
    String name;

    @XmlAttribute(name = "value")
    String value;

    public Param() {
    }

    public Param(String name, String value) {
        this.name = name;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public String getValue() {
        return value;
    }

    @Override
    public String toString() {
        return "Param{" +
                "name='" + name + '\'' +
                ", value=" + value +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Param param = (Param) o;
        return Objects.equals(value, param.value) && Objects.equals(name, param.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, value);
    }
}
