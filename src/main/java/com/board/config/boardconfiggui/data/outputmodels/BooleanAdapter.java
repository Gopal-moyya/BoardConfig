package com.board.config.boardconfiggui.data.outputmodels;

import org.apache.commons.lang3.StringUtils;

import javax.xml.bind.annotation.adapters.XmlAdapter;
import java.util.Objects;

public class BooleanAdapter extends XmlAdapter<String, Boolean> {

    @Override
    public Boolean unmarshal(String value) throws Exception {
      if (StringUtils.isBlank(value)) {
        return null;
      }
        return "TRUE".equals(value) || "true".equals(value);
    }

    @Override
    public String marshal(Boolean value) throws Exception {
      if (Objects.isNull(value)) {
        return null;
      }
        return value ? "TRUE" : "FALSE";
    }
}

