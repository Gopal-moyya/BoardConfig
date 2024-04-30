package com.board.config.boardconfiggui.data.outputmodels;

import com.board.config.boardconfiggui.data.outputmodels.clockconfig.ClockConfig;
import com.board.config.boardconfiggui.data.outputmodels.connectivityconfig.ConnectivityConfig;
import com.board.config.boardconfiggui.data.outputmodels.genralconfig.GeneralConfig;
import com.board.config.boardconfiggui.data.outputmodels.ipconfig.IpConfig;
import com.board.config.boardconfiggui.data.outputmodels.pinconfig.PinConfig;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.Objects;

@XmlRootElement(name = "root")
@XmlAccessorType(XmlAccessType.FIELD)
public class BoardResult {

    @XmlElement(name = "generalConfig")
    GeneralConfig generalConfig;

    @XmlElement(name = "connectivityConfig")
    ConnectivityConfig connectivityConfig;

    @XmlElement(name = "pinConfig")
    PinConfig pinConfig;

    @XmlElement(name = "ipConfig")
     IpConfig ipConfig;

    @XmlElement(name = "clockConfig")
    ClockConfig clockConfig;

    public GeneralConfig getGeneralConfig() {
        return generalConfig;
    }

    public void setGeneralConfig(GeneralConfig generalConfig) {
        this.generalConfig = generalConfig;
    }

    public ConnectivityConfig getConnectivityConfig() {
        return connectivityConfig;
    }

    public void setConnectivityConfig(ConnectivityConfig connectivityConfig) {
        this.connectivityConfig = connectivityConfig;
    }

    public PinConfig getPinConfig() {
        return pinConfig;
    }

    public void setPinConfig(PinConfig pinConfig) {
        this.pinConfig = pinConfig;
    }

    public IpConfig getIpConfig() {
        return ipConfig;
    }

    public void setIpConfig(IpConfig ipConfig) {
        this.ipConfig = ipConfig;
    }

    public ClockConfig getClockConfig() {
        return clockConfig;
    }

    public void setClockConfig(ClockConfig clockConfig) {
        this.clockConfig = clockConfig;
    }

    @Override
    public String toString() {
        return "Root{" +
                "generalConfig=" + generalConfig +
                ", connectivityConfig=" + connectivityConfig +
                ", pinConfig=" + pinConfig +
                ", ipConfig=" + ipConfig +
                ", clockConfig=" + clockConfig +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BoardResult root = (BoardResult) o;
        return Objects.equals(generalConfig, root.generalConfig) && Objects.equals(connectivityConfig, root.connectivityConfig) && Objects.equals(pinConfig, root.pinConfig) && Objects.equals(ipConfig, root.ipConfig) && Objects.equals(clockConfig, root.clockConfig);
    }

    @Override
    public int hashCode() {
        return Objects.hash(generalConfig, connectivityConfig, pinConfig, ipConfig, clockConfig);
    }
}
