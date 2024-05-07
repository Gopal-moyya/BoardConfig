package com.board.config.boardconfiggui.ui.models;

import org.apache.commons.collections4.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class PinType {
    private final String name;
    private List<String> children = new ArrayList<>();

    public PinType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public List<String> getChildren() {
        return children;
    }

    public void addChild(String child){
        if(Objects.isNull(child))
            return;

        if(CollectionUtils.isEmpty(children)){
            this.children = new ArrayList<>();
        }
        this.children.add(child);
    }

    public void setChildren(List<String> children) {
        this.children = children;
    }
}
