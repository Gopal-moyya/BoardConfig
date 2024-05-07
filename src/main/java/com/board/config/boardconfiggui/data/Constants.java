package com.board.config.boardconfiggui.data;

import com.board.config.boardconfiggui.data.enums.ConfigParam;

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

    public static final String BOARD_CONFIG_FILE_NAME = "board_configuration.xml";

    /**
     * Constant representing the development role.
     */
    public static final String DEV_ROLE = "devRole";

    /**
     * Constant representing the QSPI IP name
     */
    public static final String QSPI_IP_NAME = "QSPI";

    /**
     * Constant representing the I3C IP name
     */
    public static final String I3C_IP_NAME = "I3C";

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
    public static final String IBI_DEVICE_ENABLED = "1";
    public static final String NUMERIC_FIELD_ONLY = "Numeric Field Only";

    //Strings related to the Pin configuration
    public static final String INPUT = "input";
    public static final String OUTPUT = "output";
    public static final String LEVEL = "Level";
    public static final String LEVEL_TRIGGERED = "LevelTriggered";
    public static final String EDGE_TRIGGERED = "EdgeTriggered";
    public static final String MODES_TEXT = "Modes";
    public static final String BY_PASS = "Bypass";
    public static final String GPIO = "GPIO";
    public static final String EXTI = "EXTI";
    public static final String EDGE = "Edge";

    public static final String BOARD_NAME = "Board Name";

    public static final String SELECT = "select";

    public static final String TYPE = " Type:";
    public static final String PIN_STATUS_LABEL = "PIN Status:";
    public static final String MODE_TYPE_LABEL = "Mode Type:";

    /**
     * Constant for direction output.
     */
    public static final String DIRECTION_OUTPUT = "Output";
    public static final String DIRECTION_INPUT = "Input";

    /**
     * Constant representing the value '0'.
     */
    public static final String ZERO = "0";

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

    //IpConfig strings
    public static final String SYS_CLK_PARAM = "sysClk";
    public static final String I2C_FREQ_PARAM = "i2cFreq";
    public static final String SDR_FREQ_PARAM = "sdrFreq";

    // load data controller strings
    public static final String SELECT_FOLDER = "Select Folder";
    public static final String XML_BUTTON_TYPE = "xmlBtn";
    public static final String REPO_BUTTON_TYPE = "repoBtn";
    public static final String TOOL_CHAIN_BUTTON_TYPE = "toolChainBtn";
    public static final String OUTPUT_BUTTON_TYPE = "outputBtn";

    /**
     * Constant representing write completion config header to show in UI.
     */
    public static final String WRITE_COMPLETION_CONFIG_HEADER = "Write completion config controls";

    /**
     * Constant representing the list of ConfigParam for write completion config controls
     */
    public static final List<ConfigParam> QSPI_WRITE_COMPLETION_CONFIG_CONTROLS = Arrays.asList(
            ConfigParam.POOL_COUNT,
            ConfigParam.POLLING_BIT_INDEX,
            ConfigParam.OPCODE,
            ConfigParam.IS_DISABLED,
            ConfigParam.POLLING_POLARITY,
            ConfigParam.REPETITION_DELAY
    );
}

