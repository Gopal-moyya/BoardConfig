package com.board.config.boardconfiggui.data.inputmodels.ipconfig;

import javax.xml.bind.annotation.*;
import java.util.List;
import java.util.Objects;

/**
 * POJO class represents Chip information.
 */
@XmlRootElement(name = "ChipInfo")
@XmlAccessorType(XmlAccessType.FIELD)
public class ChipInfo {
    @XmlElement(name = "Option")
    Option option;
    @XmlElementWrapper(name="Core")
    @XmlElement(name = "Option")
    List<Option> coreList;

    public Option getOption() {
        return option;
    }

    public void setOption(Option option) {
        this.option = option;
    }

    public List<Option> getCoreList() {
        return coreList;
    }

    public void setCoreList(List<Option> coreList) {
        this.coreList = coreList;
    }
}
