package com.board.config.boardconfiggui.data.repo;

import com.board.config.boardconfiggui.data.inputmodels.ipconfig.IpConfig;
import com.board.config.boardconfiggui.data.inputmodels.pinconfig.PinConfig;

public class InputConfigRepo {

    private static InputConfigRepo instance;
    private IpConfig ipConfig;
    private PinConfig pinConfig;

    public InputConfigRepo() {
    }

    public static InputConfigRepo getInstance() {
        if (instance == null) {
            instance = new InputConfigRepo();
        }
        return instance;
    }

    public IpConfig getIpConfig() {
        return ipConfig;
    }

    public void setIpConfig(IpConfig ipConfig) {
        this.ipConfig = ipConfig;
    }

    public PinConfig getPinConfig() {
        return pinConfig;
    }

    public void setPinConfig(PinConfig pinConfig) {
        this.pinConfig = pinConfig;
    }
}
