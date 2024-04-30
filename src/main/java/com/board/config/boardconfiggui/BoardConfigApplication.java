package com.board.config.boardconfiggui;

import com.board.config.boardconfiggui.data.Constants;
import com.board.config.boardconfiggui.data.inputmodels.ipconfig.IpConfig;
import com.board.config.boardconfiggui.data.inputmodels.pinconfig.PinConfig;
import com.board.config.boardconfiggui.data.repo.InputConfigRepo;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.io.IOException;
import java.util.logging.Logger;

public class BoardConfigApplication extends Application {
    private static final Logger logger = Logger.getLogger(BoardConfigApplication.class.getName());

    @Override
    public void init() {
        initialize();
    }

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(BoardConfigApplication.class.getResource("board-config.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 320, 240);
        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }

    /**
     * Initializes the input configuration repository with data from XML files.
     * Reads IP configuration from the hardware configuration file and PIN configuration
     * from the PIN muxing file.
     *
     * This method initializes the input configuration repository with data obtained
     * from XML files using JAXB (Java Architecture for XML Binding).
     *
     * @throws JAXBException if an error occurs during unmarshalling of XML files.
     */
    private static void initialize() {
        try {
            InputConfigRepo inputConfigRepo = InputConfigRepo.getInstance();
            JAXBContext ipConfigContext = JAXBContext.newInstance(IpConfig.class);
            Unmarshaller ipConfigUnmarshaller = ipConfigContext.createUnmarshaller();
            IpConfig ipConfig = (IpConfig) ipConfigUnmarshaller.unmarshal(new File(Constants.HARDWARE_CONFIG_FILE));
            inputConfigRepo.setIpConfig(ipConfig);

            JAXBContext pinConfigContext = JAXBContext.newInstance(PinConfig.class);
            Unmarshaller pinConfigUnmarshaller = pinConfigContext.createUnmarshaller();
            PinConfig pinConfig = (PinConfig) pinConfigUnmarshaller.unmarshal(new File(Constants.PIN_CONFIG_FILE));
            inputConfigRepo.setPinConfig(pinConfig);
        } catch (JAXBException e) {
            logger.warning("Initialization failed" + e);
        }
    }
}