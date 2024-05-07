package com.board.config.boardconfiggui.ui.models;

import com.board.config.boardconfiggui.data.enums.DataType;

public class ClockConfigModel {
    private final String id;
    private final DataType viewType;
    private final String label;
    private String result;

    public ClockConfigModel(String id, DataType viewType, String label) {
        this.id = id;
        this.viewType = viewType;
        this.label = label;
    }

    public String getId() {
        return id;
    }

    public DataType getViewType() {
        return viewType;
    }

    public String getLabel() {
        return label;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

}

