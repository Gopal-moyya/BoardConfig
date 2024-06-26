package com.board.config.boardconfiggui.data.outputmodels.ipconfig;

import com.board.config.boardconfiggui.data.outputmodels.Param;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import javax.xml.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@XmlRootElement(name = "IP")
@XmlAccessorType(XmlAccessType.FIELD)
public class IpConfigIp {

    @XmlAttribute(name = "Name")
    String name;

    @XmlElement(name = "Port")
    List<IpConfigPort> ports;

    @XmlElement(name = "param")
    List<Param> params;

    @XmlElement(name = "writeCompletionConfig")
    WriteCompletionConfig writeCompletionConfig;

    @XmlElement(name = "deviceDescriptor")
    DeviceDescriptor deviceDescriptor;

    public IpConfigIp() {
    }

    public IpConfigIp(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public List<IpConfigPort> getPorts() {
        return ports;
    }

    public void setPorts(List<IpConfigPort> ports) {
        this.ports = ports;
    }

    public List<Param> getParams() {
        return params;
    }

    public void setParams(List<Param> params) {
        this.params = params;
    }

    public WriteCompletionConfig getWriteCompletionConfig() {
        return writeCompletionConfig;
    }

    public void setWriteCompletionConfig(WriteCompletionConfig writeCompletionConfig) {
        this.writeCompletionConfig = writeCompletionConfig;
    }

    public DeviceDescriptor getDeviceDescriptor() {
        return deviceDescriptor;
    }

    public void setDeviceDescriptor(DeviceDescriptor deviceDescriptor) {
        this.deviceDescriptor = deviceDescriptor;
    }

    public IpConfigPort getPortInfo(String portName) {
        if(CollectionUtils.isEmpty(ports))
            return null;
        return ports.stream().filter(ipConfigPort -> StringUtils.equals(portName, ipConfigPort.getName())).findFirst().orElse(null);
    }

    public void addPort(IpConfigPort portInfo) {
        if(CollectionUtils.isEmpty(ports))
            ports = new ArrayList<>();
        ports.add(portInfo);
    }

    public void deleteExistingSignalParam(SignalParam signalParam) {
        if(CollectionUtils.isEmpty(ports))
            return;
        for(IpConfigPort ipConfigPort : ports)
            ipConfigPort.deleteExistingSignalParam(signalParam);
    }

    @Override
    public String toString() {
        return "IpConfigIp{" +
                "name='" + name + '\'' +
                ", port=" + ports +
                ", param=" + params +
                ", writeCompletionConfig=" + writeCompletionConfig +
                ", deviceDescriptor=" + deviceDescriptor +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        IpConfigIp that = (IpConfigIp) o;
        return Objects.equals(name, that.name) && Objects.equals(ports, that.ports) && Objects.equals(params, that.params) && Objects.equals(writeCompletionConfig, that.writeCompletionConfig) && Objects.equals(deviceDescriptor, that.deviceDescriptor);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, ports, params, writeCompletionConfig, deviceDescriptor);
    }
}
