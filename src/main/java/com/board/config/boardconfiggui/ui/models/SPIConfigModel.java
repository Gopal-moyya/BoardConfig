package com.board.config.boardconfiggui.ui.models;

import com.board.config.boardconfiggui.data.IpPinConfig;

import java.util.List;
import java.util.Objects;

public class SPIConfigModel {
    private final String label;
    private final List<IpPinConfig> ipPinConfigs;
    private String result;

    public SPIConfigModel(String label, List<IpPinConfig> ipPinConfigs, String result) {
        this.label = label;
        this.ipPinConfigs = ipPinConfigs;
        this.result = result;
    }

    public String getLabel() {
        return label;
    }

    public List<IpPinConfig> getIpPinConfigs() {
        return ipPinConfigs;
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
        SPIConfigModel that = (SPIConfigModel) o;
        return Objects.equals(label, that.label) && Objects.equals(ipPinConfigs, that.ipPinConfigs) && Objects.equals(result, that.result);
    }

    @Override
    public int hashCode() {
        return Objects.hash(label, ipPinConfigs, result);
    }

    @Override
    public String toString() {
        return "SPIConfigModel{" +
                "label='" + label + '\'' +
                ", ipPinConfigs=" + ipPinConfigs +
                ", result='" + result + '\'' +
                '}';
    }
}
