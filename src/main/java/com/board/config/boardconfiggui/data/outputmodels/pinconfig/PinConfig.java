package com.board.config.boardconfiggui.data.outputmodels.pinconfig;

import com.sun.istack.NotNull;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.*;

@XmlRootElement(name = "pinConfig")
@XmlAccessorType(XmlAccessType.FIELD)
public class PinConfig {

    @XmlElement(name = "Port")
    List<PinConfigPort> ports;

    public List<PinConfigPort> getPorts() {
        return ports;
    }

    public void addPort(PinConfigPort pinConfigPort) {
        if (Objects.isNull(ports)) {
            ports = new ArrayList<>();
        }
        ports.add(pinConfigPort);
    }

    public void removePort(PinConfigPort pinConfigPort) {
        if (Objects.isNull(pinConfigPort)) {
            return;
        }
        ports.remove(pinConfigPort);
    }


    public void setPorts(List<PinConfigPort> ports) {
        this.ports = ports;
    }

    public PinConfigParam getPinConfigParamsData(String portNumber, String pinNumber) {
        if (ports != null) {
            for (PinConfigPort pinConfigPort : ports) {
                if (portNumber.equals(pinConfigPort.name)) {
                    return pinConfigPort.getPinConfigParamData(pinNumber);
                }
            }
        }
        return null;
    }

    @Override
    public String toString() {
        return "PinConfig{" +
                "Port=" + ports +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PinConfig pinConfig = (PinConfig) o;
        return Objects.equals(ports, pinConfig.ports);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(ports);
    }

    /**
     * Method to save the pin configuration of the given port.
     * @param portNumber reference of the port number
     * @param pinNumber reference of the pin number to be saved
     * @param pinConfigParam reference of the pin configuration to be saved.
     */
    public void savePinConfig(String portNumber, String pinNumber, @NotNull PinConfigParam pinConfigParam) {

        if (CollectionUtils.isEmpty(ports)) {
            PinConfigPort pinConfigPort1 = new PinConfigPort(portNumber);
            pinConfigPort1.setPinConfigParamData(pinNumber, pinConfigParam);
            addPort(pinConfigPort1);
        } else {
            PinConfigPort pinConfigPort = ports.stream()
                    .filter(x -> x.getName().equals(portNumber))
                    .findFirst()
                    .orElse(null);
            if (Objects.isNull(pinConfigPort)) {
                PinConfigPort pinConfigPort1 = new PinConfigPort(portNumber);
                pinConfigPort1.setPinConfigParamData(pinNumber, pinConfigParam);
                addPort(pinConfigPort1);
            } else {
                if (portNumber.equals(pinConfigPort.name)) {
                    pinConfigPort.setPinConfigParamData(pinNumber, pinConfigParam);
                }
            }

        }
    }


    /**
     * Method to remove the pin configuration of the given port.
     * @param portNumber reference of the port number
     * @param pinNumber reference of the pin to be removed.
     */
    public void removePinConfig(String portNumber, String pinNumber) {

        if (CollectionUtils.isNotEmpty(ports)) {
            PinConfigPort pinConfigPort = ports.stream()
                    .filter(x -> x.getName().equals(portNumber))
                    .findFirst()
                    .orElse(null);
            if (Objects.nonNull(pinConfigPort)) {
                pinConfigPort.removePinConfigParamData(pinNumber);
            }

        }
    }

    /**
     * Method to get all the bypass disabled pin configurations of the current port.
     *
     * @return bypass disabled pin configurations
     */
    public Map<String, List<String>> getBypassDisabledPins() {
      if (CollectionUtils.isEmpty(ports)) {
        return new HashMap<>();
      }
      Map<String, List<String>> bypassDisabledPins = new HashMap<>();
      for (PinConfigPort pinConfigPort : ports) {
        List<String> pins = new ArrayList<>();
        for (PinConfigParam pinConfigParam: pinConfigPort.getConfigParams()) {
          if (BooleanUtils.isFalse(pinConfigParam.byPassMode)) {
            pins.add(pinConfigParam.getPin());
          }
        }
        bypassDisabledPins.put(pinConfigPort.getName(), pins);
      }
      return bypassDisabledPins;
    }

  /**
   * Method to get all the pins that are configured for Ips that are other than current Ip.
   *
   * @param currentIpName current ip name for reference
   *
   * @return pins used by other ips
   */
    public Map<String, List<String>> getPinsUsedByOtherIps(String currentIpName) {
      if (CollectionUtils.isEmpty(ports)) {
        return new HashMap<>();
      }
      Map<String, List<String>> pinsUsedByOtherIps = new HashMap<>();
      for (PinConfigPort pinConfigPort : ports) {
        List<String> pins = new ArrayList<>();
        for (PinConfigParam pinConfigParam: pinConfigPort.getConfigParams()) {
          String ipName = pinConfigParam.getValue().split("_")[0];
          if (!StringUtils.equals(currentIpName, ipName)) {
            pins.add(pinConfigParam.getPin());
          }
        }
        pinsUsedByOtherIps.put(pinConfigPort.getName(), pins);
      }
      return pinsUsedByOtherIps;
    }
}
