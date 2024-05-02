package com.board.config.boardconfiggui.ui.models;

public class ClockConfigModel {
    private ViewType viewType;
    private String label;
    private String result;

    public ClockConfigModel(ViewType viewType, String label, String result) {
        this.viewType = viewType;
        this.label = label;
        this.result = result;
    }

    public ViewType getViewType() {
        return viewType;
    }

    public void setViewType(ViewType viewType) {
        this.viewType = viewType;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public enum ViewType {
        TEXT_FIELD,
        RADIO_BUTTON
    }
}

