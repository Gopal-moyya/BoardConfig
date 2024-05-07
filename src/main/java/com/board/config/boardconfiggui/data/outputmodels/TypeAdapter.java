package com.board.config.boardconfiggui.data.outputmodels;

import com.board.config.boardconfiggui.data.enums.DataType;
import org.apache.commons.lang3.StringUtils;

import javax.xml.bind.annotation.adapters.XmlAdapter;
import java.util.Objects;

/**
 * Custom XML adapter for converting between a String representation and a DataType object.
 * This adapter is used during XML unmarshalling (from XML to Java objects) and marshalling
 * (from Java objects to XML).
 */
public class TypeAdapter extends XmlAdapter<String, DataType> {

    /**
     * Converts a String value to a DataType object during unmarshalling.
     *
     * @param value The String value to be converted.
     * @return The corresponding DataType object, or null if the value is blank.
     * @throws Exception Any exception that occurs during the conversion process.
     */
    @Override
    public DataType unmarshal(String value) throws Exception {
        if (StringUtils.isBlank(value)) {
            return null;
        }
        return DataType.valueOf(DataType.class, value);
    }

    /**
     * Converts a DataType object to its String representation during marshalling.
     *
     * @param dataType The DataType object to be converted.
     * @return The String representation of the DataType, or null if the DataType is null.
     * @throws Exception Any exception that occurs during the conversion process.
     */
    @Override
    public String marshal(DataType dataType) throws Exception {
        if (Objects.isNull(dataType)) {
            return null;
        }
        return dataType.toString();
    }
}

