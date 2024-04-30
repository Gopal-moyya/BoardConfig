package com.board.config.boardconfiggui.data.outputmodels.ipconfig;

import com.board.config.boardconfiggui.data.Constants;

import javax.xml.bind.annotation.*;
import java.util.List;
import java.util.Objects;

@XmlRootElement(name = "writeCompletionConfig")
@XmlAccessorType(XmlAccessType.FIELD)
public class WriteCompletionConfig {

    @XmlAttribute(name = "name")
    final static String name = Constants.WRITE_COMPLETION_CONFIG_NAME;

    @XmlElement(name = "configParam")
    List<WriteCompletionConfigParam> configParams;

    public WriteCompletionConfig() {
    }

    public String getName() {
        return name;
    }

    public List<WriteCompletionConfigParam> getConfigParams() {
        return configParams;
    }

    public void setConfigParams(List<WriteCompletionConfigParam> configParams) {
        this.configParams = configParams;
    }

    @Override
    public String toString() {
        return "WriteCompletionConfig{" +
                ", configParam=" + configParams +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        WriteCompletionConfig that = (WriteCompletionConfig) o;
        return Objects.equals(configParams, that.configParams);
    }

    @Override
    public int hashCode() {
        return Objects.hash(configParams);
    }
}
