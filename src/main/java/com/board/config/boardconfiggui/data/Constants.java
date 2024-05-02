package com.board.config.boardconfiggui.data;

/**
 * The Constants class provides constant values used in the application.
 */
public class Constants {

    /**
     * File path for the hardware configuration XML file.
     */
    public static final String HARDWARE_CONFIG_FILE = "src/main/assets/hardware_configuration.xml";

    /**
     * File path for the PIN configuration XML file.
     */
    public static final String PIN_CONFIG_FILE = "src/main/assets/pin_muxing.xml";

    /**
     * Constant representing the development role.
     */
    public static final String DEV_ROLE = "devRole";

    /**
     * Constant representing the QSPI IP name
     */
    public static final String QSPI_IP_NAME = "QSPI";

    /**
     * Constant representing the write completion config name
     */
    public static final String WRITE_COMPLETION_CONFIG_NAME = "writeCompletionConfig";

    /**
     * Constant representing the device descriptor
     */
    public static final String DEVICE_DESCRIPTOR_NAME = "deviceDescriptor";

    // Slave Device configuration strings
    public static final String DEVICE_NAME = "Device Name";
    public static final String I2CFM_PLUS_SPEED = "i2cFmPlusSpeed";
    public static final String I2C10B_ADDRESS = "i2c10bAddr";
    public static final String HDR_CAPABLE = "hdrCapable";
    public static final String LEGACY_I2C_DEV = "legacyI2CDev";
    public static final String HAS_STATIC_ADDRESS = "hasStaticAddress";
    public static final String STATIC_ADDRESS = "staticAddress";
    public static final String DYNAMIC_ADDRESS = "dynamicAddress";
    public static final String IS_IBI_DEVICE = "isIbiDevice";
    public static final String IBI_PAYLOAD_SIZE = "ibiPayloadSize";
    public static final String IBI_PAYLOAD_SPEED_LIMIT = "ibiPayloadSpeedLimit";
    public static final String SELECT_DEVICE_ROLE = "Select Device Role";

    //Strings related to the Pin configuration
    public static final String INPUT = "input";
    public static final String OUTPUT_HIGH = "output_high";
    public static final String OUTPUT_LOW = "output_low";
    public static final String EDGE_TRIG_RISE = "Edge_trig_rise";
    public static final String EDGE_TRIG_FALL = "Edge_trig_fall";
    public static final String EDGE_TRIG_ANY = "Edge_trigger_any";
    public static final String LEVEL_TRIG_HIGH = "Level_trig_high";
    public static final String LEVEL_TRIG_LOW = "Level_trig_low";
    public static final String LEVEL = "Level";
    public static final String LEVEL_TRIGGERED = "LevelTriggered";
    public static final String EDGE_TRIGGERED = "EdgeTriggered";
    public static final String BY_PASS = "Bypass";
    public static final String GPIO = "GPIO";
    public static final String EXTI = "EXTI";
}

