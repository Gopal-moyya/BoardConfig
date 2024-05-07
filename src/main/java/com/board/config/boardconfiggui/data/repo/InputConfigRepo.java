package com.board.config.boardconfiggui.data.repo;

import com.board.config.boardconfiggui.data.inputmodels.ipconfig.IpConfig;
import com.board.config.boardconfiggui.data.inputmodels.pinconfig.PinConfig;
import com.board.config.boardconfiggui.data.outputmodels.clockconfig.ClockConfig;

/**
 * The InputConfigRepo class represents a repository for storing input configuration data,
 * including IP configuration, PIN configuration and Clock configuration.
 * <p>
 * This class follows the singleton pattern, ensuring that only one instance exists
 * throughout the application.
 */
public class InputConfigRepo {

    private static InputConfigRepo instance;
    private IpConfig ipConfig;
    private PinConfig pinConfig;
    private ClockConfig clockConfig;

    /**
     * Private constructor to prevent instantiation from outside the class.
     */
    private InputConfigRepo() {
    }

    /**
     * Returns the singleton instance of InputConfigRepo.
     * If the instance does not exist, a new one is created.
     *
     * @return The singleton instance of InputConfigRepo.
     */
    public static InputConfigRepo getInstance() {
        if (instance == null) {
            instance = new InputConfigRepo();
        }
        return instance;
    }

    /**
     * Gets the IP configuration stored in the repository.
     *
     * @return The IP configuration.
     */
    public IpConfig getIpConfig() {
        return ipConfig;
    }

    /**
     * Sets the IP configuration in the repository.
     *
     * @param ipConfig The IP configuration to be set.
     */
    public void setIpConfig(IpConfig ipConfig) {
        this.ipConfig = ipConfig;
    }

    /**
     * Gets the PIN configuration stored in the repository.
     *
     * @return The PIN configuration.
     */
    public PinConfig getPinConfig() {
        return pinConfig;
    }

    /**
     * Sets the PIN configuration in the repository.
     *
     * @param pinConfig The PIN configuration to be set.
     */
    public void setPinConfig(PinConfig pinConfig) {
        this.pinConfig = pinConfig;
    }

    /**
     * Gets the Clock configuration stored in the repository.
     *
     * @return The Clock configuration.
     */
    public ClockConfig getClockConfig() {
        return clockConfig;
    }

    /**
     * Sets the Clock configuration in the repository.
     *
     * @param clockConfig The clock configuration to be set.
     */
    public void setClockConfig(ClockConfig clockConfig) {
        this.clockConfig = clockConfig;
    }
}
