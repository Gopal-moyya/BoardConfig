package com.board.config.boardconfiggui.data;

import java.util.Arrays;
import java.util.List;

/**
 * The Constants class provides constant values used in the application.
 */
public class Constants {

    /**
     * File path for the hardware configuration XML file name.
     */
    public static final String HARDWARE_CONFIG_FILE_NAME = "hardware_configuration.xml";

    /**
     * File path for the PIN configuration XML file name.
     */
    public static final String PIN_CONFIG_FILE_NAME = "pin_muxing.xml";

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
    public static final String NUMERIC_FIELD_ONLY = "Numeric Field Only";

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

    public static final String BOARD_NAME = "Board Name";

    /**
     * Constant for direction output.
     */
    public static final String DIRECTION_OUTPUT = "Output";

    /**
     * Constant representing the value '1'.
     */
    public static final String ONE = "1";

    /**
     * Constant representing the system clock.
     */
    public static final String SYS_CLK = "sysClk";

    /**
     * Constant representing the I2C frequency.
     */
    public static final String I2C_FREQ = "i2cFreq";

    /**
     * Constant representing the SDR frequency.
     */
    public static final String SDR_FREQ = "sdrFreq";

    /**
     * Required parameters for I3C IP configuration.
     */
    public static final List<String> I3C_IP_CONFIG_PARAMS_REQUIRED = Arrays.asList(
            SYS_CLK,
            I2C_FREQ,
            SDR_FREQ
    );

    /**
     * Required parameters for I3C device configuration.
     */
    public static final List<String> I3C_DEVICE_CONFIG_PARAMS_REQUIRED = Arrays.asList(
            I2CFM_PLUS_SPEED,
            I2C10B_ADDRESS,
            HDR_CAPABLE,
            LEGACY_I2C_DEV,
            HAS_STATIC_ADDRESS,
            STATIC_ADDRESS,
            DYNAMIC_ADDRESS,
            DEV_ROLE,
            IS_IBI_DEVICE
    );

    /**
     * Required parameters for I3C configuration of IBI device.
     */
    public static final List<String> I3C_CONFIG_IBI_DEVICE_PARAMS_REQUIRED = Arrays.asList(
            "ibiPayloadSize", "ibiPayloadSpeedLimit"
    );

    /**
     * Required parameters for clock configuration.
     */
    public static final List<String> CLOCK_CONFIG_PARAMS_REQUIRED = Arrays.asList(
            "FREF_BYPASS",
            "DAC_EN",
            "DSM_EN",
            "FBDIV_INT",
            "FBDIV_FRAC",
            "FOUT_POSTDIV_EN",
            "FOUT_VCO_EN",
            "FREF",
            "OFFSET_CAL_EN",
            "OFFSET_CAL_BYP",
            "OFFSET_CAL_CNT",
            "OFFSET_CAL_IN",
            "OFFSET_FAST_CAL",
            "PLL_EN",
            "REF_DIV",
            "POSTDIV1",
            "POSTDIV2",
            "FOUT_POSTDIV1",
            "FOUT_POSTDIV2",
            "FOUT_POSTDIV3",
            "FOUT_POSTDIV4",
            "FOUT_POSTDIV5"
    );
}

