package com.board.config.boardconfiggui.ui.utils;

import com.board.config.boardconfiggui.data.Constants;
import com.board.config.boardconfiggui.data.IpPinConfig;
import org.apache.commons.lang3.BooleanUtils;

import java.util.Objects;

public class IpPinConfigListCell extends javafx.scene.control.ListCell<IpPinConfig> {
    @Override
    protected void updateItem(IpPinConfig item, boolean empty) {
        super.updateItem(item, empty);
        if (Objects.isNull(item) || BooleanUtils.isTrue(empty)) {
            setText(Constants.SELECT);
        } else {
            setText(item.getDisplayValue());
            setDisable(item.isDisabled());
        }
    }
}
