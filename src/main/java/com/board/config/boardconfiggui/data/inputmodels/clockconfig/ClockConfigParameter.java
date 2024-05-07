package com.board.config.boardconfiggui.data.inputmodels.clockconfig;

import com.board.config.boardconfiggui.data.enums.DataType;
import com.board.config.boardconfiggui.data.outputmodels.TypeAdapter;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.util.Objects;

/**
 * Represents a configuration parameter for a clock.
 */
@XmlRootElement(name = "configParam")
public class ClockConfigParameter {

    @XmlAttribute(name = "id")
    String id;

    @XmlAttribute(name = "displayValue")
    String displayValue;

    @XmlAttribute(name = "type")
    @XmlJavaTypeAdapter(TypeAdapter.class)
    DataType type;

    public String getId() {
        return id;
    }

    public String getDisplayValue() {
        return displayValue;
    }

    public DataType getType() {
        return type;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ClockConfigParameter that = (ClockConfigParameter) o;
        return Objects.equals(id, that.id) && Objects.equals(displayValue, that.displayValue) && type == that.type;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, displayValue, type);
    }

    @Override
    public String toString() {
        return "ClockConfigParam{"
                + "id='" + id + '\'' +
                ", displayValue='" +
                displayValue + '\'' +
                ", type=" + type + '}';
    }
}
