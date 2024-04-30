package com.board.config.boardconfiggui.data.inputmodels.pinconfig;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.Objects;

/**
 * Represents a pin configuration for each port.
 */
@XmlRootElement(name = "pin")
public class Pin {
    @XmlAttribute(name = "name")
    String name;
    @XmlAttribute(name = "defaultValue")
    String defaultValue;
    @XmlAttribute(name = "values")
    String values;

    public String getName() {
        return name;
    }

    public String getDefaultValue() {
        return defaultValue;
    }

    public String getValues() {
        return values;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Pin pin = (Pin) o;
        return Objects.equals(name, pin.name);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(name);
    }

    @Override
    public String toString() {
        return "Pin{" +
                "name='" + name + '\'' +
                ", defaultValue='" + defaultValue + '\'' +
                ", values='" + values + '\'' +
                '}';
    }
}
