package com.board.config.boardconfiggui.data.outputmodels.ipconfig;

import org.apache.commons.lang3.StringUtils;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.Objects;

@XmlRootElement(name = "configParam")
@XmlAccessorType(XmlAccessType.FIELD)
public class WriteCompletionConfigParam {

    @XmlAttribute(name = "name")
    String name;

    @XmlAttribute(name = "Value")
    String value;

    public WriteCompletionConfigParam() {
    }

    public WriteCompletionConfigParam(String name, String value) {
        this.name = name;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public String getValue() {
        return value;
    }

    public boolean isValid(){
        return StringUtils.isNotEmpty(name) && StringUtils.isNotEmpty(value);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        WriteCompletionConfigParam that = (WriteCompletionConfigParam) o;
        return Objects.equals(name, that.name) && Objects.equals(value, that.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, value);
    }
}
