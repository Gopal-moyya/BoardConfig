package com.board.config.boardconfiggui;

import com.board.config.boardconfiggui.data.inputmodels.ipconfig.IpConfig;
import com.board.config.boardconfiggui.data.inputmodels.pinconfig.PinConfig;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.io.IOException;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 320, 240);
        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
//        launch();
        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(IpConfig.class);
            Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
            IpConfig ipConfig = (IpConfig) unmarshaller.unmarshal(new File("src/main/assets/hardware_configuration.xml"));
            System.out.println(ipConfig);
        } catch (JAXBException e) {
            e.printStackTrace();
        }

        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(PinConfig.class);
            Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
            PinConfig pinConfig = (PinConfig) unmarshaller.unmarshal(new File("src/main/assets/pin_muxing.xml"));
            System.out.println(pinConfig);
        } catch (JAXBException e) {
            e.printStackTrace();
        }
    }
}