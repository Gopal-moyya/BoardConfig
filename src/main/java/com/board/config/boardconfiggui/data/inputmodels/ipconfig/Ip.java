package com.board.config.boardconfiggui.data.inputmodels.ipconfig;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;
import java.util.Objects;

/**
 * Represents an IP with list of instances.
 */
@XmlRootElement(name = "IP")
public class Ip {
    @XmlAttribute(name = "name")
    String name;
    @XmlAttribute(name = "noOfInstances")
    Integer noOfInstances;
    @XmlElement(name = "Instance")
    List<Instance> instanceList;

    public String getName() {
        return name;
    }

    public Integer getNoOfInstances() {
        return noOfInstances;
    }

    public List<Instance> getInstanceList() {
        return instanceList;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Ip ip = (Ip) o;
        return Objects.equals(name, ip.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    @Override
    public String toString() {
        return "Ip{" +
                "name='" + name + '\'' +
                ", noOfInstances=" + noOfInstances +
                ", instanceList=" + instanceList +
                '}';
    }
}
