package com.board.config.boardconfiggui.data.outputmodels.genralconfig;

import org.apache.commons.collections4.CollectionUtils;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@XmlRootElement(name = "generalConfig")
@XmlAccessorType(XmlAccessType.FIELD)
public class GeneralConfig {

    @XmlElement(name = "option")
    List<Option> options;

    public GeneralConfig() {
    }

    public void addConfig(Option option) {
        if(CollectionUtils.isEmpty(options))
            options = new ArrayList<>();
        this.options.add(option);
    }

    public List<Option> getOptions() {
        return options;
    }

    public Option getOption(String optionName) {
        return options.stream().filter(option -> option.getName().equals(optionName)).findFirst().orElse(null);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GeneralConfig that = (GeneralConfig) o;
        return Objects.equals(options, that.options);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(options);
    }

    @Override
    public String toString() {
        return "GeneralConfig{" +
                "options=" + options +
                '}';
    }
}
