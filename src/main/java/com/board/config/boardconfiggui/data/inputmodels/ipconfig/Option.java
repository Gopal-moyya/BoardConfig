package com.board.config.boardconfiggui.data.inputmodels.ipconfig;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.Objects;

/**
 * POJO class holds the information related to the parameter
 */
@XmlRootElement(name = "Option")
@XmlAccessorType(XmlAccessType.FIELD)
public class Option {

    @XmlAttribute(name = "name")
    String name;

    @XmlAttribute(name = "value", required = false)
    String value;

    public Option() {
    }

    public Option(String name) {
        this.name = name;
    }

    public Option(String name, String value) {
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
        Option param = (Option) o;
        return Objects.equals(value, param.value) && Objects.equals(name, param.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, value);
    }
}
