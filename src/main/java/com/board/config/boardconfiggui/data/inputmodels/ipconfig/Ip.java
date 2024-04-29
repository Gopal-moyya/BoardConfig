package com.board.config.boardconfiggui.data.inputmodels.ipconfig;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "IP")
public class Ip {
    private String name;
    private Integer noOfInstances;
    private List<Instance> instanceList;

    @XmlAttribute(name = "name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @XmlAttribute(name = "noOfInstances")
    public Integer getNoOfInstances() {
        return noOfInstances;
    }

    public void setNoOfInstances(Integer noOfInstances) {
        this.noOfInstances = noOfInstances;
    }

    @XmlElement(name = "Instance")
    public List<Instance> getInstanceList() {
        return instanceList;
    }

    public void setInstanceList(List<Instance> instanceList) {
        this.instanceList = instanceList;
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
