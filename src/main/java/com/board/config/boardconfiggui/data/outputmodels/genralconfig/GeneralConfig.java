package com.board.config.boardconfiggui.data.outputmodels.genralconfig;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.Objects;

@XmlRootElement(name = "generalConfig")
@XmlAccessorType(XmlAccessType.FIELD)
public class GeneralConfig {

    @XmlElement(name = "option")
    Option option;

    public GeneralConfig() {
    }

    public GeneralConfig(Option option) {
        this.option = option;
    }

    public Option getOption() {
        return option;
    }

    @Override
    public String toString() {
        return "GeneralConfig{" +
                "option=" + option +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GeneralConfig that = (GeneralConfig) o;
        return Objects.equals(option, that.option);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(option);
    }
}
