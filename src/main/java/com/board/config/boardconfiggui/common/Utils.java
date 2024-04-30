package com.board.config.boardconfiggui.common;

import com.board.config.boardconfiggui.data.enums.DeviceRole;

import java.util.Arrays;
import java.util.List;

/**
 * Utility class containing helper methods.
 */
public class Utils {

    /**
     * Returns a list of slave device roles.
     * The roles include "SECONDARY_MASTER" and "SLAVE".
     *
     * @return List of strings representing slave device roles.
     */
    public static List<String> getSlaveDeviceRoles() {
        return Arrays.asList(DeviceRole.SECONDARY_MASTER.name().toLowerCase(), DeviceRole.SLAVE.name().toLowerCase());
    }
}
