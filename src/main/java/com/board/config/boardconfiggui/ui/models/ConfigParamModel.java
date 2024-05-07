package com.board.config.boardconfiggui.ui.models;

import com.board.config.boardconfiggui.data.enums.ConfigParam;

import java.util.Objects;

public class ConfigParamModel {
    private final ConfigParam configParam;
    private String result;

    public ConfigParamModel(ConfigParam configParam, String result) {
        this.configParam = configParam;
        this.result = result;
    }

    public ConfigParam getConfigParam() {
        return configParam;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ConfigParamModel that = (ConfigParamModel) o;
        return configParam == that.configParam && Objects.equals(result, that.result);
    }

    @Override
    public int hashCode() {
        return Objects.hash(configParam, result);
    }

    @Override
    public String toString() {
        return "ConfigParamModel{" +
                "configParam=" + configParam +
                ", result='" + result + '\'' +
                '}';
    }
}

