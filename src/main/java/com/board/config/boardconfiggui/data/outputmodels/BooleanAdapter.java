package com.board.config.boardconfiggui.data.outputmodels;

import javax.xml.bind.annotation.adapters.XmlAdapter;

public class BooleanAdapter extends XmlAdapter<String, Boolean> {

    @Override
    public Boolean unmarshal(String value) throws Exception {
        return "TRUE".equals(value) || "true".equals(value);
    }

    @Override
    public String marshal(Boolean value) throws Exception {
        return value ? "TRUE" : "FALSE";
    }
}

