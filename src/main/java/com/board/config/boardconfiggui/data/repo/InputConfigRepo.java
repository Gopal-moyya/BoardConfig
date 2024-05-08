package com.board.config.boardconfiggui.data.repo;

import com.board.config.boardconfiggui.data.inputmodels.ipconfig.IpConfig;
import com.board.config.boardconfiggui.data.inputmodels.pinconfig.Pin;
import com.board.config.boardconfiggui.data.inputmodels.pinconfig.PinConfig;
import com.board.config.boardconfiggui.data.inputmodels.pinconfig.Port;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * The InputConfigRepo class represents a repository for storing input configuration data,
 * including IP configuration and PIN configuration.
 * <p>
 * This class follows the singleton pattern, ensuring that only one instance exists
 * throughout the application.
 */
public class InputConfigRepo {

    private static InputConfigRepo instance;
    private IpConfig ipConfig;
    private PinConfig pinConfig;

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
     * Method to get the IP controls available for all Pins
     * @param ipName IP Name to be checked
     * @return list of controls for the given ip name
     */
    public List<String> getIPControls(String ipName) {
        if (StringUtils.isEmpty(ipName)) {
            return new ArrayList<>();
        }

        List<String> ipControls = new ArrayList<>();
        List<Port> ports = instance.getPinConfig().getPorts();

        // Pattern to match IP param from the pin values
        Pattern pattern = Pattern.compile("("+ ipName + "\\w+)");

        for (Port port : ports) {
            for (Pin pin : port.getPinList()) {
                Matcher matcher = pattern.matcher(pin.getValues());
                if (matcher.find()) {
                    String matchedString = matcher.group(1);
                    if (!ipControls.contains(matchedString)) {
                        ipControls.add(matchedString);
                    }
                }
            }
        }
        return ipControls;
    }
}
