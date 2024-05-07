package com.board.config.boardconfiggui.data.outputmodels.ipconfig;

import org.apache.commons.collections4.CollectionUtils;

import javax.xml.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@XmlRootElement(name = "ipConfig")
@XmlAccessorType(XmlAccessType.FIELD)
public class IpConfig {

    @XmlAttribute(name = "Name")
    String name;

    @XmlElement(name = "IP")
    List<IpConfigIp> ips;

    public IpConfig() {
    }

    public IpConfig(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public List<IpConfigIp> getIps() {
        return ips;
    }

    public void setIps(List<IpConfigIp> ips) {
        this.ips = ips;
    }

  public void removeIpConfig(String ipName) {

    if (CollectionUtils.isNotEmpty(ips)) {
      IpConfigIp ipConfigIp = ips.stream()
        .filter(ipConfigIpInstance -> ipConfigIpInstance.getName().equals(ipName))
        .findFirst()
        .orElse(null);
      if (Objects.nonNull(ipConfigIp)) {
        ips.remove(ipConfigIp);
      }
    }
  }

  /**
   * Method to get {@link IpConfigIp} based on ip name
   * @param ipName for reference
   * @return {@link IpConfigIp}
   */
  public IpConfigIp getIpConfig(String ipName) {

    if (CollectionUtils.isNotEmpty(ips)) {
      return ips.stream()
        .filter(ipConfigIpInstance -> ipConfigIpInstance.getName().equals(ipName))
        .findFirst()
        .orElse(null);
    }

    return null;
  }

    public void addIpConfigIp(IpConfigIp newIpConfigIp) {
        if (CollectionUtils.isEmpty(ips))
            ips = new ArrayList<>();
        ips.add(newIpConfigIp);
    }

    @Override
    public String toString() {
        return "IpConfig{" +
                "name='" + name + '\'' +
                ", IP=" + ips +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        IpConfig ipConfig = (IpConfig) o;
        return Objects.equals(name, ipConfig.name) && Objects.equals(ips, ipConfig.ips);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, ips);
    }
}
