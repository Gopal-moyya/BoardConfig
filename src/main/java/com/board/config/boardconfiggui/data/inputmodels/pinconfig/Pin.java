package com.board.config.boardconfiggui.data.inputmodels.pinconfig;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

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
    public String toString() {
        return "Pin{" +
                "name='" + name + '\'' +
                ", defaultValue='" + defaultValue + '\'' +
                ", values='" + values + '\'' +
                '}';
    }
}
