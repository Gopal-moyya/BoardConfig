package com.board.config.boardconfiggui.common;

import com.board.config.boardconfiggui.data.enums.DeviceRole;

import java.util.Arrays;
import java.util.List;

public class Utils {

    public static List<String> getSlaveDeviceRoles() {
        return Arrays.asList(DeviceRole.SECONDARY_MASTER.name().toLowerCase(), DeviceRole.SLAVE.name().toLowerCase());
    }
}
