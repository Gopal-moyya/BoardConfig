package com.board.config.boardconfiggui.data.outputmodels.genralconfig;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.Objects;

@XmlRootElement(name = "option")
@XmlAccessorType(XmlAccessType.FIELD)
public class Option {

    @XmlAttribute(name = "Name")
    String name = "Board";

    @XmlAttribute(name = "value")
    String value;

    public Option() {
    }

    public Option(String name, String value) {
        this.name = name;
        this.value = value;
    }

    public Option(String value) {
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
        return "Option{" +
                "name='" + name + '\'' +
                ", value='" + value + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Option option = (Option) o;
        return Objects.equals(name, option.name) && Objects.equals(value, option.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, value);
    }
}
