package com.board.config.boardconfiggui.ui.utils;

import com.board.config.boardconfiggui.data.IpPinConfig;

public class IpPinConfigListCell extends javafx.scene.control.ListCell<IpPinConfig> {
  @Override
  protected void updateItem(IpPinConfig item, boolean empty) {
    super.updateItem(item, empty);
    if (item == null || empty) {
      setText("select");
    } else {
      setText(item.getDisplayValue());
      setDisable(item.isDisabled());
    }
  }
}
